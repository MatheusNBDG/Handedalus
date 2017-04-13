package com.matheusnbdg.handedalus;

/**
 * Created by Mathe on 26/03/2017.
 */

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
import android.preference.PreferenceManager;
import android.widget.CheckBox;

/**
 * A login screen that offers login via email/password.
 */
public class LoginAfterErrorActivity extends Activity implements OnClickListener {

    CheckBox checkBox;
    EditText numUsp;
    EditText password;
    TextView mensage;
    Button button;
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

        mensage.setText("Houve um problema com a conexão...");

        button.setOnClickListener(this);
        loadSavedPreferences();
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(LoginActivity.LoginActivityContext());
        String numUSP = sharedPreferences.getString("numUsp", "");
        String passWord = sharedPreferences.getString("password", "");
        numUsp.setText(numUSP);
        password.setText(passWord);
    }

    @Override
    public void onClick(View v){
        setContentView(R.layout.login);
        library.username=numUsp.getText().toString();
        library.password=password.getText().toString();
        // TODO Auto-generated method stub
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
}
