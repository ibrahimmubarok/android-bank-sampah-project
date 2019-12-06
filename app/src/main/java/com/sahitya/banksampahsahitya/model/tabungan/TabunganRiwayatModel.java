package com.sahitya.banksampahsahitya.model.tabungan;

import com.google.gson.annotations.SerializedName;

public class TabunganRiwayatModel {

    @SerializedName("id")
    private int id;

    @SerializedName("penarikan")
    private int penarikan;

    @SerializedName("created_at")
    private String createdAt;

    public TabunganRiwayatModel(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPenarikan() {
        return penarikan;
    }

    public void setPenarikan(int penarikan) {
        this.penarikan = penarikan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
