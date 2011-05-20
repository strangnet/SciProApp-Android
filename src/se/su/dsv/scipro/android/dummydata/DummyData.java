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

package se.su.dsv.scipro.android.dummydata;

import java.util.ArrayList;

import se.su.dsv.scipro.android.dao.PrivateMessage;
import se.su.dsv.scipro.android.dao.Project;
import se.su.dsv.scipro.android.dao.User;

public class DummyData {
    
    private ArrayList<User> users;
    private ArrayList<Project> projects;
    private ArrayList<PrivateMessage> messages;
    
    private static DummyData instance;    
    
    public static DummyData getInstance() {
        if (instance == null) {
            instance = new DummyData();
        }
        
        return instance;
    }
    
    private DummyData() {
        addUsers();
        addProjects();
        addMessages();
    }
    
    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public ArrayList<PrivateMessage> getMessages() {
        return messages;
    }

    private void addUsers() {
        users = new ArrayList<User>();
        users.add(new User("Janne", "Nilsson", "janne@epost.se"));
        users.add(new User("Mia", "Persson", "mia@epost.se"));
        users.add(new User("Patrick", "Clipper", "patrick@epost.se"));
        users.add(new User("Helena", "Jonsson", "helena@epost.se"));
        users.add(new User("Johan", "Andersson", "johan@epost.se"));
        users.add(new User("Anna", "Nilsson-Torefjord", "anna@epost.se"));
    }
    
    private void addProjects() {
        projects = new ArrayList<Project>();
        Project p = new Project("Smartphones in education");
        p.setStatus(Project.STATUS.NEUTRAL);
        p.addMember(users.get(2));
        p.addMember(users.get(4));
        projects.add(p);
        p = new Project("HCI for large systems");
        p.setStatus(Project.STATUS.POSITIVE);
        p.addMember(users.get(0));
        p.addMember(users.get(5));
        projects.add(p);
        p = new Project("CSF for medium enterprises");
        p.setStatus(Project.STATUS.NEGATIVE);
        p.addMember(users.get(1));
        p.addMember(users.get(3));
        projects.add(p);
    }

    private void addMessages() {
        messages = new ArrayList<PrivateMessage>();
        messages.add(new PrivateMessage(users.get(0), users.get(2), "Testing", "This is a test."));
        messages.add(new PrivateMessage(users.get(4), users.get(2), "Tomorrow", "This is the end. My only friend, the end."));
        messages.add(new PrivateMessage(users.get(2), users.get(4), "Call me", "Call me on my cell: 070-2092397"));
    }
    
}
