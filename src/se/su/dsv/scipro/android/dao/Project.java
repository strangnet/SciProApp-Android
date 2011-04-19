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
import java.util.ArrayList;
import java.util.List;

public class Project implements Serializable {
    
    private static final long serialVersionUID = 1712537089207993167L;

    private static int counter = 0;
    
    public static enum STATUS {
        POSITIVE, NEUTRAL, NEGATIVE;
    }
    
    private int id;
    private ArrayList<User> members;
    private String title;
    private STATUS status;
    
    public Project(String title) {
        this.title = title;
        members = new ArrayList<User>();
        id = counter++;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }
    
    public void addMember(User user) {
        members.add(user);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    
    public STATUS getStatus() {
        return status;
    }
    
    public void setStatus(STATUS status) {
        this.status = status;
    }
}
