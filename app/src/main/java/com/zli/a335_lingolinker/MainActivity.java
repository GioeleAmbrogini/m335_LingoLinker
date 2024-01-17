package com.zli.a335_lingolinker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button englishButton;

    private Button frenchButton;

    private Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initOnclicks();
    }

    protected void initViews() {
        englishButton = findViewById(R.id.englishButton);
        frenchButton = findViewById(R.id.frenchButton);
        historyButton = findViewById(R.id.historyButton);
    }

    protected void initOnclicks() {
        onClickEnglish();
        onClickFrench();
    }

    protected void onClickEnglish() {
        englishButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent(this, TranslationActivity.class);
            resultIntent.putExtra(TranslationActivity.TRANSLATION_LANGUAGE, "EN");
            startActivity(resultIntent);
        });
    }

    protected void onClickFrench() {
        frenchButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent(this, TranslationActivity.class);
            resultIntent.putExtra(TranslationActivity.TRANSLATION_LANGUAGE, "FR");
            startActivity(resultIntent);
        });
    }
}