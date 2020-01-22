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

import com.sahitya.banksampahsahitya.model.GarbageInfoModel;
import com.sahitya.banksampahsahitya.rest.client.ApiClient;
import com.sahitya.banksampahsahitya.rest.service.GarbageInfoService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GarbageInfoApiUtils extends ViewModel {
    private final String TAG = GarbageInfoModel.class.getSimpleName();

    private MutableLiveData<ArrayList<GarbageInfoModel>> mutableLiveDataGarbageInfo = new MutableLiveData<>();

    public void asyncGarbageInfoData(ProgressBar loading, LinearLayout linearLayout, FrameLayout frameLayout){
        GarbageInfoService garbageInfoService = ApiClient.getClient().create(GarbageInfoService.class);

        final ArrayList<GarbageInfoModel> listGarbageInfo = new ArrayList<>();

        Call<ArrayList<GarbageInfoModel>> call = garbageInfoService.getInfoSampah();
        call.enqueue(new Callback<ArrayList<GarbageInfoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GarbageInfoModel>> call, Response<ArrayList<GarbageInfoModel>> response) {
                ArrayList<GarbageInfoModel> garbageInfoList = response.body();

                if (!response.isSuccessful()){
                    return;
                }

                for (GarbageInfoModel g : garbageInfoList){
                    GarbageInfoModel garbageInfoModel = new GarbageInfoModel();

                    garbageInfoModel.setId(g.getId());
                    garbageInfoModel.setKategori(g.getKategori());
                    garbageInfoModel.setNama(g.getNama());
                    garbageInfoModel.setHargaPerKilo(g.getHargaPerKilo());

                    listGarbageInfo.add(garbageInfoModel);
                }
                mutableLiveDataGarbageInfo.postValue(listGarbageInfo);

                linearLayout.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<GarbageInfoModel>> call, Throwable t) {
                Log.d(TAG, t.toString());
                linearLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }
        });
    }

    public LiveData<ArrayList<GarbageInfoModel>> getLiveDataGarbageInfo(){
        return mutableLiveDataGarbageInfo;
    }
}
