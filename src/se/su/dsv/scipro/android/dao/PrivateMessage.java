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
