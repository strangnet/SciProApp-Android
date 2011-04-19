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
