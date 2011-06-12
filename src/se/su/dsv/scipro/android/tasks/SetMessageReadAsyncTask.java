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
import se.su.dsv.scipro.android.dao.PrivateMessage;
import se.su.dsv.scipro.android.json.SciProJSON;

/**
 * User: patrick
 * Date: 2011-05-30
 * Time: 11:36
 */
public class SetMessageReadAsyncTask extends AsyncTask<Void, Void, SetMessageReadAsyncTask.MessageReadResult> {

    public interface MessageReadResponder {
        public void settingStatus();

        public void statusIsSet(MessageReadResult result);
    }

    public class MessageReadResult {
        public boolean read;

        public MessageReadResult(boolean read) {
            this.read = read;
        }
    }

    public static final String TAG = "SetMessageReadAsyncTask";

    private MessageReadResponder responder;
    private PrivateMessage message;

    public SetMessageReadAsyncTask(MessageReadResponder responder, PrivateMessage message) {
        this.responder = responder;
        this.message = message;
    }

    @Override
    protected MessageReadResult doInBackground(Void... params) {
        String response = SciProJSON.getInstance().setMessageRead(message);

        boolean authenticated = true;

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (!jsonObject.getString("apikey").equals("success")) {
                authenticated = false;
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: ", e);
        }

        return new MessageReadResult(authenticated);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        responder.settingStatus();
    }

    @Override
    protected void onPostExecute(MessageReadResult result) {
        super.onPostExecute(result);
        responder.statusIsSet(result);
    }
}
