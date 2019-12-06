package com.sahitya.banksampahsahitya.model.tabungan;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TabunganModel {

    @SerializedName("saldo")
    private int saldo;

    @SerializedName("berat")
    private double berat;

    @SerializedName("riwayat")
    private ArrayList<TabunganRiwayatModel> riwayat;

    public TabunganModel(){
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public ArrayList<TabunganRiwayatModel> getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(ArrayList<TabunganRiwayatModel> riwayat) {
        this.riwayat = riwayat;
    }
}
