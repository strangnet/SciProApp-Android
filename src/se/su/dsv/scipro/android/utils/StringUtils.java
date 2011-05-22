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

package se.su.dsv.scipro.android.utils;

import android.util.Log;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils {

    public static final String TAG = "StringUtils";

    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n = 0;
                while ((n = reader.read(buffer)) != -1)
                    writer.write(buffer, 0, n);
            } catch (Exception e) {

            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static String hash(String plaintext) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(plaintext.getBytes("UTF-8"));
            return String.valueOf(Base64Coder.encode(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "SHA-1 support is required.");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Unexpected exception", e);
        }

        return "";
    }

}
