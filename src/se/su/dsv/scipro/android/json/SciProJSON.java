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

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import se.su.dsv.scipro.android.SciProApplication;

public class SciProJSON {
    
    private String url;
    private int res;
    
    public SciProJSON(String url) {
        this.url = url;
    }
    
    public SciProJSON(int res) {
        this.res = res;
    }
    
    public String readJSON() {
        String x ="";
        try {
            InputStream is = SciProApplication.getInstance().getResources().openRawResource(res);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String jsonText = new String(buffer);
            JSONArray entries = new JSONArray(jsonText);
            
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
            }
            
            x = jsonText;
        } catch (Exception e) {
            x = "Error reading json: " + e.getMessage();
        }
        
        return x;
    }
}
