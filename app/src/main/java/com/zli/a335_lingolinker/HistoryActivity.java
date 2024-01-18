package com.zli.a335_lingolinker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout historyLayout;

    private Button returnButton;

    private TextView historyWords;

    public List<TranslatedWords> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initViews();
        initOnclicks();
    }

    protected void initViews() {
        returnButton = findViewById(R.id.backButton);
        historyLayout = findViewById(R.id.historyLayout);
    }

    protected void initOnclicks() {
        onClickReturn();
    }

    protected void onClickReturn() {
        returnButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent(this, MainActivity.class);
            startActivity(resultIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        list = getListFromSharedPreferences();
        initHistoryWords();
    }

    private List<TranslatedWords> getListFromSharedPreferences() {
        SharedPreferences sharedPref = this.getSharedPreferences("historyOfTranslations", Context.MODE_PRIVATE);
        String json = sharedPref.getString("wordList", "[]");

        Gson gson = new Gson();
        Type type = new TypeToken<List<TranslatedWords>>(){}.getType();

        return gson.fromJson(json, type);
    }

    protected void initHistoryWords() {
        for (int i = 0; i < list.size(); i++) {
            setTextView(i);
        }
    }

    private void setTextView(int i) {
        TranslatedWords translatedWords = list.get(i);

        String original = translatedWords.getOriginal();
        String translated = translatedWords.getTranslated();
        String language = translatedWords.getLanguage();

        TextView textView = new TextView(this);
        String text = "Or: " + original + " Tr: " + translated + "  Ln: " + language;
        textView.setText(text);
        historyLayout.addView(textView);
    }
}