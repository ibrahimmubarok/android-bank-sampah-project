package com.sahitya.banksampahsahitya.rest.service;

import com.sahitya.banksampahsahitya.model.tabungan.TabunganModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TabunganService {
    @GET("tabungan/{id}")
    Call<TabunganModel> getTabunganUser(
            @Path("id") int id
    );
}
