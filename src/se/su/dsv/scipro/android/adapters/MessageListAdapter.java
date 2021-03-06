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

package se.su.dsv.scipro.android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.dao.PrivateMessage;
import se.su.dsv.scipro.android.json.SciProJSON;
import se.su.dsv.scipro.android.view.MessageListItem;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {
    
    private List<PrivateMessage> messages;
    private Context context;
    
    public MessageListAdapter(List<PrivateMessage> messages, Context context) {
        super();
        this.messages = messages;
        this.context = context;
    }

    public int getCount() {
        return messages.size();
    }

    public PrivateMessage getItem(int position) {
        return messages == null ? null : messages.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MessageListItem mli;
        if (convertView == null) {
            mli = (MessageListItem) View.inflate(context, R.layout.list_item_message, null);
        } else {
            mli = (MessageListItem) convertView;
        }
        mli.setMessage(messages.get(position));
        return mli;
    }

    public void setMessageRead(int position) {
        final PrivateMessage message = getItem(position);
        message.read = true;
        new Thread() {
            @Override
            public void run() {
                SciProJSON.getInstance().setMessageRead(message);
            }
        }.start();
        Toast.makeText(context, "Message marked as read", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

}
