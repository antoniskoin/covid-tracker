package com.antonisk.covidtracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<CovidData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String totalConfirmed = intent.getStringExtra("CONFIRMED_GLOBAL");
        String totalDeaths = intent.getStringExtra("DEATHS_GLOBAL");

        TextView txtConfirmed = findViewById(R.id.txtConfirmedWorld);
        TextView txtDeaths = findViewById(R.id.txtDeathsWorld);
        TextInputEditText txtSearch = findViewById(R.id.txtSearchCountry);

        txtConfirmed.setText(getString(R.string.confirmed, totalConfirmed));
        txtDeaths.setText(getString(R.string.deaths, totalDeaths));

        RecyclerView recyclerView = findViewById(R.id.lstData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DataAdapter adapter = new DataAdapter(getApplicationContext(), data);
        recyclerView.setAdapter(adapter);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString(), adapter);
            }
        });
    }

    private void filter(String query, DataAdapter dataAdapter) {
        ArrayList<CovidData> filteredData = new ArrayList<>();

        for (CovidData item : data) {
            if (item.getCountry().toLowerCase().contains(query.toLowerCase())) {
                filteredData.add(item);
            }
        }

        dataAdapter.filterList(filteredData);
    }
}