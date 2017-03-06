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

public class network extends AsyncTask<String, Void ,Void >{

    public List<book> myLibrary;
    public String username;
    public String password;


    public network(){
        myLibrary = new ArrayList<book>();
        username="";
        password="";
    }

    @Override
    protected Void doInBackground(String... _userpass){
        username = _userpass[0];
        password = _userpass[1];
        try {
            myLibrary = dedalus.main(username, password);
        }catch (Exception e) {
            Log.d("error", e.toString());
        }
        library.username=username;
        library.password=password;
        library.myLibrary=myLibrary;
        return null;
    }
}
