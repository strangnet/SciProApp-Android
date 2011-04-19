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

import android.os.Bundle;
import android.widget.TextView;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.R.id;
import se.su.dsv.scipro.android.R.layout;
import se.su.dsv.scipro.android.dao.Project;

public class ProjectViewActivity extends SciProActivity {
    
    private Project project;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        project = (Project) bundle.getSerializable("project");
        setContentView(R.layout.view_project);
        
        setUpViews();
    }

    private void setUpViews() {
        titleText = (TextView) findViewById(R.id.project_title);
        titleText.setText(project.getTitle());
    }
    
}
