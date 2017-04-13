package com.matheusnbdg.handedalus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by Mathe on 25/03/2017.
 */

public class backNetwork extends AsyncTask<String, String ,List<book> > {

    public List<book> myLibrary;
    public String username;
    public String password;
    public String login;

    public backNetwork(){
        super();

        loadSavedPreferences();

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
        List<String> books = new ArrayList<>();
        for(int i=0;i<myLibrary.size();i++){
            int days = Integer.parseInt(dias.getRelativeTime(myLibrary.get(i).data));
            if(days==0)
                books.add(myLibrary.get(i).name);
        }
        library.books=books;
        library.myLibrary = myLibrary;
        try {
            dedalus.renovar(username, password);
        }catch (Exception e){
            ;
        }
        return ;
        //if(days<=11){
          //  task algo = new task();
            //algo.callNotification(Integer.toString(days));
        //}
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(LoginActivity.LoginActivityContext());
        username = sharedPreferences.getString("numUsp", "");
        password = sharedPreferences.getString("password", "");
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

