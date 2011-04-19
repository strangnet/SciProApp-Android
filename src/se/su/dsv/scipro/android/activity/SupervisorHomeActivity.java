/*
 * Copyright (C) 2011 Patrick Strang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package se.su.dsv.scipro.android.activity;

import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.R.layout;
import se.su.dsv.scipro.android.adapter.ProjectListAdapter;
import se.su.dsv.scipro.android.dummydata.DummyData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SupervisorHomeActivity extends SciProListActivity {
    
    private static final int SHOW_PROJECT = 1;
    
    private ProjectListAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_home);
        
        setUpViews();
        adapter = new ProjectListAdapter(DummyData.getInstance().getProjects(), this);
        setListAdapter(adapter);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, ProjectViewActivity.class);
        intent.putExtra("project", adapter.getItem(position));
        startActivityForResult(intent, SHOW_PROJECT);
    }

    private void setUpViews() {
        
    }

}