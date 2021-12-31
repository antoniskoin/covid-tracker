package com.antonisk.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread processData = new Thread(() -> {
            try {
                jsonData = getJsonData();
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject summary = new JSONObject(jsonObject.getString("summaryStats"));
                JSONObject globalObject = new JSONObject(summary.getString("global"));
                String totalConfirmed = globalObject.getString("confirmed");
                String totalDeaths = globalObject.getString("deaths");
                ArrayList<CovidData> covidData = processCovidData(jsonObject);

                runOnUiThread(() -> {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("CONFIRMED_GLOBAL", totalConfirmed);
                    intent.putExtra("DEATHS_GLOBAL", totalDeaths);
                    MainActivity.data = covidData; // Pass data through a static variable to avoid TransactionTooLargeException.
                    startActivity(intent);
                    finish();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        processData.start();
    }

    private ArrayList<CovidData> processCovidData(JSONObject jsonObject) {
        ArrayList<CovidData> data = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonObject.getString("rawData"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObject = new JSONObject(jsonArray.get(i).toString());
                String country = jObject.getString("Country_Region");
                String province = jObject.getString("Province_State");
                String confirmed = jObject.getString("Confirmed");
                String deaths = jObject.getString("Deaths");
                String updated = jObject.getString("Last_Update");
                String fatalityRatio = jObject.getString("Case_Fatality_Ratio");

                if (fatalityRatio.equals("")) {
                    CovidData covidData = new CovidData(country, province, confirmed, deaths, updated, "N/A");
                    data.add(covidData);
                } else {
                    double convertedRatio = Math.round(Float.parseFloat(fatalityRatio) * 100.0) / 100.0;
                    CovidData covidData = new CovidData(country, province, confirmed, deaths, updated, Double.toString(convertedRatio));
                    data.add(covidData);
                }
            }
            return data;
        } catch (Exception e) {
            return data;
        }
    }

    private String getJsonData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://coronavirus.m.pipedream.net/")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            return "";
        }
    }
}