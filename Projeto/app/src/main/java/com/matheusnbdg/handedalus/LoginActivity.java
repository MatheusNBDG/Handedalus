package com.matheusnbdg.handedalus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.CheckBox;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements OnClickListener {

    CheckBox checkBox;
    EditText numUsp;
    EditText password;
    TextView mensage;
    Button button;
    Boolean hasLogged;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        context = getApplicationContext();

        checkBox = (CheckBox) findViewById(R.id.checkBox1);
        numUsp = (EditText) findViewById(R.id.numUsp);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button1);
        mensage = (TextView) findViewById(R.id.mensagem);

        button.setOnClickListener(this);
        loadSavedPreferences();
        if(hasLogged) {
            button.performClick();
        }
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value", false);
        String numUSP = sharedPreferences.getString("numUsp", "");
        String passWord = sharedPreferences.getString("password", "");
        Boolean HasLogged = sharedPreferences.getBoolean("hasLogged", false);
        if (checkBoxValue) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        numUsp.setText(numUSP);
        password.setText(passWord);
        hasLogged=HasLogged;
    }

    private void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public void onClick(View v){
        setContentView(R.layout.login);
        library.username=numUsp.getText().toString();
        library.password=password.getText().toString();
        // TODO Auto-generated method stub
        if (checkBox.isChecked()){
            savePreferences("numUsp", numUsp.getText().toString());
            savePreferences("password", password.getText().toString());
            savePreferences("CheckBox_Value", true);
            savePreferences("hasLogged", checkBox.isChecked());
            task periodicTask = new task(LoginActivity.LoginActivityContext());
            periodicTask.maketask();
        }
        if(isOnline()) {
            Carregando activityN = new Carregando();
            Intent myIntent = new Intent(this, activityN.getClass());
            startActivity(myIntent);
        }else{
            mensage.setText("Verifique a conexão com internet");
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static Context LoginActivityContext(){
        return context;
    }

    public Context Context(){
        return context;
    }
}


