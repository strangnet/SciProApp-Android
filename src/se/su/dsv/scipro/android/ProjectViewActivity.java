package se.su.dsv.scipro.android;

import android.os.Bundle;
import android.widget.TextView;
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
