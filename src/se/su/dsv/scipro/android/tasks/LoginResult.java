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

public class LoginResult {
    public boolean authenticated;
    public long userid;
    public String username;
    public String apikey;

    public LoginResult(boolean authenticated, String username, String apikey, long userid) {
        this.authenticated = authenticated;
        this.username = username;
        this.apikey = apikey;
        this.userid = userid;
    }
}