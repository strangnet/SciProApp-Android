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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import se.su.dsv.scipro.android.dao.PrivateMessage;
import se.su.dsv.scipro.android.dao.User;
import se.su.dsv.scipro.android.json.SciProJSON;

import java.util.ArrayList;
import java.util.List;

/**
 * User: patrick
 * Date: 2011-05-22
 * Time: 14:12
 */
public class GetMessagesAsyncTask extends AsyncTask<Void, Void, GetMessagesAsyncTask.MessagesResult> {

    public interface MessagesResponder {
        public void retrievingMessages();

        public void retrievedMessages(MessagesResult result);
    }

    public class MessagesResult {
        public boolean authenticated;
        public List<PrivateMessage> messages;

        public MessagesResult(boolean authenticated, List<PrivateMessage> messages) {
            this.authenticated = authenticated;
            this.messages = messages;
        }
    }

    public static final String TAG = "GetMessagesAsyncTask";

    private MessagesResponder responder;

    public GetMessagesAsyncTask(MessagesResponder responder) {
        this.responder = responder;
    }

    @Override
    protected MessagesResult doInBackground(Void... params) {
        String response = SciProJSON.getInstance().getMessages();

        boolean authenticated = true;
        List<PrivateMessage> messages = new ArrayList<PrivateMessage>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (!jsonObject.getString("apikey").equals("success")) {
                authenticated = false;
            } else {
                JSONArray messagesArray = jsonObject.getJSONArray("messageArray");
                for (int i = 0; i < messagesArray.length(); i++) {
                    JSONObject currentMessage = messagesArray.getJSONObject(i);

                    long id = currentMessage.getLong("id");
                    String subject = currentMessage.getString("subject");
                    String message = currentMessage.getString("message");
                    String date = currentMessage.getString("date");
                    boolean read = currentMessage.getBoolean("read");
                    JSONObject fromUser = currentMessage.getJSONObject("from");
                    User from = new User(fromUser.getLong("id"), fromUser.getString("name"));

                    messages.add(new PrivateMessage(id, subject, message, from, date, read));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: ", e);
        }

        return new MessagesResult(authenticated, messages);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        responder.retrievingMessages();
    }

    @Override
    protected void onPostExecute(MessagesResult result) {
        super.onPostExecute(result);
        responder.retrievedMessages(result);
    }
}
