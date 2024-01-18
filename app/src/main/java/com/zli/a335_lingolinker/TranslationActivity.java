package com.zli.a335_lingolinker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationActivity extends AppCompatActivity {
    private Button returnButton;

    private Button translateButton;

    private EditText inputText;

    private TextView translatedText;

    private String language;

    private TextView title;

    private String toTranslatingText;

    private RequestQueue mRequestQueue;

    private StringRequest mStringRequest;

    private String url = "https://api-free.deepl.com/v2/translate";

    // Not Optimal but for the testing and grading i'm doing it like this
    private String apiKey = "DeepL-Auth-Key ede6af0c-469a-0e94-7d82-f25e66cb72a2:fx";

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
        Intent intent = getIntent();
        language = intent.getStringExtra("lang");
        if (language.equals("en-GB")) {
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
            toTranslatingText = inputText.getText().toString();
            getData();
        });
    }

    private void setSuccesfulText(String succesfulText) {
        translatedText.setText(succesfulText);
        saveDataIntoList(succesfulText);
    }

    private void saveDataIntoList(String succesfulText) {
        TranslatedWords newTranslation = new TranslatedWords(toTranslatingText, succesfulText, language);
        list.add(newTranslation);
    }

    private void getData() {
        JSONObject jsonPayload = getJsonObject();
        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    getSuccessfulString(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, getErrorListener()) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonPayload.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", apiKey);
                return headers;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    private void getSuccessfulString(String response) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);

        JSONArray translationsArray = jsonResponse.getJSONArray("translations");

        JSONObject translationObject = translationsArray.getJSONObject(0);

        String succesfultext = translationObject.getString("text");

        setSuccesfulText(succesfultext);

        Log.d(TAG, succesfultext);
    }

    @NonNull
    private static Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        };
    }

    @NonNull
    private JSONObject getJsonObject() {
        mRequestQueue = Volley.newRequestQueue(this);

        JSONArray textArray = new JSONArray();
        textArray.put(toTranslatingText);

        JSONObject jsonPayload = new JSONObject();
        try {
            jsonPayload.put("text", textArray);
            jsonPayload.put("target_lang", language);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonPayload;
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPref = this.getSharedPreferences("historyOfTranslations", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();

        String json = gson.toJson(list);

        editor.putString("wordList", json);

        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        list = getListFromSharedPreferences();
    }

    private List<TranslatedWords> getListFromSharedPreferences() {
        SharedPreferences sharedPref = this.getSharedPreferences("historyOfTranslations", Context.MODE_PRIVATE);
        String json = sharedPref.getString("wordList", "[]");

        Gson gson = new Gson();
        Type type = new TypeToken<List<TranslatedWords>>(){}.getType();

        return gson.fromJson(json, type);
    }
}