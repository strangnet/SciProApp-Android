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
import se.su.dsv.scipro.android.dao.User;
import se.su.dsv.scipro.android.json.SciProJSON;

import java.util.List;

/**
 * User: patrick
 * Date: 2011-05-22
 * Time: 23:24
 */
public class SendMessageAsyncTask extends AsyncTask<Void, Void, SendMessageAsyncTask.SentMessageResult> {

    public interface SentMessageResponder {
        public void sendingMessage();

        public void messageSent(SentMessageResult result);
    }

    public class SentMessageResult {
        public boolean authenticated;

        public SentMessageResult(boolean authenticated) {
            this.authenticated = authenticated;
        }
    }

    public static final String TAG = "SendMessageAsyncTask";

    private SentMessageResponder responder;
    private String subject;
    private String message;
    private List<User> recipients;

    public SendMessageAsyncTask(SentMessageResponder responder, String subject, String message, List<User> recipients) {
        this.responder = responder;
        this.subject = subject;
        this.message = message;
        this.recipients = recipients;
    }

    @Override
    protected SentMessageResult doInBackground(Void... params) {
        String response = SciProJSON.getInstance().sendMessage(subject, message, recipients);

        boolean authenticated = true;

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (!jsonObject.getString("apikey").equals("success")) {
                authenticated = false;
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: ", e);
        }

        return new SentMessageResult(authenticated);
    }

    @Override
    protected void onPostExecute(SentMessageResult result) {
        super.onPostExecute(result);
        responder.messageSent(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        responder.sendingMessage();
    }
}
