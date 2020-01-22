package com.sahitya.banksampahsahitya.utils;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

    public void asyncTabungan(int id, ProgressBar loading, LinearLayout linearLayout, FrameLayout frameLayout){
        TabunganService tabunganService = ApiClient.getClient().create(TabunganService.class);

        final ArrayList<TabunganRiwayatModel> riwayatArrayList = new ArrayList<>();

        Call<TabunganModel> call = tabunganService.getTabunganUser(id);
        call.enqueue(new Callback<TabunganModel>() {
            @Override
            public void onResponse(Call<TabunganModel> call, Response<TabunganModel> response) {

                ArrayList<TabunganRiwayatModel> riwayatList = response.body().getRiwayat();
                ArrayList<TabunganModel> tabunganList = new ArrayList<>();

                if (!response.isSuccessful()){
                    return;
                }

                TabunganModel tm = new TabunganModel();

                tm.setSaldo(response.body().getSaldo());
                tm.setBerat(response.body().getBerat());

                tabunganList.add(tm);

                Log.d("Saldo", String.valueOf(tabunganList.get(0).getSaldo()));
                Log.d("berat", String.valueOf(tabunganList.get(0).getBerat()));

                for (TabunganRiwayatModel r : riwayatList){
                    TabunganRiwayatModel tb = new TabunganRiwayatModel();

                    tb.setPenarikan(r.getPenarikan());
                    Log.d(TAG, String.valueOf(r.getPenarikan()));
                    tb.setCreatedAt(r.getCreatedAt());
                    Log.d(TAG, r.getCreatedAt());
                    tb.setCreatedAt(String.valueOf(r.getCreatedAt()));

                    riwayatArrayList.add(tb);
                }

                mutableLiveDataRiwayat.postValue(riwayatArrayList);
                mutableLiveDataTabungan.postValue(tabunganList);

                linearLayout.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TabunganModel> call, Throwable t) {
                Log.e(TAG, t.toString());
                linearLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
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
