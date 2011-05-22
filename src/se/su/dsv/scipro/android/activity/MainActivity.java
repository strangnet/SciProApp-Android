package se.su.dsv.scipro.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import se.su.dsv.scipro.android.SciProApplication;

/**
 * User: patrick
 * Date: 2011-05-21
 * Time: 22:49
 */
public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SciProApplication.getInstance().isAuthenticated()) {
            Intent intent = new Intent(this, Authenticate.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SupervisorHome.class);
            startActivity(intent);
            finish();
        }
    }
}