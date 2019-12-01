package com.sahitya.banksampahsahitya.rest.service;

import com.sahitya.banksampahsahitya.model.VerifikasiModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VerificationService {
    @POST("verifikasi")
    @FormUrlEncoded
    Call<VerifikasiModel> saveVerifikasiPost(
            @Field("id") int id,
            @Field("kode_verifikasi") String kodeVerifikasi
    );
}
