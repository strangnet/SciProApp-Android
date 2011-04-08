package se.su.dsv.scipro.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewMessageActivity extends SciProActivity {

    private TextView recipientField;
    private TextView subjectField;
    private Button submitButton;

    protected boolean changesPending;
    private AlertDialog unsavedChangesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_message);

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
        recipientField = (TextView) findViewById(R.id.to_edit);
        subjectField = (TextView) findViewById(R.id.subject_edit);
        submitButton = (Button) findViewById(R.id.send_message);

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
