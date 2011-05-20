package se.su.dsv.scipro.android.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import se.su.dsv.scipro.android.Preferences;
import se.su.dsv.scipro.android.R;

/**
 * User: patrick
 */
public class MenuHelper {

    public static boolean openActivityFromMenuItem(Context context, MenuItem item) {
        Intent intent;
        Class<? extends Activity> requestingClass = (Class<? extends Activity>) context.getClass();
        switch (item.getItemId()) {
            case R.id.preferences_menu_item:
                if (requestingClass != Preferences.class) {
                    intent = new Intent(context, Preferences.class);
                    context.startActivity(intent);
                    return true;
                }
                break;
        }
        return false;
    }
}
