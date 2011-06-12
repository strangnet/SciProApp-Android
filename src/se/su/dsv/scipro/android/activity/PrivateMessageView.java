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
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import se.su.dsv.scipro.android.IHeaderOnClick;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.dao.PrivateMessage;
import android.os.Bundle;
import android.widget.TextView;
import se.su.dsv.scipro.android.helpers.MenuHelper;
import se.su.dsv.scipro.android.tasks.SetMessageReadAsyncTask;
import se.su.dsv.scipro.android.utils.SciProUtils;

public class PrivateMessageView extends Activity implements IHeaderOnClick, SetMessageReadAsyncTask.MessageReadResponder {

    private static final int REPLY_MESSAGE = 3;

    private PrivateMessage message;
    private TextView subjectText;
    private TextView fromText;
    private TextView messageText;
    private Button replyButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        message = (PrivateMessage) bundle.getSerializable("message");
        setContentView(R.layout.activity_message);

        new SetMessageReadAsyncTask(this, message).execute();
        
        setUpViews();
    }

    private void setUpViews() {
        subjectText = (TextView) findViewById(R.id.message_subject);
        subjectText.setText(message.subject);
        fromText = (TextView) findViewById(R.id.message_from);
        fromText.setText("From: " + message.from.toString());
        messageText = (TextView) findViewById(R.id.message_text);
        messageText.setText(message.message);

        replyButton = (Button) findViewById(R.id.reply_message_button);
        replyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PrivateMessageView.this, NewMessage.class);
                intent.putExtra("message", message);
                startActivityForResult(intent, REPLY_MESSAGE);
            }
        });

        ImageButton homeButton = (ImageButton) findViewById(R.id.header_home_btn);
        homeButton.setVisibility(View.VISIBLE);
    }

    public void onHeaderHomeClick(View v) {
        SciProUtils.openHomeActivity(this);
    }

    public void onHeaderMessagesClick(View v) {
        SciProUtils.openMessagesActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuHelper.openActivityFromMenuItem(this, item);
    }

    public void settingStatus() {

    }

    public void statusIsSet(SetMessageReadAsyncTask.MessageReadResult result) {
        message.read = result.read;
    }
}
