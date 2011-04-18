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

package se.su.dsv.scipro.android;

import se.su.dsv.scipro.android.adapters.MessageListAdapter;
import se.su.dsv.scipro.android.dummydata.DummyData;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class PrivateMessagesViewActivity extends SciProListActivity {

    private static final int SHOW_MESSAGE = 1;
    private static final int NEW_MESSAGE = 2;
    private MessageListAdapter adapter;
    private Button submitButton;
    
    /**
     * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        
        switch (item.getItemId()) {
        case R.id.messages_context_view:
            Intent intent = new Intent(this, PrivateMessageViewActivity.class);
            intent.putExtra("message", adapter.getItem(info.position));
            startActivityForResult(intent, SHOW_MESSAGE);
            return true;
        case R.id.messages_context_delete:
            adapter.deleteMessage(info.position);
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
        setContentView(R.layout.message_list);
        
        setUpViews();
        adapter = new MessageListAdapter(DummyData.getInstance().getMessages(), this);
        setListAdapter(adapter);
        
        registerForContextMenu(getListView());
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
