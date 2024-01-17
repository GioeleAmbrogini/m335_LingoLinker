package com.zli.a335_lingolinker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EnglishActivity extends AppCompatActivity {

    public static final String TRANSLATION_LANGUAGE = "lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);
    }
}