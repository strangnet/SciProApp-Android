package se.su.dsv.scipro.android.views;

import se.su.dsv.scipro.android.dao.Project;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProjectListItem extends LinearLayout {
    
    private Project project;
    private TextView textView;

    public ProjectListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        textView.setText(project.getTitle());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) findViewById(android.R.id.text1);
    }

}
