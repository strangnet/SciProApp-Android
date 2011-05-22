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

package se.su.dsv.scipro.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import se.su.dsv.scipro.android.dao.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SciProApplication extends Application {

    private static SciProApplication instance;
    private SharedPreferences preferences;

    private Map<Long, User> userMap = new HashMap<Long, User>();

    public static SciProApplication getInstance() {
        return instance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    }

    public boolean isAuthenticated() {
        return getUserId() != -1 && !getUsername().equals("") && !getApiKey().equals("");
    }

    public String getUsername() {
        return preferences.getString(Preferences.PREF_USERNAME, null);
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Preferences.PREF_USERNAME, username);
        editor.commit();
    }

    public String getApiKey() {
        return preferences.getString(Preferences.PREF_APIKEY, null);
    }

    public void setApiKey(String apiKey) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Preferences.PREF_APIKEY, apiKey);
        editor.commit();
    }

    public long getUserId() {
        return preferences.getLong(Preferences.PREF_USERID, -1);
    }

    public void setUserid(long userid) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(Preferences.PREF_USERID, userid);
        editor.commit();
    }

    public void logout() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Preferences.PREF_USERID).remove(Preferences.PREF_USERNAME).remove(Preferences.PREF_APIKEY);
        editor.commit();
    }

    public List<User> getUsers() {
        return new ArrayList<User>(userMap.values());
    }

    public void addUser(User user) {
        if (userMap == null)
            userMap = new HashMap<Long, User>();
        userMap.put(user.id, user);
    }
}
