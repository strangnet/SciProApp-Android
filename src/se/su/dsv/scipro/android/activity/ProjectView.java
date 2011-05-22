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

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import se.su.dsv.scipro.android.IHeaderOnClick;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.dao.Project;
import se.su.dsv.scipro.android.dao.User;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import se.su.dsv.scipro.android.helpers.MenuHelper;
import se.su.dsv.scipro.android.utils.SciProUtils;

public class ProjectView extends Activity implements IHeaderOnClick {
    
    private Project project;
    private TextView titleText;
    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        project = (Project) bundle.getSerializable("project");
        setContentView(R.layout.activity_project);
        
        setUpViews();
    }

    private void setUpViews() {
        titleText = (TextView) findViewById(R.id.project_title);
        titleText.setText(project.getTitle());
        ImageButton homeButton = (ImageButton) findViewById(R.id.header_home_btn);
        homeButton.setVisibility(View.VISIBLE);
        infoText = (TextView) findViewById(R.id.project_data_info);
        String projectInfo = "Member(s):\n";
        for (User u : project.getMembers())
            projectInfo += u.getName() + "\n";
        projectInfo += "\nStatus: ";
        Project.STATUS status = project.getStatus();
        switch (status) {
        case NEGATIVE:
            projectInfo += "Negative\n";
            break;
        case POSITIVE:
            projectInfo += "Positive\n";
            break;
        case NEUTRAL:
        default:
            projectInfo += "Neutral\n";
        }
        
        projectInfo += "\nNo new Message Board Messages.";
        infoText.setText(projectInfo);
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
}
