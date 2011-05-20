package se.su.dsv.scipro.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import se.su.dsv.scipro.android.R;

/**
 * User: patrick
 * Date: 2011-05-20
 * Time: 16:02
 */
public class Authenticate extends Activity {

    public static final String TAG = "Authenticate";
    private TextView usernameText;
    private TextView passwordText;
    private TextView apikeyText;
    private Button authenticateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        setUpViews();
    }

    private void setUpViews() {
        usernameText = (TextView) findViewById(R.id.auth_username_entry);
        passwordText = (TextView) findViewById(R.id.auth_password_entry);
        apikeyText = (TextView) findViewById(R.id.auth_apikey_entry);
        authenticateButton = (Button) findViewById(R.id.auth_button);
    }
}
