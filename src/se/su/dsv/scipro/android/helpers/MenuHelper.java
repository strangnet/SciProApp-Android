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

package se.su.dsv.scipro.android.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import se.su.dsv.scipro.android.Preferences;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.SciProApplication;
import se.su.dsv.scipro.android.activity.MainActivity;

/**
 * User: patrick
 */
public class MenuHelper {

    public static boolean openActivityFromMenuItem(Context context, MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.preferences_menu_item:
                intent = new Intent(context, Preferences.class);
                context.startActivity(intent);
                return true;
            case R.id.logout_menu_item:
                SciProApplication.getInstance().logout();
                intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                return true;
        }
        return false;
    }
}
