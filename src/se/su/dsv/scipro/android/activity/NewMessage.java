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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import se.su.dsv.scipro.android.IHeaderOnClick;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.SciProApplication;
import se.su.dsv.scipro.android.dao.PrivateMessage;
import se.su.dsv.scipro.android.dao.User;
import se.su.dsv.scipro.android.helpers.MenuHelper;
import se.su.dsv.scipro.android.tasks.SendMessageAsyncTask;
import se.su.dsv.scipro.android.utils.SciProUtils;

import java.util.ArrayList;
import java.util.List;

public class NewMessage extends Activity implements IHeaderOnClick, SendMessageAsyncTask.SentMessageResponder {

    public static final String TAG = "NewMessage";

    private MultiAutoCompleteTextView recipientField;
    private TextView subjectField;
    private EditText messageTextField;
    private Button submitButton;
    private PrivateMessage replyToMessage;

    private ProgressDialog sendingMessageInProgress;

    protected boolean changesPending;
    private AlertDialog unsavedChangesDialog;
    private ArrayAdapter<User> adapter;
    private List<User> selectedRecipients = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("message") != null) {
                replyToMessage = (PrivateMessage) bundle.getSerializable("message");
            }
        }

        setContentView(R.layout.activity_compose_message);

        setUpViews();
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    protected void cancel() {
        if (changesPending) {
            unsavedChangesDialog = new AlertDialog.Builder(this)
                    .setTitle("Changes made")
                    .setMessage("You have made changes.")
                    .setPositiveButton(R.string.send_message, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            sendMessage();
                        }
                    })
                    .setNeutralButton(R.string.discard, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            unsavedChangesDialog.cancel();
                        }
                    })
                    .create();
            unsavedChangesDialog.show();
        } else {
            finish();
        }
    }

    protected void sendMessage() {

    }

    protected boolean validInputFields() {
        return true;
    }

    private void setUpViews() {
        recipientField = (MultiAutoCompleteTextView) findViewById(R.id.to_edit);
        subjectField = (TextView) findViewById(R.id.subject_edit);
        messageTextField = (EditText) findViewById(R.id.message_edit);
        submitButton = (Button) findViewById(R.id.send_message);

        adapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_dropdown_item_1line, SciProApplication.getInstance().getUsers());
        recipientField.setAdapter(adapter);
        recipientField.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        recipientField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changesPending = true;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        recipientField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u = (User) (parent.getItemAtPosition(position));
                Log.d(TAG, "Added " + u.name + " to list");
                selectedRecipients.add(u);
            }
        });

        subjectField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changesPending = true;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        if (replyToMessage != null) {
            subjectField.setText("Re: " + replyToMessage.subject);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validInputFields()) {
//                    Log.d(TAG, subjectField.getText().toString());
//                    Log.d(TAG, messageTextField.getText().toString());
//                    Log.d(TAG, "" + selectedRecipients.size());
                    new SendMessageAsyncTask(NewMessage.this,
                            subjectField.getText().toString(),
                            messageTextField.getText().toString(),
                            selectedRecipients)
                            .execute();
                } else {

                }
            }
        });

        ImageButton homeButton = (ImageButton) findViewById(R.id.header_home_btn);
        homeButton.setVisibility(View.VISIBLE);
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

    public void onHeaderHomeClick(View v) {
        SciProUtils.openHomeActivity(this);
    }

    public void onHeaderMessagesClick(View v) {
        SciProUtils.openMessagesActivity(this);
    }

    public void messageSent(SendMessageAsyncTask.SentMessageResult result) {
        sendingMessageInProgress.dismiss();

        finish();
    }

    public void sendingMessage() {
        sendingMessageInProgress = ProgressDialog.show(this,
                "Sending",
                "Sending message.");
    }
}
