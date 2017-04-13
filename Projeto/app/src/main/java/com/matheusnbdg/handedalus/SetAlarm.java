package com.matheusnbdg.handedalus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mathe on 12/04/2017.
 */

public class SetAlarm extends AsyncTask<String, Void ,Void > {

    public SetAlarm(){
        super();
    }

    public void onPreExecute(){
        ;
    }

    protected void onProgressUpdate(Void... progress) {
        ;
    }

    protected void onPostExecute(Void _void){
        Context context = LoginActivity.LoginActivityContext().getApplicationContext();
        CharSequence text = "O alarme foi marcado \n Você será avisado caso não mate o aplicativo";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected Void doInBackground(String... _days){
        int _inDays=Integer.parseInt(_days[0]);
        Calendar c = Calendar.getInstance();
        long firstTime;
        if(_inDays!=0)  {
            c.add(Calendar.DATE, _inDays);
        }
        firstTime = c.getTimeInMillis();

        Intent notificationIntent = new Intent(LoginActivity.LoginActivityContext(), onRunTask.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.LoginActivityContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) LoginActivity.LoginActivityContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, firstTime+500, pendingIntent);

        return null;
    }
}
