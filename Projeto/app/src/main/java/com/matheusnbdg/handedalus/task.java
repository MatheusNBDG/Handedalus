package com.matheusnbdg.handedalus;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import static com.matheusnbdg.handedalus.LoginActivity.LoginActivityContext;
import static com.matheusnbdg.handedalus.LoginActivity.context;


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
                .setFlex(21600L)
                .setUpdateCurrent(false)
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setTag("HANDEDALUS_GET_LYBRARY")
                .build();
        mGcmNetworkManager.schedule(task);
    }

    public void onInitializeTasks() {
        super.onInitializeTasks();
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        Context context = LoginActivityContext();
        Intent nothing = new Intent(context, LoginActivity.class);
        context.startService(nothing);

        try {
            backNetwork net = new backNetwork();
            net.execute();
        }catch(Exception e) {
            return super.onStartCommand(nothing, 0, GcmNetworkManager.RESULT_FAILURE);
        }
        return super.onStartCommand(nothing, 0, GcmNetworkManager.RESULT_SUCCESS);
    }


    public void callNotification(String days){
        try {
            LoginActivity activity = new LoginActivity();

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(activity.Context())
                            .setSmallIcon(R.drawable.notification_incons)
                            .setContentTitle("Aviso de livro a expirar logo!")
                            .setContentText("Você tem um livro que irá expirar em " + days + " dias.");
            Intent resultIntent = new Intent(activity.Context(), LoginActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity.Context());
            stackBuilder.addParentStack(LoginActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            int NOTIFICATION_ID = 100;
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }catch (Exception e){
            Log.d("error", "error");
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }
}
