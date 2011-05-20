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
import se.su.dsv.scipro.android.dao.User;
import se.su.dsv.scipro.android.dummydata.DummyData;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class NewMessage extends SciProActivity {

    private MultiAutoCompleteTextView recipientField;
    private TextView subjectField;
    private Button submitButton;

    protected boolean changesPending;
    private AlertDialog unsavedChangesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        finish();
    }
    
    protected boolean validInputFields() {
        return true;
    }

    private void setUpViews() {
        recipientField = (MultiAutoCompleteTextView) findViewById(R.id.to_edit);
        subjectField = (TextView) findViewById(R.id.subject_edit);
        submitButton = (Button) findViewById(R.id.send_message);
        
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, 
                android.R.layout.simple_dropdown_item_1line, DummyData.getInstance().getUsers());
        recipientField.setAdapter(adapter);

        recipientField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changesPending = true;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });

        subjectField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changesPending = true;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validInputFields()) {
                    sendMessage();
                } else {
                    
                }
            }
        });
    }
    
}
