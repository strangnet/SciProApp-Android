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

package se.su.dsv.scipro.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import se.su.dsv.scipro.android.dao.Project;

public class ProjectListItem extends LinearLayout {

    private Project project;
    private TextView textView;
    private ImageView statusImage;
    private TextView miscTextView;

    public ProjectListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        textView = (TextView) findViewById(android.R.id.text1);
//        statusImage = (ImageView) findViewById(R.id.project_list_item_status);
//        miscTextView = (TextView) findViewById(R.id.project_list_item_misc);
    }

}
