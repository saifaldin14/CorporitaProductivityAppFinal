package com.corporita.corporitaproductivityapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by saif on 2017-08-29.
 */

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, "Please update your goals", "Corporita App");
    }

    public void createNotification(Context context, String goal, String msgAlert){

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0, new Intent(context, GoalsPage.class),0);

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.life_wheel)
                .setContentText(goal)
                .setTicker(msgAlert);


        mbuilder.setContentIntent(notificIntent);

        mbuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);

        mbuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mbuilder.build());


    }
}
