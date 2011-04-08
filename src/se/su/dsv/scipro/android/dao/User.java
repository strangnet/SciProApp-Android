package se.su.dsv.scipro.android.dao;

import java.io.Serializable;

public class User implements Serializable {
    
    private static final long serialVersionUID = -7102705806225167000L;

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
