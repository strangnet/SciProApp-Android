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

package se.su.dsv.scipro.android.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project implements Serializable, Comparable<Project> {
    
    private static final long serialVersionUID = 1712537089207993167L;

    private static int counter = 0;
    
    public static enum STATUS {
        NEEDHELP, NEUTRAL, FINE;
    }
    
    public int id;
    public List<User> projectMembers;
    public List<User> projectReviewers;
    public List<User> projectCoSupervisors;
    public List<FinalSeminar> finalSeminars;
    public String title;
    public STATUS status;
    public String statusMessage;
    public String level;
    public int projectProgress;

    
    public Project(String title) {
        this.title = title;
        projectMembers = new ArrayList<User>();
        id = counter++;
    }

    public int compareTo(Project another) {
        return this.status.compareTo(another.status);
    }
}
