package se.su.dsv.scipro.android.dao;

import java.util.List;

public class DaoUtils {

    public static String projectMembersAsString(Project project) {
        List<User> members = project.getMembers();
        String membersAsString = "";
        boolean multipleMembers = false;
        for (User user : members) {
            if (multipleMembers) 
                membersAsString += ", ";
            else
                multipleMembers = true;
            membersAsString += user.getName();
        }
        return membersAsString;
    }
    
}
