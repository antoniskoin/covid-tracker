package com.antonisk.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<CovidData> data;
    private final LayoutInflater mInflater;
    private final Context context;

    DataAdapter(Context context, ArrayList<CovidData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.data_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String country = data.get(position).getCountry();
        String province = data.get(position).getProvince();
        String confirmedCases = data.get(position).getConfirmed();
        String deaths = data.get(position).getDeaths();
        String updated = data.get(position).getUpdated();
        String fatalityRatio = data.get(position).getCaseFatalityRation();

        if (!province.equals("")) {
            holder.txtCountry.setText(context.getString(R.string.country_province, country, province));
        } else {
            holder.txtCountry.setText(country);
        }

        holder.txtConfirmed.setText(context.getString(R.string.confirmed, confirmedCases));
        holder.txtDeaths.setText(context.getString(R.string.deaths, deaths));
        holder.txtUpdated.setText(context.getString(R.string.last_update, updated));
        holder.txtFatalityRatio.setText(context.getString(R.string.fatality_ratio, fatalityRatio));
    }

    public void filterList(ArrayList<CovidData> filteredList) {
        data = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCountry, txtConfirmed, txtDeaths, txtUpdated, txtFatalityRatio;

        ViewHolder(View itemView) {
            super(itemView);
            txtCountry = itemView.findViewById(R.id.txtCountry);
            txtConfirmed = itemView.findViewById(R.id.txtConfirmed);
            txtDeaths = itemView.findViewById(R.id.txtDeaths);
            txtUpdated = itemView.findViewById(R.id.txtUpdated);
            txtFatalityRatio = itemView.findViewById(R.id.txtFatalityRatio);
        }
    }
}
