/*
 * Copyright (C) 2011 Patrick Strang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package se.su.dsv.scipro.android.json;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import se.su.dsv.scipro.android.SciProApplication;
import se.su.dsv.scipro.android.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SciProJSON {
    
    private static final String SCIPRO_JSON_ADDRESS = "http://192.168.1.2:8080/SciPro/json/";
    private static final String TAG = "SciProJSON";

    private static SciProJSON instance;
    public static SciProJSON getInstance() {
        if (instance == null) {
            instance = new SciProJSON();
        }
        return instance;
    }

    private DefaultHttpClient httpClient;
    
    private SciProJSON() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000); 
        httpClient = new DefaultHttpClient(params);
        BasicCookieStore cookieStore = new BasicCookieStore();
        httpClient.setCookieStore(cookieStore);
    }
    
    public String getProjects() {
        String uri = SCIPRO_JSON_ADDRESS + "project";
        
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("userid", String.valueOf(SciProApplication.getInstance().getUserId())));
        postParams.add(new BasicNameValuePair("apikey", SciProApplication.getInstance().getApiKey()));

        String result = getJson(uri, postParams);

        Log.d(TAG, "getProjects: " + result);

        return result;
    }

    public String jsonLogin(String username, String password) {
        String uri = SCIPRO_JSON_ADDRESS + "login";

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("username", username);
            jsonObj.put("password", password);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e);
        }

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("json", jsonObj.toString()));

        String result = getJson(uri, postParams);

        Log.d(TAG, "jsonLogin: " + result);

        return result;
    }

    public String jsonAuth() {
        return jsonAuth(SciProApplication.getInstance().getUsername(),
                SciProApplication.getInstance().getApiKey());
    }

    public String jsonAuth(String username, String apikey) {
        String uri = SCIPRO_JSON_ADDRESS + "auth";
        
        JSONObject jsonObj = new JSONObject();
        
        try {
            jsonObj.put("username", username);
            jsonObj.put("apikey", apikey);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e);
        }
        
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("json", jsonObj.toString()));

        String result = getJson(uri, postParams);

        Log.d(TAG, "jsonAuth: " + result);

        return result;
    }

    public String getMessages() {
        String uri = SCIPRO_JSON_ADDRESS + "message";

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("userid", String.valueOf(SciProApplication.getInstance().getUserId())));
        postParams.add(new BasicNameValuePair("apikey", SciProApplication.getInstance().getApiKey()));

        String result = getJson(uri, postParams);

        Log.d(TAG, "getMessages: " + result);

        return result;
    }

    private String getJson(String uri, List<NameValuePair> postParams) {
        String result = "";
        HttpPost httpPost = new HttpPost(uri);
        String contentType = "application/json";

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
            entity.setContentEncoding(HTTP.UTF_8);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", contentType);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException: " + e);
        }

        try {
            HttpResponse httpResponse = httpClient.execute(httpPost, new BasicHttpContext());
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null) {
                InputStream is = httpEntity.getContent();
                result = StringUtils.convertStreamToString(is);
            }
        } catch (ClientProtocolException e) {
            Log.e(TAG, "ClientProtocolException: ", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException: ", e);
        }

        return result;
    }
}
