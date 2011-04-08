package se.su.dsv.scipro.android.adapters;

import java.util.ArrayList;

import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.dao.PrivateMessage;
import se.su.dsv.scipro.android.views.MessageListItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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

}
