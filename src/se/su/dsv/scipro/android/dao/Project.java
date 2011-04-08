package se.su.dsv.scipro.android.dao;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private static int counter = 0;
    
    private int id;
    private ArrayList<User> members;
    private String title;
    
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
}
