package se.su.dsv.scipro.android;

import se.su.dsv.scipro.android.adapters.MessageListAdapter;
import se.su.dsv.scipro.android.dummydata.DummyData;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class PrivateMessagesViewActivity extends ListActivity {

    private static final int SHOW_MESSAGE = 1;
    private static final int NEW_MESSAGE = 2;
    private MessageListAdapter adapter;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        
        setUpViews();
        adapter = new MessageListAdapter(DummyData.getInstance().getMessages(), this);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, PrivateMessageViewActivity.class);
        intent.putExtra("message", adapter.getItem(position));
        startActivityForResult(intent, SHOW_MESSAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUpViews() {
        submitButton = (Button) findViewById(R.id.new_message_button);
        
        submitButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                Intent intent = new Intent(PrivateMessagesViewActivity.this, NewMessageActivity.class);
                startActivityForResult(intent, NEW_MESSAGE);
            }
        });
    }
    
    
}
