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

package se.su.dsv.scipro.android.adapters;

import java.util.ArrayList;

import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.dao.PrivateMessage;
import se.su.dsv.scipro.android.views.MessageListItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class MessageListAdapter extends BaseAdapter {
    
    private ArrayList<PrivateMessage> messages;
    private Context context;
    
    public MessageListAdapter(ArrayList<PrivateMessage> messages, Context context) {
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
            mli = (MessageListItem) View.inflate(context, R.layout.message_list_item, null);
        } else {
            mli = (MessageListItem) convertView;
        }
        mli.setMessage(messages.get(position));
        return mli;
    }

    public void deleteMessage(int position) {
        messages.remove(getItem(position));
        Toast toast = Toast.makeText(context, "Message deleted", 1500);
        toast.show();
        notifyDataSetChanged();
    }

}
