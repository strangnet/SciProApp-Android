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

package se.su.dsv.scipro.android.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.dao.PrivateMessage;

public class MessageListItem extends RelativeLayout {
    
    private PrivateMessage message;
    private TextView messageTitleTextView;
    private TextView messageDateTextView;

    public MessageListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrivateMessage getMessage() {
        return message;
    }

    public void setMessage(PrivateMessage message) {
        this.message = message;
        messageTitleTextView.setText(message.subject);
        if (!message.read) {
            messageTitleTextView.setTypeface(null, Typeface.BOLD);
        }
        messageDateTextView.setText(message.date);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        messageTitleTextView = (TextView) findViewById(R.id.list_item_message_title);
        messageDateTextView = (TextView) findViewById(R.id.list_item_message_date);
    }

}
