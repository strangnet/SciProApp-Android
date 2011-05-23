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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import se.su.dsv.scipro.android.IHeaderOnClick;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.SciProApplication;
import se.su.dsv.scipro.android.adapters.ProjectListAdapter;
import se.su.dsv.scipro.android.helpers.MenuHelper;
import se.su.dsv.scipro.android.services.LocationService;
import se.su.dsv.scipro.android.tasks.GetProjectsAsyncTask;
import se.su.dsv.scipro.android.utils.SciProUtils;

public class SupervisorHome extends ListActivity implements IHeaderOnClick, GetProjectsAsyncTask.ProjectsResponder {
    
    private static final int SHOW_PROJECT = 1;
    private static final String TAG = "SupervisorHome";
    
    private ProjectListAdapter adapter;
    private ProgressDialog projectRetrievalInProgress;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_supervisor_home);
        
        setUpViews();
//        adapter = new ProjectListAdapter(DummyData.getInstance().getProjects());
//        setListAdapter(adapter);
        if (SciProApplication.getInstance().getProjects().size() == 0) {
            new GetProjectsAsyncTask(this).execute();
        } else {
            initListAdapter();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (!SciProApplication.getInstance().isAuthenticated()) {
            Intent intent = new Intent(this, Authenticate.class);
            startActivity(intent);
        }
        startService(new Intent(this, LocationService.class));
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
}