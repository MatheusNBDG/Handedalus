package com.example.mathe.handedalus;

/**
 * Created by Mathe on 04/03/2017.
 */

public class book {
    String name;
    String data;
    String author;
    String library;
    int days;

    public book(){
        this.name="";
        this.data="";
        this.author="";
        this.library="";
        int days=0;
    }

    public int days(){
        return this.days;
    }
}