package com.sahitya.banksampahsahitya.model.ranking;

import com.google.gson.annotations.SerializedName;



public class RankingModel {
    @SerializedName("berat")
    private double berat;

    @SerializedName("name")
    private String name;

    public RankingModel(double berat, String name) {
        this.berat = berat;
        this.name = name;
    }

    public RankingModel(){
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
