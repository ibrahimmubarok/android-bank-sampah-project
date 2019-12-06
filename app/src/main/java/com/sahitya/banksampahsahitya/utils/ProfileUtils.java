package com.sahitya.banksampahsahitya.utils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sahitya.banksampahsahitya.model.ProfileUserModel;
import com.sahitya.banksampahsahitya.rest.client.ApiClient;
import com.sahitya.banksampahsahitya.rest.service.ProfileService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUtils extends ViewModel {
    private final String TAG = ProfileUtils.class.getSimpleName();

    private MutableLiveData<ArrayList<ProfileUserModel>> mutableLiveDataProfile = new MutableLiveData<>();

    public void asyncProfileUser(int id){
        ProfileService profileService = ApiClient.getClient().create(ProfileService.class);

        final ArrayList<ProfileUserModel> listProfile = new ArrayList<>();

        Log.d(TAG, String.valueOf(id));

        Call<ProfileUserModel> call = profileService.getProfileUser(id);
        call.enqueue(new Callback<ProfileUserModel>() {
            @Override
            public void onResponse(Call<ProfileUserModel> call, Response<ProfileUserModel> response) {
                if (!response.isSuccessful()){
                    return;
                }

                ProfileUserModel profileModel = new ProfileUserModel();

                profileModel.setName(response.body().getName());
                profileModel.setEmail(response.body().getEmail());
                profileModel.setFoto(response.body().getFoto());

                listProfile.add(profileModel);

                mutableLiveDataProfile.postValue(listProfile);
            }

            @Override
            public void onFailure(Call<ProfileUserModel> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<ProfileUserModel>> getLiveDataProfileUser(){
        return mutableLiveDataProfile;
    }
}
