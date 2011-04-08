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
        users.add(new User("Janne"));
        users.add(new User("Mia"));
        users.add(new User("Patrick"));
        users.add(new User("Helena"));
        users.add(new User("Johan"));
        users.add(new User("Anna"));
    }
    
    private void addProjects() {
        projects = new ArrayList<Project>();
        Project p = new Project("Smartphones in education");
        p.addMember(users.get(2));
        p.addMember(users.get(4));
        projects.add(p);
        p = new Project("HCI for large systems");
        p.addMember(users.get(0));
        p.addMember(users.get(5));
        projects.add(p);
        p = new Project("CSF for medium enterprises");
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
