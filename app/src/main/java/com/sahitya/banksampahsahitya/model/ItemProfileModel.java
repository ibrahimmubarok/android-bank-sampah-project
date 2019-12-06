package com.sahitya.banksampahsahitya.model;

public class ItemProfileModel {

    private int imgProfile;

    private String tvProfile;

    public ItemProfileModel(int imgProfile, String tvProfile) {
        this.imgProfile = imgProfile;
        this.tvProfile = tvProfile;
    }

    public int getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(int imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getTvProfile() {
        return tvProfile;
    }

    public void setTvProfile(String tvProfile) {
        this.tvProfile = tvProfile;
    }
}
