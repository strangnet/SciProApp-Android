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

package se.su.dsv.scipro.android.tasks;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import se.su.dsv.scipro.android.utils.DaoUtils;
import se.su.dsv.scipro.android.dao.FinalSeminar;
import se.su.dsv.scipro.android.dao.Project;
import se.su.dsv.scipro.android.dao.User;
import se.su.dsv.scipro.android.json.SciProJSON;

import java.util.ArrayList;
import java.util.List;

/**
 * User: patrick
 * Date: 2011-05-22
 * Time: 14:12
 */
public class GetProjectsAsyncTask extends AsyncTask<Void, Void, GetProjectsAsyncTask.ProjectsResult> {

    public interface ProjectsResponder {
        public void retrievingProjects();

        public void retrievedProjects(ProjectsResult result);
    }

    public class ProjectsResult {
        public boolean authenticated;
        public List<Project> projects;

        public ProjectsResult(boolean authenticated, List<Project> projects) {
            this.authenticated = authenticated;
            this.projects = projects;
        }
    }

    public static final String TAG = "GetProjectsAsyncTask";

    private ProjectsResponder responder;

    public GetProjectsAsyncTask(ProjectsResponder responder) {
        this.responder = responder;
    }

    @Override
    protected ProjectsResult doInBackground(Void... params) {
        String response = SciProJSON.getInstance().getProjects();

        boolean authenticated = true;
        List<Project> projects = new ArrayList<Project>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (!jsonObject.getString("apikey").equals("success")) {
                authenticated = false;
            } else {
                JSONArray projectsArray = jsonObject.getJSONArray("projectArray");
                for (int i = 0; i < projectsArray.length(); i++) {
                    JSONObject currentObject = projectsArray.getJSONObject(i);

                    String title = currentObject.getString("title");

                    Project.STATUS status = Enum.valueOf(Project.STATUS.class, currentObject.getString("status"));

                    String statusMessage = currentObject.getString("statusMessage");

                    String level = currentObject.getString("level");

                    List<User> projectMembers = new ArrayList<User>();
                    JSONArray memberArray = currentObject.getJSONArray("projectMembers");
                    for (int j = 0; j < memberArray.length(); j++) {
                        JSONObject member = memberArray.getJSONObject(j);
                        projectMembers.add(new User(member.getLong("id"), member.getString("name")));
                    }
                    DaoUtils.addUsersToApplication(projectMembers);

                    List<User> projectReviewers = new ArrayList<User>();
                    JSONArray reviewerArray = currentObject.getJSONArray("projectReviewers");
                    for (int j = 0; j < reviewerArray.length(); j++) {
                        JSONObject reviewer = reviewerArray.getJSONObject(j);
                        projectReviewers.add(new User(reviewer.getLong("id"), reviewer.getString("name")));
                    }
                    DaoUtils.addUsersToApplication(projectReviewers);

                    List<User> projectCoSupervisors = new ArrayList<User>();
                    JSONArray coSuperVisorArray = currentObject.getJSONArray("projectCosupervisors");
                    for (int j = 0; j < coSuperVisorArray.length(); j++) {
                        JSONObject coSupervisor = coSuperVisorArray.getJSONObject(j);
                        projectCoSupervisors.add(new User(coSupervisor.getLong("id"), coSupervisor.getString("name")));
                    }
                    DaoUtils.addUsersToApplication(projectCoSupervisors);

                    int projectProgress = currentObject.getInt("projectProgress");

                    List<FinalSeminar> finalSeminars = new ArrayList<FinalSeminar>();
                    JSONArray finalSeminarArray = currentObject.getJSONArray("finalSeminars");
                    for (int j = 0; j < finalSeminarArray.length(); j++) {
                        JSONObject finalSeminar = finalSeminarArray.getJSONObject(j);
                        String room = finalSeminar.getString("room");
                        String date = finalSeminar.getString("date");

                        List<User> activeListeners = new ArrayList<User>();
                        JSONArray activeListenerArray = finalSeminar.getJSONArray("active");
                        for (int k = 0; k < activeListenerArray.length(); k++) {
                            JSONObject listener = activeListenerArray.getJSONObject(k);
                            activeListeners.add(new User(listener.getLong("id"), listener.getString("name")));
                        }
                        DaoUtils.addUsersToApplication(activeListeners);

                        List<User> opponents = new ArrayList<User>();
                        JSONArray opponentArray = finalSeminar.getJSONArray("opponents");
                        for (int k = 0; k < opponentArray.length(); k++) {
                            JSONObject opponent = opponentArray.getJSONObject(k);
                            opponents.add((new User(opponent.getLong("id"), opponent.getString("name"))));
                        }
                        DaoUtils.addUsersToApplication(opponents);

                        finalSeminars.add(new FinalSeminar(room, date, activeListeners, opponents));
                    }

                    Project project = new Project(title);
                    project.status = status;
                    project.statusMessage = statusMessage;
                    project.level = level;
                    project.projectMembers = projectMembers;
                    project.projectCoSupervisors = projectCoSupervisors;
                    project.projectReviewers = projectReviewers;
                    project.projectProgress = projectProgress;
                    project.finalSeminars = finalSeminars;

                    projects.add(project);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: ", e);
        }

        return new ProjectsResult(authenticated, projects);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        responder.retrievingProjects();
    }

    @Override
    protected void onPostExecute(ProjectsResult result) {
        super.onPostExecute(result);
        responder.retrievedProjects(result);
    }
}
