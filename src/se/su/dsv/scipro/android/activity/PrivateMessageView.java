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
import se.su.dsv.scipro.android.dao.PrivateMessage;
import android.os.Bundle;
import android.widget.TextView;

public class PrivateMessageView extends SciProActivity {

    private PrivateMessage message;
    private TextView subjectText;
    private TextView fromText;
    private TextView messageText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        message = (PrivateMessage) bundle.getSerializable("message");
        setContentView(R.layout.view_message);
        
        setUpViews();
    }

    private void setUpViews() {
        subjectText = (TextView) findViewById(R.id.message_subject);
        subjectText.setText(message.getSubject());
        fromText = (TextView) findViewById(R.id.message_from);
        fromText.setText("From: " + message.getFromUser().toString());
        messageText = (TextView) findViewById(R.id.message_text);
        messageText.setText(message.getMessage());
    }
        
}
