package com.example.mathe.handedalus;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;



/**
 * Created by Mathe on 05/03/2017.
 */

public class task {

    private final GcmNetworkManager mGcmNetworkManager;

    public task(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void maketask() {

        Task task = new PeriodicTask.Builder()
                .setService(onRunTask.class)
                .setPeriod(60 * 60 * 34)
                .setFlex(60 * 60 * 24)
                .setUpdateCurrent(false)
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setTag("GET_DATA")
                .build();
        mGcmNetworkManager.schedule(task);

    }
}
