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
 * Date: 2011-05-30
 * Time: 11:59
 */
public class SetStatusAsyncTask extends AsyncTask<Void, Void, SetStatusAsyncTask.StatusResult> {

    public interface StatusResponder {
        public void checkingIn();

        public void checkedIn(StatusResult result);
    }

    public class StatusResult {
        public boolean authenticated;
        public boolean checkedIn;
        public String status;

        public StatusResult(boolean authenticated, boolean checkedIn, String status) {
            this.authenticated = authenticated;
            this.checkedIn = checkedIn;
            this.status = status;
        }
    }

    public static final String TAG = "SetStatusAsyncTask";

    private StatusResponder responder;
    private boolean checkedIn;
    private String status;

    public SetStatusAsyncTask(StatusResponder responder, boolean checkedIn, String status) {
        this.responder = responder;
        this.checkedIn = checkedIn;
        this.status = status;
    }

    @Override
    protected StatusResult doInBackground(Void... params) {
        String response = SciProJSON.getInstance().setCheckinStatus(checkedIn, status);

        boolean authenticated = true;

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (!jsonObject.getString("apikey").equals("success")) {
                authenticated = false;
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: ", e);
        }

        return new StatusResult(authenticated, checkedIn, status);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        responder.checkingIn();
    }

    @Override
    protected void onPostExecute(StatusResult result) {
        super.onPostExecute(result);
        responder.checkedIn(result);
    }
}
