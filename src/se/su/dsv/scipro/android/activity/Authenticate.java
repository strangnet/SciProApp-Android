package se.su.dsv.scipro.android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.SciProApplication;
import se.su.dsv.scipro.android.tasks.AuthAsyncTask;
import se.su.dsv.scipro.android.tasks.LoginAsyncTask;
import se.su.dsv.scipro.android.tasks.LoginResponder;
import se.su.dsv.scipro.android.tasks.LoginResult;

/**
 * User: patrick
 * Date: 2011-05-20
 * Time: 16:02
 */
public class Authenticate extends Activity implements LoginResponder {

    public static final String TAG = "Authenticate";
    private TextView usernameText;
    private TextView passwordText;
    private TextView apikeyText;
    private Button authenticateButton;
    private AlertDialog authenticateInfoDialog;
    private ProgressDialog authenticationInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        setUpViews();
        createAuthenticateInfoDialog();
    }

    private void createAuthenticateInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SciPro Authentication");
        builder.setMessage("You can either use your SciPro password to authenticate or the pre-generated API-key. If " +
                "you have no API-key, one will be generated for you when isAuthenticated with your SciPro password.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        authenticateInfoDialog = builder.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        authenticateInfoDialog.show();
    }

    private void setUpViews() {
        usernameText = (TextView) findViewById(R.id.auth_username_entry);
        passwordText = (TextView) findViewById(R.id.auth_password_entry);
        apikeyText = (TextView) findViewById(R.id.auth_apikey_entry);
        authenticateButton = (Button) findViewById(R.id.auth_button);

        authenticateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                String apikey = apikeyText.getText().toString();

                if (username.length() > 0) {
                    boolean passwordIsSet = password.length() > 0;
                    boolean apikeyIsSet = apikey.length() > 0;
                    if (passwordIsSet || apikeyIsSet) {
                        if (passwordIsSet) {
                            new LoginAsyncTask(Authenticate.this, username, password).execute();
                        } else {
                            new AuthAsyncTask(Authenticate.this, username, apikey).execute();
                        }
                    }
                }
            }
        });
    }

    public void loggingIn() {
        authenticationInProgress = ProgressDialog.show(Authenticate.this,
                "Authenticating",
                "Authentication with SciPro in progress...");
    }

    public void loggedIn(LoginResult result) {
        authenticationInProgress.dismiss();

        if(result.authenticated) {
            SciProApplication.getInstance().setUsername(result.username);
            SciProApplication.getInstance().setApiKey(result.apikey);
            SciProApplication.getInstance().setUserid(result.userid);
            finish();
        }
    }
}
