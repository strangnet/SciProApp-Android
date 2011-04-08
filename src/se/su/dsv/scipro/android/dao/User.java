package se.su.dsv.scipro.android.dao;

public class User {
    private static int counter = 0;
    
    private int id;
    private String name;
    
    public User(String name) {
        this.name = name;
        id = counter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
