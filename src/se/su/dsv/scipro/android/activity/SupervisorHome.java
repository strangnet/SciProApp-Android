/*
 * Copyright (c) 2011 Patrick Strang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package se.su.dsv.scipro.android.activity;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import se.su.dsv.scipro.android.IHeaderOnClick;
import se.su.dsv.scipro.android.Preferences;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.SciProApplication;
import se.su.dsv.scipro.android.adapters.ProjectListAdapter;
import se.su.dsv.scipro.android.helpers.MenuHelper;
import se.su.dsv.scipro.android.location.LocationIntentReceiver;
import se.su.dsv.scipro.android.tasks.GetProjectsAsyncTask;
import se.su.dsv.scipro.android.utils.SciProUtils;

public class SupervisorHome extends ListActivity implements IHeaderOnClick, GetProjectsAsyncTask.ProjectsResponder, SharedPreferences.OnSharedPreferenceChangeListener {
    
    private static final int SHOW_PROJECT = 1;
    private static final String TAG = "SupervisorHome";

    private static final double DSV_LONGITUDE = 17.94446;
    private static final double DSV_LATITUDE = 59.40540;
    private static final double HOME_LONGITUDE = 59.19032;
    private static final double HOME_LATITUDE = 17.81658;

    private static final int MINIMUM_LOCATION_UPDATE_TIME = 60000;
    private static final float MINIMUM_LOCATION_UPDATE_DISTANCE = 5f;

    private static final String PROXIMITY_ALERT = "se.su.dsv.scipro.android.ProximityAlert";

    private static final float PROXIMITY_RADIUS = 200f;
    private static final long NO_EXPIRATION_TIME = -1;
    
    private ProjectListAdapter adapter;
    private ProgressDialog projectRetrievalInProgress;

    private LocationManager locationManager;
    private PendingIntent pendingLocationIntent;
    private LocationIntentReceiver locationIntentReceiver;
    private String provider;
    private static boolean proximityAlertSet = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).registerOnSharedPreferenceChangeListener(this);

        setContentView(R.layout.activity_supervisor_home);
        
        setUpViews();

//        if (SciProApplication.getInstance().getProjects().size() == 0) {
//            new GetProjectsAsyncTask(this).execute();
//        } else {
//            initListAdapter();
//        }

        new GetProjectsAsyncTask(this).execute();

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean(Preferences.PREF_LOCATION, false)
                && !proximityAlertSet) {
            initProximityAlert();
        }
        
        bleh();
    }

    private void bleh() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, StatusCheckIn.class), 0);

        Notification notification = createNotification();

        String notificationTitle = "";
        String notificationText = "";

        notificationTitle = "Entering DSV";
        notificationText = "You are entering the proximity of DSV. Click to change your SciPro status.";
        
        notification.setLatestEventInfo(this,
                notificationTitle,
                notificationText,
                pendingIntent);

        NotificationManager notificationManager =
            (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        
        notificationManager.notify(4711, notification);
    }
    
    private Notification createNotification() {
        Notification notification = new Notification();

        notification.icon = R.drawable.ic_stat_location;
        notification.when = System.currentTimeMillis();

        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        return notification;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!SciProApplication.getInstance().isAuthenticated()) {
//            Intent intent = new Intent(this, Authenticate.class);
//            startActivity(intent);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, ProjectView.class);
        intent.putExtra("project", adapter.getItem(position));
        startActivityForResult(intent, SHOW_PROJECT);
    }



    public void initListAdapter() {
        adapter = new ProjectListAdapter(SciProApplication.getInstance().getProjects());
        setListAdapter(adapter);
    }

    private void setUpViews() {
        ImageView headerLogo = (ImageView) findViewById(R.id.header_logo);
        headerLogo.setVisibility(View.VISIBLE);
    }

    public void onHeaderHomeClick(View v) {
        SciProUtils.openHomeActivity(this);
    }

    public void onHeaderMessagesClick(View v) {
        SciProUtils.openMessagesActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuHelper.openActivityFromMenuItem(this, item);
    }

    public void retrievingProjects() {
        projectRetrievalInProgress = ProgressDialog.show(this,
                "Loading Projects",
                "Retrieving projects from SciPro");
    }

    public void retrievedProjects(GetProjectsAsyncTask.ProjectsResult result) {
        projectRetrievalInProgress.dismiss();
        SciProApplication.getInstance().setProjects(result.projects);
        initListAdapter();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!sharedPreferences.getBoolean(Preferences.PREF_LOCATION, false)) {
            //removeProximityAlert();
        } else {
            //initProximityAlert();
        }
    }

    private void initProximityAlert() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(criteria, true);

        locationManager.requestLocationUpdates(
                provider,
                MINIMUM_LOCATION_UPDATE_TIME,
                MINIMUM_LOCATION_UPDATE_DISTANCE,
                new MyLocationListener());

        setProximityAlert();

        Toast.makeText(this, "The Proximity Alert Service was initialized.", Toast.LENGTH_LONG).show();
    }

    private void setProximityAlert() {
        Intent intent = new Intent(PROXIMITY_ALERT);
        pendingLocationIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        locationIntentReceiver = new LocationIntentReceiver();

        locationManager.addProximityAlert(
                DSV_LATITUDE,
                DSV_LONGITUDE,
                PROXIMITY_RADIUS,
                NO_EXPIRATION_TIME,
                pendingLocationIntent);

        IntentFilter filter = new IntentFilter(PROXIMITY_ALERT);
        registerReceiver(locationIntentReceiver, filter);
        proximityAlertSet = true;
    }

    private void removeProximityAlert() {
        if (pendingLocationIntent != null) {
            locationManager.removeProximityAlert(pendingLocationIntent);
        }
        unregisterReceiver(locationIntentReceiver);
        proximityAlertSet = false;
        Toast.makeText(this, "The Proximity Alert Service was shut down.", Toast.LENGTH_LONG).show();
    }

private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Log.d(TAG, "Location Changed: " + location);
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d(TAG, "Location Changed: " + s + " " + i);
        }

        public void onProviderEnabled(String s) {
        }

        public void onProviderDisabled(String s) {
        }
    }


}