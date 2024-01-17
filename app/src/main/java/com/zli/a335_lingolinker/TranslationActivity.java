package com.zli.a335_lingolinker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TranslationActivity extends AppCompatActivity {

    private Button returnButton;

    private Button translateButton;

    private EditText inputText;

    private TextView translatedText;

    private String language;

    private TextView title;

    public static final String TRANSLATION_LANGUAGE = "lang";

    public List<TranslatedWords> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        initViews();
        initOnclicks();
        initParam();
    }

    protected void initParam() {
        Intent intent =getIntent();
        language = intent.getStringExtra("lang");
        if (language.equals("EN")) {
            title.setText("English");
        } else {
            title.setText("French");
        }
    }

    protected void initViews() {
        returnButton = findViewById(R.id.backButton);
        translateButton = findViewById(R.id.translateButton);
        inputText = findViewById(R.id.inputText);
        translatedText = findViewById(R.id.translatedText);
        title = findViewById(R.id.appTitle);
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
            String test = "hello";
            translatedText.setText(toTranslatingText);

            TranslatedWords words = new TranslatedWords(toTranslatingText, test, language);
            list.add(words);
        });
    }
}