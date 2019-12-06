package com.sahitya.banksampahsahitya.utils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sahitya.banksampahsahitya.model.tabungan.TabunganModel;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganRiwayatModel;
import com.sahitya.banksampahsahitya.rest.client.ApiClient;
import com.sahitya.banksampahsahitya.rest.service.TabunganService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabunganUtils extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    private MutableLiveData<ArrayList<TabunganRiwayatModel>> mutableLiveDataRiwayat = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TabunganModel>> mutableLiveDataTabungan = new MutableLiveData<>();

    public void asyncTabungan(int id){
        TabunganService tabunganService = ApiClient.getClient().create(TabunganService.class);

        final ArrayList<TabunganRiwayatModel> tabunganArrayList = new ArrayList<>();

        Call<TabunganModel> call = tabunganService.getTabunganUser(id);
        call.enqueue(new Callback<TabunganModel>() {
            @Override
            public void onResponse(Call<TabunganModel> call, Response<TabunganModel> response) {
                ArrayList<TabunganRiwayatModel> riwayatList = response.body().getRiwayat();

                ArrayList<TabunganModel> tabunganList = new ArrayList<>();

                if (!response.isSuccessful()){
                    return;
                }

                Log.d("Saldo", String.valueOf(response.body().getSaldo()));
                Log.d("berat", String.valueOf(response.body().getBerat()));

                for (TabunganRiwayatModel r : riwayatList){
                    TabunganRiwayatModel tb = new TabunganRiwayatModel();

                    tb.setPenarikan(r.getPenarikan());
                    Log.d(TAG, String.valueOf(r.getPenarikan()));
                    tb.setCreatedAt(r.getCreatedAt());
                    Log.d(TAG, r.getCreatedAt());
                    tb.setCreatedAt(String.valueOf(r.getId()));

                    tabunganArrayList.add(tb);
                }

                tabunganList.add(new TabunganModel());

                mutableLiveDataRiwayat.postValue(tabunganArrayList);

                mutableLiveDataTabungan.postValue(tabunganList);
            }

            @Override
            public void onFailure(Call<TabunganModel> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<TabunganRiwayatModel>> getLiveDataRiwayat(){
        return mutableLiveDataRiwayat;
    }

    public MutableLiveData<ArrayList<TabunganModel>> getMutableLiveDataTabungan() {
        return mutableLiveDataTabungan;
    }
}
