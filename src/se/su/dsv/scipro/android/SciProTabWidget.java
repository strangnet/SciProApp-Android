package se.su.dsv.scipro.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class SciProTabWidget extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_view);
        
//        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent = new Intent().setClass(this, SupervisorHomeActivity.class);
        spec = tabHost.newTabSpec("home").setIndicator("Home").setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, PrivateMessagesViewActivity.class);
        spec = tabHost.newTabSpec("messages").setIndicator("Messages").setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
    }    
    
}
