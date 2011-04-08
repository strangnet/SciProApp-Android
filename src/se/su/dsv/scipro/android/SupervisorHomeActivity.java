package se.su.dsv.scipro.android;

import se.su.dsv.scipro.android.adapters.ProjectListAdapter;
import se.su.dsv.scipro.android.dummydata.DummyData;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SupervisorHomeActivity extends ListActivity {
    
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
        
    }

    private void setUpViews() {
        
    }

}