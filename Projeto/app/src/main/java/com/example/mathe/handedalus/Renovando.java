package com.example.mathe.handedalus;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Mathe on 07/03/2017.
 */

public class Renovando extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carregando);
        try {
            network net = new network(this);
            net.execute(library.username, library.password,"renove");
        }catch (Exception e){
            ;
        }
    }
}