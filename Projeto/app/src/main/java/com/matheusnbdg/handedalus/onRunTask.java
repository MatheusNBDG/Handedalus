package com.matheusnbdg.handedalus;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by Mathe on 05/03/2017.
 */

public class onRunTask extends WakefulBroadcastReceiver {

    public static String NOTIFICATION_ID = "handedalussetup";
    public static String NOTIFICATION = "handedalussetupnot";
    private static CharSequence title = "Aviso de livro a expirar logo!";
    private static CharSequence text = "Um livro irá expirar em alguns dias.";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startIntent = new Intent(context,AlarmService.class);
        context.startService(startIntent);

        /*Intent i = new Intent(context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification_incons)
                        .setContentTitle(title)
                        .setContentText(text);
        Intent resultIntent = new Intent(context, LoginActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(LoginActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        Notification notification = mBuilder.build();

        int id = 100;
        notificationManager.notify(id, notification);
    }*/
    }
}