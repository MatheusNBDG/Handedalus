package com.matheusnbdg.handedalus;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mathe on 04/03/2017.
 */

public class LivrosActivity extends Activity implements View.OnClickListener {

    static List<book> myLibrary;
    static String username;
    static String password;
    private Button button;
    private Button button2;
    private Button button3;
    private EditText contagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.livros);

        button = (Button) findViewById(R.id.renovar);
        button.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.alerta);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.renovar1);
        button3.setOnClickListener(this);

        contagem = (EditText) findViewById(R.id.contagem);

        myLibrary = library.myLibrary;
        username = library.username;
        password = library.password;

        Collections.sort(myLibrary, new CustomComparator());

        library.myLibrary = myLibrary;

        loadSavedPreferences();

        if (!myLibrary.isEmpty()) {
            for (int i = 0; i != myLibrary.size(); i++) {
                LinearLayout placeHolder = (LinearLayout) findViewById(R.id.row9);
                if (i == 0) {
                    placeHolder = (LinearLayout) findViewById(R.id.row0);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro0);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 1) {
                    placeHolder = (LinearLayout) findViewById(R.id.row1);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro1);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 2) {
                    placeHolder = (LinearLayout) findViewById(R.id.row2);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro2);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 3) {
                    placeHolder = (LinearLayout) findViewById(R.id.row3);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro3);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 4) {
                    placeHolder = (LinearLayout) findViewById(R.id.row4);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro4);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 5) {
                    placeHolder = (LinearLayout) findViewById(R.id.row5);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro5);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 6) {
                    placeHolder = (LinearLayout) findViewById(R.id.row6);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro6);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 7) {
                    placeHolder = (LinearLayout) findViewById(R.id.row7);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro7);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 8) {
                    placeHolder = (LinearLayout) findViewById(R.id.row8);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro8);
                    checkBox.setVisibility(View.VISIBLE);
                }
                if (i == 9) {
                    placeHolder = (LinearLayout) findViewById(R.id.row9);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.marcarLivro9);
                    checkBox.setVisibility(View.VISIBLE);
                }
                placeHolder.setVisibility(View.VISIBLE);
                ((TextView) placeHolder.findViewById(R.id.autorLivro)).setText(myLibrary.get(i).author);
                ((TextView) placeHolder.findViewById(R.id.tituloLivro)).setText(myLibrary.get(i).name);
                ((TextView) placeHolder.findViewById(R.id.instituteLivro)).setText(myLibrary.get(i).library);
                ((TextView) placeHolder.findViewById(R.id.dataLivro)).setText(myLibrary.get(i).data);
                String diasa=dias.getRelativeTime(myLibrary.get(i).data);
                ((TextView) placeHolder.findViewById(R.id.dias)).setText(diasa);
                if(Integer.parseInt(diasa)<=2)
                    if(Integer.parseInt(diasa)==0)
                        ((GradientDrawable) (placeHolder.findViewById(R.id.dias)).getBackground()).setColor(Color.RED);
                    else{
                        ((GradientDrawable) (placeHolder.findViewById(R.id.dias)).getBackground()).setColor(Color.YELLOW);
                    }
            }
        } else {
            Log.d("error", "Biblioteca vazia!");
        }
    }

    public void callNotification(String _days, int _inDays){
        SetAlarm alarm = new SetAlarm();
        alarm.execute(Integer.toString(_inDays));
    }




    public NotificationCompat.Builder callNotification(String days){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(LivrosActivity.this)
                        .setSmallIcon(R.drawable.notification_incons)
                        .setContentTitle("Aviso de livro a expirar logo!")
                        .setContentText("Você tem um livro que irá expirar em " + days + " dias.");
        Intent resultIntent = new Intent(LivrosActivity.this, LivrosActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(LivrosActivity.this);
        stackBuilder.addParentStack(LivrosActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.renovar:
                // TODO Auto-generated method stub
                List<String> livrosRenovar = new ArrayList<String>();
                setContentView(R.layout.livros);
                LinearLayout test = (LinearLayout) v.getRootView();
                if (((CheckBox) test.findViewById(R.id.marcarLivro0)).isChecked())
                    livrosRenovar.add(myLibrary.get(0).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro1)).isChecked())
                    livrosRenovar.add(myLibrary.get(1).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro2)).isChecked())
                    livrosRenovar.add(myLibrary.get(2).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro3)).isChecked())
                    livrosRenovar.add(myLibrary.get(3).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro4)).isChecked())
                    livrosRenovar.add(myLibrary.get(4).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro5)).isChecked())
                    livrosRenovar.add(myLibrary.get(5).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro6)).isChecked())
                    livrosRenovar.add(myLibrary.get(6).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro7)).isChecked())
                    livrosRenovar.add(myLibrary.get(7).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro8)).isChecked())
                    livrosRenovar.add(myLibrary.get(8).name);
                if (((CheckBox) test.findViewById(R.id.marcarLivro9)).isChecked())
                    livrosRenovar.add(myLibrary.get(9).name);
                Log.d("debug", Integer.toString(livrosRenovar.size()));
                library.books=livrosRenovar;
                Renovando activity = new Renovando();
                library.myLibrary = myLibrary;
                Intent myIntent = new Intent(this, activity.getClass());
                startActivity(myIntent);
                break;
            case R.id.alerta:
                callNotification(dias.getRelativeTime(myLibrary.get(0).data), Integer.parseInt(contagem.getText().toString()));
                Log.d("debug","\n \n \n ERROR ERROR ERROR \n \n \n");
                savePreferences("days",contagem.getText().toString());
                break;
            case  R.id.renovar1:
                List<String> livrosRenovar2 = new ArrayList<String>();
                for(int i=0; i<myLibrary.size(); i++)
                    if(Integer.parseInt(dias.getRelativeTime(myLibrary.get(i).data))<2)
                        livrosRenovar2.add(myLibrary.get(i).name);
                library.books=livrosRenovar2;
                Renovando activity2 = new Renovando();
                library.myLibrary = myLibrary;
                Intent myIntent2 = new Intent(this, activity2.getClass());
                startActivity(myIntent2);
                break;


        }
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String days = sharedPreferences.getString("days", "");
        contagem.setText(days);
    }

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}