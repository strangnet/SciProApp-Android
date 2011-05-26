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

package se.su.dsv.scipro.android.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import se.su.dsv.scipro.android.R;
import se.su.dsv.scipro.android.activity.StatusCheckIn;

/**
 * User: patrick
 * Date: 2011-05-23
 * Time: 11:34
 */
public class LocationIntentReceiver extends BroadcastReceiver {

    public static final String TAG = "LocationIntentReceiver";

    private static final int NOTIFICATION_ID = 4711;

    private boolean hasEnabledLocationAwareSetting;

    @Override
    public void onReceive(Context context, Intent intent) {

        String key = LocationManager.KEY_PROXIMITY_ENTERING;

        boolean entering = intent.getBooleanExtra(key, false);

        if (entering) {
            Log.d(TAG, "Entering");
        } else {
            Log.d(TAG, "Leaving");
        }


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, StatusCheckIn.class), 0);

        Notification notification = createNotification();

        String notificationTitle = "";
        String notificationText = "";

        if (entering) {
            notificationTitle = "Entering DSV";
            notificationText = "You are entering the proximity of DSV. Click to change your SciPro status.";
        } else {
            notificationTitle = "Leaving DSV";
            notificationText = "You are leaving the proximity of DSV. Click to change your SciPro status.";
        }


        notification.setLatestEventInfo(context,
                notificationTitle,
                notificationText,
                pendingIntent);

        notificationManager.notify(NOTIFICATION_ID, notification);
    }


    private Notification createNotification() {
        Notification notification = new Notification();

        notification.icon = R.drawable.ic_stat_location;
        notification.when = System.currentTimeMillis();

        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        return notification;
    }
}
