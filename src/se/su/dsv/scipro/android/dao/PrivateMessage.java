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

package se.su.dsv.scipro.android.dao;

import java.io.Serializable;

public class PrivateMessage implements Serializable {
    
    private static final long serialVersionUID = 1678548421983921901L;

    private static int counter = 0;
    
    private int id;
    private User fromUser;
    private User toUser;
    private String subject;
    private String message;
    
    public PrivateMessage(User from, User to, String subject, String message) {
        id = counter++;
        this.fromUser = from;
        this.toUser = to;
        this.subject = subject;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
    
}
