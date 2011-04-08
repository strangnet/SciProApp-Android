package se.su.dsv.scipro.android.adapters;

import java.util.ArrayList;

import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.dao.Project;
import se.su.dsv.scipro.android.views.ProjectListItem;

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
