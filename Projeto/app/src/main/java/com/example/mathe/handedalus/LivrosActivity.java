package com.example.mathe.handedalus;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.vision.text.Line;

import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.delay;
import static com.example.mathe.handedalus.R.id.numUsp;
import static com.example.mathe.handedalus.R.id.password;
import static com.example.mathe.handedalus.R.layout.row;

/**
 * Created by Mathe on 04/03/2017.
 */

public class LivrosActivity extends Activity implements View.OnClickListener {

    static List<book> myLibrary;
    static String username;
    static String password;
    private Button button;
    private Button button2;
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
                ((TextView) placeHolder.findViewById(R.id.dataLivro)).setText(myLibrary.get(i).data + " (" + dias.getRelativeTime(myLibrary.get(i).data) + ")");
            }
        } else {
            Log.d("error", "Biblioteca vazia!");
        }
    }

    public void callNotification(String _days, int _inDays){
        Calendar c = Calendar.getInstance();
        long firstTime;
        if(_inDays!=0)  {
            c.add(Calendar.DATE, _inDays);
        }
        firstTime = c.getTimeInMillis();

        library.days=_days;
        Notification notification = onRunTask.callNotification(_days,this).build();

        Intent notificationIntent = new Intent(this, onRunTask.class);
        notificationIntent.putExtra(onRunTask.NOTIFICATION_ID, onRunTask.NOTIFICATION_ID);
        notificationIntent.putExtra(onRunTask.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, firstTime, pendingIntent);
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