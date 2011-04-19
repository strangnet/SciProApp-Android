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

package se.su.dsv.scipro.android.adapter;

import java.util.ArrayList;

import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.dao.Project;
import se.su.dsv.scipro.android.view.ProjectListItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProjectListAdapter extends BaseAdapter {

    private ArrayList<Project> projects;
    private Context context;
    
    public ProjectListAdapter(ArrayList<Project> projects, Context context) {
        super();
        this.projects = projects;
        this.context = context;
    }
    
    public int getCount() {
        return projects.size();
    }

    public Project getItem(int position) {
        return projects == null ? null : projects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectListItem pli;
        if (convertView == null) {
            pli = (ProjectListItem) View.inflate(context, R.layout.project_list_item, null);
        } else {
            pli = (ProjectListItem) convertView;
        }
        pli.setProject(projects.get(position));
        return pli;
    }

}
