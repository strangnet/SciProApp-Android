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

package se.su.dsv.scipro.android.dao;

import java.io.Serializable;
import java.util.List;

public class PrivateMessage implements Serializable {
    
    private static final long serialVersionUID = 1678548421983921901L;

    public long id;
    public User from;
    public List<User> recipients;
    public String subject;
    public String message;
    public String date;
    public boolean read;
    
    public PrivateMessage(long id, String subject, String message, User from, String date, boolean read) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.from = from;
        this.date = date;
        this.read = read;
    }
}
