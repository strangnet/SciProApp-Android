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

package se.su.dsv.scipro.android.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import se.su.dsv.scipro.android.location.LocationIntentReceiver;

/**
 * User: patrick
 * Date: 2011-05-23
 * Time: 11:50
 */
public class LocationService extends Service {

    public static final String TAG = "LocationService";

    private static final float DSV_LONGITUDE = (float) 17.94446;
    private static final float DSV_LATITUDE = (float) 59.40540;

    private static final int MINIMUM_LOCATION_UPDATE_TIME = 60000;
    private static final float MINIMUM_LOCATION_UPDATE_DISTANCE = 10;

    private static final String PROXIMITY_ALERT = "se.su.dsv.scipro.android.ProximityAlert";

    private static final float PROXIMITY_RADIUS = 200;
    private static final long NO_EXPIRATION_TIME = -1;

    private LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated.");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_LOCATION_UPDATE_TIME,
                MINIMUM_LOCATION_UPDATE_DISTANCE,
                new MyLocationListener());

        saveProximityLocation();
    }

    private void saveProximityLocation() {
        saveLocationInPreferences(DSV_LONGITUDE, DSV_LATITUDE);
        addProximityAlert();
    }

    private void addProximityAlert() {
        Intent intent = new Intent(PROXIMITY_ALERT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        locationManager.addProximityAlert(
                DSV_LATITUDE,
                DSV_LONGITUDE,
                PROXIMITY_RADIUS,
                NO_EXPIRATION_TIME,
                pendingIntent);

        IntentFilter filter = new IntentFilter(PROXIMITY_ALERT);
        registerReceiver(new LocationIntentReceiver(), filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyed.");
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Location pointLocation = retrieveLocationFromPreferences();
            float distance = location.distanceTo(pointLocation);
            Toast.makeText(LocationService.this,
                    "Distance from point: " + distance, Toast.LENGTH_LONG).show();
            Log.d(TAG, "Distance: " + distance);
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String s) {
        }

        public void onProviderDisabled(String s) {
        }
    }

    private Location retrieveLocationFromPreferences() {
        SharedPreferences preferences = this.getSharedPreferences(getClass().getSimpleName(),
                Context.MODE_PRIVATE);
        Location location = new Location("POINT_LOCATION");
        location.setLongitude(preferences.getFloat("POINT_LONGITUDE_KEY", 0));
        location.setLatitude(preferences.getFloat("POINT_LATITUDE_KEY", 0));
        return location;
    }

    private void saveLocationInPreferences(float longitude, float latitude) {
        SharedPreferences preferences = this.getSharedPreferences(getClass().getSimpleName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("POINT_LONGITUDE_KEY", longitude);
        editor.putFloat("POINT_LATITUDE_KEY", latitude);
        editor.commit();
    }
}
