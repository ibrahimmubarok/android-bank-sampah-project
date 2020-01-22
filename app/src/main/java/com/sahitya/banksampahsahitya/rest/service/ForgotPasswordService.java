package com.sahitya.banksampahsahitya.rest.service;

import com.sahitya.banksampahsahitya.model.ForgotPasswordModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ForgotPasswordService {
    @POST("lupa_password")
    @FormUrlEncoded
    Call<ForgotPasswordModel> saveForgotPasswordPost(
            @Field("email") String email
    );
}
