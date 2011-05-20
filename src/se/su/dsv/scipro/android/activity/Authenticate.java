package se.su.dsv.scipro.android.activity;

import android.app.Activity;
import android.os.Bundle;
import se.su.dsv.scipro.android.R;

/**
 * User: patrick
 * Date: 2011-05-20
 * Time: 16:02
 */
public class Authenticate extends Activity {

    public static final String TAG = "Authenticate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        setUpViews();
    }

    private void setUpViews() {

    }
}
