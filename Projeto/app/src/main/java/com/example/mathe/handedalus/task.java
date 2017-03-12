package com.example.mathe.handedalus;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import java.util.Collections;
import java.util.List;


/**
 * Created by Mathe on 05/03/2017.
 */

public class task extends GcmTaskService {

    private final GcmNetworkManager mGcmNetworkManager;

    public task(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public task(){
        mGcmNetworkManager = GcmNetworkManager.getInstance(LoginActivity.LoginActivityContext());
    }

    public void maketask() {
        super.onInitializeTasks();
        Task task = new PeriodicTask.Builder()
                .setService(task.class)
                .setPeriod(108000L)
                .setFlex(36000L)
                .setUpdateCurrent(false)
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setTag("GET_DATA")
                .build();
        mGcmNetworkManager.schedule(task);

    }

    public void onInitializeTasks() {
        super.onInitializeTasks();
    }
    @Override
    public int onRunTask(TaskParams taskParams) {
        Intent nothing = new Intent();
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(LoginActivity.LoginActivityContext());
        String numUSP = sharedPreferences.getString("numUsp", "");
        String passWord = sharedPreferences.getString("password", "");
        dedalus net = new dedalus();
        try {
            net.main(numUSP,passWord);
        }catch(Exception e) {
            return super.onStartCommand(nothing, 0, GcmNetworkManager.RESULT_FAILURE);
        }
        List<book> myLibrary = library.myLibrary;
        if(myLibrary.isEmpty()) return super.onStartCommand(nothing, 0, GcmNetworkManager.RESULT_FAILURE);
        Collections.sort(myLibrary, new CustomComparator());

        int days = Integer.parseInt(dias.getRelativeTime(myLibrary.get(0).data));
        if(days<=3){
            callNotification(Integer.toString(days));
        }
        return super.onStartCommand(nothing, 0, GcmNetworkManager.RESULT_SUCCESS);
    }

    public void callNotification(String days){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(LoginActivity.LoginActivityContext())
                        .setSmallIcon(R.drawable.notification_incons)
                        .setContentTitle("Aviso de livro a expirar logo!")
                        .setContentText("Você tem um livro que irá expirar em " + days + ".");
        Intent resultIntent = new Intent(LoginActivity.LoginActivityContext(), LivrosActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(LoginActivity.LoginActivityContext());
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
