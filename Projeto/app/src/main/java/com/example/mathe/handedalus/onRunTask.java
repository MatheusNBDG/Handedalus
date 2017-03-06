package com.example.mathe.handedalus;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.util.Collections;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Mathe on 05/03/2017.
 */

class onRunTask extends GcmTaskService {

    public void onInitializeTasks() {
        super.onInitializeTasks();
    }
    @Override
    public int onRunTask(TaskParams taskParams) {
        String numUSP = library.username;
        Log.d("dedalus", numUSP);
        String passWord = library.password;
        try {
            network net = new network();
            net.execute(numUSP,passWord);
            library.myLibrary=net.myLibrary;
        }catch(Exception e) {
            return GcmNetworkManager.RESULT_FAILURE;
        }

        List<book> myLibrary = library.myLibrary;
        Collections.sort(myLibrary, new CustomComparator());

        int days = Integer.parseInt(dias.getRelativeTime(myLibrary.get(0).data));
        if(days<=3){
            callNotification(Integer.toString(days));
        }
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    public void callNotification(String days){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_incons)
                        .setContentTitle("Aviso de livro a expirar logo!")
                        .setContentText("Você tem um livro que irá expirar em " + days + ".");
        Intent resultIntent = new Intent(this, LivrosActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LivrosActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int NOTIFICATION_ID = 100;
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
