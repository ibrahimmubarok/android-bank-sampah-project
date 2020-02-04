package com.sahitya.banksampahsahitya.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifikasiModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("kode_verifikasi")
    @Expose
    private String kodeVerifikasi;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("nim")
    private String nim;

    @SerializedName("fakultas")
    private String fakultas;

    @SerializedName("jurusan")
    private String jurusan;

    @SerializedName("hp")
    private String hp;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("tanggal_lahir")
    private String ttl;

    @SerializedName("foto")
    private String foto;

    public VerifikasiModel(){
    }

    public VerifikasiModel(int id, String kodeVerifikasi, String name, String email, String nim, String fakultas, String jurusan, String hp, String alamat, String ttl, String foto) {
        this.id = id;
        this.kodeVerifikasi = kodeVerifikasi;
        this.name = name;
        this.email = email;
        this.nim = nim;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
        this.hp = hp;
        this.alamat = alamat;
        this.ttl = ttl;
        this.foto = foto;
    }

    protected VerifikasiModel(Parcel in) {
        id = in.readInt();
        kodeVerifikasi = in.readString();
        name = in.readString();
        email = in.readString();
        nim = in.readString();
        fakultas = in.readString();
        jurusan = in.readString();
        hp = in.readString();
        alamat = in.readString();
        ttl = in.readString();
        foto = in.readString();
    }

    public static final Creator<VerifikasiModel> CREATOR = new Creator<VerifikasiModel>() {
        @Override
        public VerifikasiModel createFromParcel(Parcel in) {
            return new VerifikasiModel(in);
        }

        @Override
        public VerifikasiModel[] newArray(int size) {
            return new VerifikasiModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeVerifikasi() {
        return kodeVerifikasi;
    }

    public void setKodeVerifikasi(String kodeVerifikasi) {
        this.kodeVerifikasi = kodeVerifikasi;
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

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "VerifikasiModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", nim='" + nim + '\'' +
                ", fakultas='" + fakultas + '\'' +
                ", jurusan='" + jurusan + '\'' +
                ", hp='" + hp + '\'' +
                ", alamat='" + alamat + '\'' +
                ", ttl='" + ttl + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(kodeVerifikasi);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(nim);
        parcel.writeString(fakultas);
        parcel.writeString(jurusan);
        parcel.writeString(hp);
        parcel.writeString(alamat);
        parcel.writeString(ttl);
        parcel.writeString(foto);
    }
}
