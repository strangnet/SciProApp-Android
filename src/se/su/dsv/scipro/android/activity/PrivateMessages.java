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

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageButton;
import android.widget.ListView;
import se.su.dsv.scipro.android.IHeaderOnClick;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.adapters.MessageListAdapter;
import se.su.dsv.scipro.android.helpers.MenuHelper;
import se.su.dsv.scipro.android.tasks.GetMessagesAsyncTask;
import se.su.dsv.scipro.android.utils.SciProUtils;

public class PrivateMessages extends ListActivity implements IHeaderOnClick, GetMessagesAsyncTask.MessagesResponder {

    private static final int SHOW_MESSAGE = 1;
    private static final int NEW_MESSAGE = 2;
    private MessageListAdapter adapter;
    private ProgressDialog messagesRetrievalInProgress;
    
    /**
     * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        
        switch (item.getItemId()) {
        case R.id.messages_context_view:
            Intent intent = new Intent(this, PrivateMessageView.class);
            intent.putExtra("message", adapter.getItem(info.position));
            startActivityForResult(intent, SHOW_MESSAGE);
            return true;
        case R.id.messages_context_mark_read:
            adapter.setMessageRead(info.position);
            return true;
        default:
            return super.onContextItemSelected(item);
        }
    }

    /**
     * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.messages_context_menu, menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_message);
        
        setUpViews();
//        adapter = new MessageListAdapter(new ArrayList<PrivateMessage>(), this);
//        setListAdapter(adapter);
        new GetMessagesAsyncTask(this).execute();
        
        registerForContextMenu(getListView());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, PrivateMessageView.class);
        intent.putExtra("message", adapter.getItem(position));
        startActivityForResult(intent, SHOW_MESSAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUpViews() {
        ImageButton messagesButton = (ImageButton) findViewById(R.id.header_message_btn);
        messagesButton.setVisibility(View.INVISIBLE);

        ImageButton homeButton = (ImageButton) findViewById(R.id.header_home_btn);
        homeButton.setVisibility(View.VISIBLE);

        ImageButton newMessageButton = (ImageButton) findViewById(R.id.header_new_message_btn);
        newMessageButton.setVisibility(View.VISIBLE);
        newMessageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onHeaderNewMessageClick(v);
            }
        });
    }
    
    public void onHeaderHomeClick(View v) {
        SciProUtils.openHomeActivity(this);
    }

    public void onHeaderMessagesClick(View v) {
        SciProUtils.openMessagesActivity(this);
    }

    public void onHeaderNewMessageClick(View v) {
        Intent intent = new Intent(PrivateMessages.this, NewMessage.class);
        startActivityForResult(intent, NEW_MESSAGE);
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

    public void retrievingMessages() {
        messagesRetrievalInProgress = ProgressDialog.show(this,
                "Loading Messages",
                "Retrieving messages from SciPro.");
    }

    public void retrievedMessages(GetMessagesAsyncTask.MessagesResult result) {
        messagesRetrievalInProgress.dismiss();

        adapter = new MessageListAdapter(result.messages, this);
        setListAdapter(adapter);
    }
}
