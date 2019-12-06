package com.sahitya.banksampahsahitya.rest.service;

import com.sahitya.banksampahsahitya.model.ProfileUserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfileService {
    @GET("profil/{id}")
    Call<ProfileUserModel> getProfileUser(
            @Path("id") int id);
}
