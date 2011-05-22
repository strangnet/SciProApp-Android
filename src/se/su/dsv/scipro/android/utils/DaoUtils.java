/*
 * Copyright (c) 2011 Patrick Strang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.su.dsv.scipro.android.utils;

import se.su.dsv.scipro.android.SciProApplication;
import se.su.dsv.scipro.android.dao.Project;
import se.su.dsv.scipro.android.dao.User;

import java.util.Collection;
import java.util.List;

public class DaoUtils {

    public static final String TAG = "DaoUtils";

    public static String projectMembersAsString(Project project) {
        List<User> members = project.projectMembers;
        String membersAsString = "";
        boolean multipleMembers = false;
        for (User user : members) {
            if (multipleMembers) 
                membersAsString += ", ";
            else
                multipleMembers = true;
            membersAsString += user.name;
        }
        return membersAsString;
    }

    public static void addUsersToApplication(Collection<User> users) {
        for (User u : users) {
            SciProApplication.getInstance().addUser(u);
        }
    }

}
