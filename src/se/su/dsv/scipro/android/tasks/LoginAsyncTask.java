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

package se.su.dsv.scipro.android.tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import se.su.dsv.scipro.android.json.SciProJSON;

/**
 * User: patrick
 * Date: 2011-05-22
 * Time: 02:06
 */
public class LoginAsyncTask extends AsyncTask<Void, Void, LoginResult> {

    public static final String TAG = "LoginAsyncTask";

    private LoginResponder responder;
    private String username;
    private String password;

    public LoginAsyncTask(LoginResponder responder, String username, String password) {
        super();
        this.responder = responder;
        this.username = username;
        this.password = password;
    }

    @Override
    protected LoginResult doInBackground(Void... params) {
        String response = SciProJSON.getInstance().jsonLogin(username, password);

        boolean authenticated = false;
        String apikey = "";
        long userid = -1;
        try {
            JSONObject jsonObject = new JSONObject(response);
            authenticated = jsonObject.getBoolean("authenticated");
            if (authenticated) {
                apikey = jsonObject.getString("apikey");
                userid = jsonObject.getLong("userid");
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: ", e);
        }

        return new LoginResult(authenticated, username, apikey, userid);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        responder.loggingIn();
    }

    @Override
    protected void onPostExecute(LoginResult result) {
        super.onPostExecute(result);
        responder.loggedIn(result);
    }
}
