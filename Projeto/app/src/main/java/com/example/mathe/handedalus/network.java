package com.example.mathe.handedalus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
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
    public List<String> books;
    public String version;
    Context mContext;

    public network(Context _contex){
        super();
        mContext=_contex;
        myLibrary = new ArrayList<book>();
        username="";
        password="";
        login="not";
        books=library.books;
        version= " ";
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
            library.myLibrary=myLibrary;
            if(!library.myLibrary.isEmpty()) {
                if (version.equals("renove")) {
                    RenovarActivity activityN = new RenovarActivity();
                    Intent myIntent = new Intent(mContext, activityN.getClass());
                    mContext.startActivity(myIntent);
                } else {
                    LivrosActivity activityN = new LivrosActivity();
                    Intent myIntent = new Intent(mContext, activityN.getClass());
                    mContext.startActivity(myIntent);
                }
            }else{
                LoginAfterErrorActivity activityN = new LoginAfterErrorActivity();
                Intent myIntent = new Intent(mContext, activityN.getClass());
                mContext.startActivity(myIntent);
            }
        }
    }

    @Override
    protected List<book> doInBackground(String... _userpass){
        username = _userpass[0];
        password = _userpass[1];
        version = _userpass[2];
        try {
            if(version.equals("renove"))
                myLibrary=dedalus.renovar(username, password);
            else
                myLibrary = dedalus.main(username, password);
        }catch (Exception e) {
            Log.d("error", e.toString());
        }
        login="logged";
        return myLibrary;
    }
}
