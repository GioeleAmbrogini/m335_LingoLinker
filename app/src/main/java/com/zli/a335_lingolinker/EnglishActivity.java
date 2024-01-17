package com.zli.a335_lingolinker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EnglishActivity extends AppCompatActivity {

    private Button returnButton;

    private Button translateButton;

    private EditText inputText;

    private TextView translatedText;

    public static final String TRANSLATION_LANGUAGE = "lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        initViews();
        initOnclicks();
    }

    protected void initViews() {
        returnButton = findViewById(R.id.backButton);
        translateButton = findViewById(R.id.translateButton);
        inputText = findViewById(R.id.inputText);
        translatedText = findViewById(R.id.translatedText);
    }

    protected void initOnclicks() {
        onClickReturn();
        onClickTranslate();
    }

    protected void onClickReturn() {
        returnButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent(this, MainActivity.class);
            startActivity(resultIntent);
        });
    }

    protected void onClickTranslate() {
        translateButton.setOnClickListener(view -> {
            String toTranslatingText = inputText.getText().toString();
            translatedText.setText(toTranslatingText);
        });
    }
}