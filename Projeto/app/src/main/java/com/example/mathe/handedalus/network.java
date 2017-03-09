package com.example.mathe.handedalus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathe on 04/03/2017.
 */

public class network extends AsyncTask<String, String ,List<book> >{

    public List<book> myLibrary;
    public String username;
    public String password;
    public String login;
    Context mContext;

    public network(Context _contex){
        super();
        mContext=_contex;
        myLibrary = new ArrayList<book>();
        username="";
        password="";
        login="not";
    }

    public void onPreExecute(){
        ;
    }

    protected void onProgressUpdate(Void... progress) {
        ;
    }

    protected void onPostExecute(List<book> _void){
        if(login.equals("logged")) {
            library.username = username;
            library.password = password;
            library.myLibrary = _void;
            LivrosActivity activityN = new LivrosActivity();
            Intent myIntent = new Intent(mContext, activityN.getClass());
            mContext.startActivity(myIntent);
        }
    }

    @Override
    protected List<book> doInBackground(String... _userpass){
        username = _userpass[0];
        password = _userpass[1];
        String version = _userpass[2];
        try {
            if(_userpass.equals("renove"))  dedalus.renovar(username, password);
            myLibrary = dedalus.main(username, password);
        }catch (Exception e) {
            Log.d("error", e.toString());
        }
        login="logged";
        return myLibrary;
    }
}
