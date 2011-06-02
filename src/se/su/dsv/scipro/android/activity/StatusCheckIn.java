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

package se.su.dsv.scipro.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import se.su.dsv.scipro.android.IHeaderOnClick;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.utils.SciProUtils;

/**
 * User: patrick
 * Date: 2011-05-23
 * Time: 11:46
 */
public class StatusCheckIn extends Activity implements IHeaderOnClick {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_status_checkin);
        
        setUpViews();
      
    }

    private void setUpViews() {
        ImageButton homeButton = (ImageButton) findViewById(R.id.header_home_btn);
        homeButton.setVisibility(View.VISIBLE);
    }

    public void onHeaderHomeClick(View v) {
        SciProUtils.openHomeActivity(this);
    }

    public void onHeaderMessagesClick(View v) {
        SciProUtils.openMessagesActivity(this);
    }

}