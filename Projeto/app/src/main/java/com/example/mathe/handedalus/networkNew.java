package com.example.mathe.handedalus;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathe on 06/03/2017.
 */

public class networkNew extends AsyncTask<String, Void , Void> {

    public List<book> myLibrary;
    public List<String> books;

    public networkNew(){
        myLibrary = new ArrayList<book>();
        books = new ArrayList<String>();
    }

    protected Void  doInBackground(String... _userpass) {
        String username = _userpass[0];
        String password = _userpass[1];
        try {
            myLibrary = dedalus.renovar(username, password, books);
        }catch (Exception e) {
            Log.d("error", e.toString());
        }
        return null;
    }
}
