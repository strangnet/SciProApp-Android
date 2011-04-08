package se.su.dsv.scipro.android.views;

import se.su.dsv.scipro.android.dao.PrivateMessage;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MessageListItem extends LinearLayout {
    
    private PrivateMessage message;

    public MessageListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrivateMessage getMessage() {
        return message;
    }

    public void setMessage(PrivateMessage message) {
        this.message = message;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

}
