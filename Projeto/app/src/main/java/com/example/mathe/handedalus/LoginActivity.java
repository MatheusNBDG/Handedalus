package com.example.mathe.handedalus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageButton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.example.mathe.handedalus.R.layout.livros;
import static com.example.mathe.handedalus.R.layout.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
            savePreferences("hasLogged", checkBox.isChecked());
            task periodicTask = new task(this);
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
}


