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

public class User implements Serializable {
    
    private static final long serialVersionUID = -7102705806225167000L;

    private static int counter = 0;
    
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        id = counter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String toString() {
        return getName() + " (" + email + ")";
    }
}
