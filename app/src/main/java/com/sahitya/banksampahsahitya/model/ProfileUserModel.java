package com.sahitya.banksampahsahitya.model;

import com.google.gson.annotations.SerializedName;

public class ProfileUserModel {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("foto")
    private String foto;

    public ProfileUserModel(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "ProfileUserModel{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
