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
