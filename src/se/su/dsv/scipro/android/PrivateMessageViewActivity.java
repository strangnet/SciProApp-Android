package se.su.dsv.scipro.android;

import se.su.dsv.scipro.android.dao.PrivateMessage;
import android.os.Bundle;
import android.widget.TextView;

public class PrivateMessageViewActivity extends SciProActivity {

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
        fromText.setText("From: " + message.getFromUser().getName());
        messageText = (TextView) findViewById(R.id.message_text);
        messageText.setText(message.getMessage());
    }
        
}
