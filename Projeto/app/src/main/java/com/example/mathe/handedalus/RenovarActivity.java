package com.example.mathe.handedalus;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mathe on 24/03/2017.
 */

public class RenovarActivity extends Activity implements View.OnClickListener{

    public List<book> myLibrary;
    public List<String> oldDate;
    public List<book> myTrueLibrary;

    Button button;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.renovar);

        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(this);

        oldDate=library.oldDate;
        myLibrary=library.myLibrary;
        myTrueLibrary=library.myTrueLibrary;

        if (!myLibrary.isEmpty()) {
            if(myTrueLibrary.isEmpty()){
                LivrosActivity activity=new LivrosActivity();
                Intent myIntent = new Intent(this, activity.getClass());
                startActivity(myIntent);
                return ;
            }else{
                for (int i = 0; i != myTrueLibrary.size(); i++) {
                    LinearLayout placeHolder = (LinearLayout) findViewById(R.id.tent1);
                    TextView bookName = (TextView) findViewById(R.id.name1);
                    TextView oldData = (TextView) findViewById(R.id.fdate);
                    TextView date = (TextView) findViewById(R.id.adate);
                    if (i == 0) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent1);
                        bookName = (TextView) findViewById(R.id.name1);
                        oldData = (TextView) findViewById(R.id.fdate);
                        date = (TextView) findViewById(R.id.adate);
                    }
                    if (i == 1) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent2);
                        bookName = (TextView) findViewById(R.id.name2);
                        oldData = (TextView) findViewById(R.id.fdate2);
                        date = (TextView) findViewById(R.id.adate2);
                    }
                    if (i == 2) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent3);
                        bookName = (TextView) findViewById(R.id.name3);
                        oldData = (TextView) findViewById(R.id.fdate3);
                        date = (TextView) findViewById(R.id.adate3);
                    }
                    if (i == 3) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent4);
                        bookName = (TextView) findViewById(R.id.name4);
                        oldData = (TextView) findViewById(R.id.fdate4);
                        date = (TextView) findViewById(R.id.adate4);
                    }
                    if (i == 4) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent5);
                        bookName = (TextView) findViewById(R.id.name5);
                        oldData = (TextView) findViewById(R.id.fdate5);
                        date = (TextView) findViewById(R.id.adate5);
                    }
                    if (i == 5) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent6);
                        bookName = (TextView) findViewById(R.id.name6);
                        oldData = (TextView) findViewById(R.id.fdate6);
                        date = (TextView) findViewById(R.id.adate6);
                    }
                    if (i == 6) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent7);
                        bookName = (TextView) findViewById(R.id.name7);
                        oldData = (TextView) findViewById(R.id.fdate7);
                        date = (TextView) findViewById(R.id.adate7);
                    }
                    if (i == 7) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent8);
                        bookName = (TextView) findViewById(R.id.name8);
                        oldData = (TextView) findViewById(R.id.fdate8);
                        date = (TextView) findViewById(R.id.adate8);
                    }
                    if (i == 8) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent9);
                        bookName = (TextView) findViewById(R.id.name9);
                        oldData = (TextView) findViewById(R.id.fdate9);
                        date = (TextView) findViewById(R.id.adate9);
                    }
                    if (i == 9) {
                        placeHolder = (LinearLayout) findViewById(R.id.tent10);
                        bookName = (TextView) findViewById(R.id.name10);
                        oldData = (TextView) findViewById(R.id.fdate10);
                        date = (TextView) findViewById(R.id.adate10);
                    }
                    placeHolder.setVisibility(View.VISIBLE);
                    bookName.setText("Nome: " + myTrueLibrary.get(i).name);
                    oldData.setText("De: " + oldDate.get(i));
                    date.setText("Para: " + myTrueLibrary.get(i).data);
                    if(oldDate.get(i).equals(myTrueLibrary.get(i).data))
                        date.setBackgroundColor(0xffff0000);
                }
            }
        }else{
            LoginActivity activity = new LoginActivity();
            Intent myIntent = new Intent(this, activity.getClass());
            startActivity(myIntent);
            TextView men = (TextView) findViewById(R.id.mensagem);
            men.setText("Houve um problema com a conexão...");
        }
    }

    @Override
    public void onClick(View v) {
        setContentView(R.layout.renovar);
        LivrosActivity activity=new LivrosActivity();
        library.myLibrary=myLibrary;
        Intent myIntent = new Intent(this, activity.getClass());
        startActivity(myIntent);
    }
}
