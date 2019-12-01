package com.sahitya.banksampahsahitya.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("error")
    private String error;

    @SerializedName("verified")
    private int verified;

    @SerializedName("kode_verifikasi")
    private String kodeVerifikasi;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    public LoginModel(){
    }

    protected LoginModel(Parcel in) {
        id = in.readInt();
        verified = in.readInt();
        kodeVerifikasi = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getKodeVerifikasi() {
        return kodeVerifikasi;
    }

    public void setKodeVerifikasi(String kodeVerifikasi) {
        this.kodeVerifikasi = kodeVerifikasi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "id=" + id +
                ", verified=" + verified +
                ", kodeVerifikasi='" + kodeVerifikasi + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(verified);
        parcel.writeString(kodeVerifikasi);
        parcel.writeString(email);
        parcel.writeString(password);
    }
}
