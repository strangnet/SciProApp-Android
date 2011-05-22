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

package se.su.dsv.scipro.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.utils.DaoUtils;
import se.su.dsv.scipro.android.dao.Project;

import java.util.List;

public class ProjectListAdapter extends BaseAdapter {
    
    private final List<Project> projects;
    
    public ProjectListAdapter(List<Project> projects) {
        this.projects = projects;
    }
    
    public int getCount() {
        return projects.size();
    }

    public Project getItem(int position) {
        return projects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup item = getViewGroup(convertView, parent);
        
        TextView title = (TextView) item.findViewById(R.id.project_list_item_title);
        TextView misc = (TextView) item.findViewById(R.id.project_list_item_misc);
        ImageView status = (ImageView) item.findViewById(R.id.project_list_item_status);
        
        Project project = getItem(position);
        title.setText(project.title);
        misc.setText(DaoUtils.projectMembersAsString(project));
        switch (project.status) {
        case NEEDHELP:
            status.setImageResource(R.drawable.red_ball_medium);
            break;
        case FINE:
            status.setImageResource(R.drawable.green_ball_medium);
            break;
        case NEUTRAL:
        default:
            status.setImageResource(R.drawable.yellow_ball_medium);
        }
        status.setAdjustViewBounds(true);
        
        return item;
    }
    
    private ViewGroup getViewGroup(View convertView, ViewGroup parent) {
        
        if (convertView instanceof ViewGroup)
            return (ViewGroup) convertView;
        
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup item = (ViewGroup) inflater.inflate(R.layout.list_item_project, null);
        
        return item;
    }
}
