package com.antonisk.covidtracker;

import java.io.Serializable;

public class CovidData implements Serializable {
    private String country, province, confirmed, deaths, updated, caseFatalityRation;

    public CovidData(String country, String province, String confirmed, String deaths, String updated, String caseFatalityRation) {
        this.country = country;
        this.province = province;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.updated = updated;
        this.caseFatalityRation = caseFatalityRation;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getUpdated() {
        return updated;
    }

    public String getCaseFatalityRation() {
        return caseFatalityRation;
    }
}
