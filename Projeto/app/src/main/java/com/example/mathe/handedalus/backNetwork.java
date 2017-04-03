package com.example.mathe.handedalus;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.mathe.handedalus.LoginActivity.LoginActivityContext;
import static com.example.mathe.handedalus.LoginActivity.context;

/**
 * Created by Mathe on 25/03/2017.
 */

public class backNetwork extends AsyncTask<String, String ,List<book> > {

    public List<book> myLibrary;
    public String username;
    public String password;
    public String login;
    Context mContext;

    public backNetwork(Context _contex){
        super();
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(LoginActivity.LoginActivityContext());
        mContext=_contex;
        myLibrary = new ArrayList<book>();
        username=sharedPreferences.getString("numUsp", "");
        password=sharedPreferences.getString("password", "");
        login="not";
        myLibrary = new ArrayList<book>();
    }

    public void onPreExecute(){
        ;
    }

    protected void onProgressUpdate(Void... progress) {
        ;
    }

    protected void onPostExecute(List<book> _void){
        myLibrary=_void;
        if(myLibrary.isEmpty()) return;
        Collections.sort(myLibrary, new CustomComparator());

        int days = Integer.parseInt(dias.getRelativeTime(myLibrary.get(0).data));

        if(days<=3){
            task algo = new task();
            algo.callNotification(Integer.toString(days));
        }
    }

    @Override
    protected List<book> doInBackground(String... _userpass){
        try {
            myLibrary=dedalus.main(username, password);
        }catch (Exception e) {
            Log.d("error", e.toString());
        }
        login="logged";
        return myLibrary;
    }


}

