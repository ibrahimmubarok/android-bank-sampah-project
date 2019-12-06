package com.sahitya.banksampahsahitya.ui;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganModel;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganRiwayatModel;
import com.sahitya.banksampahsahitya.utils.TabunganUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabunganFragment extends Fragment {

    private TextView tvTotalUang, tvTotalSampah, tvRiwayatTransaksi;

    private ProgressDialog loading;

    public TabunganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabungan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabunganUtils tabunganUtils = ViewModelProviders.of(this).get(TabunganUtils.class);
        tabunganUtils.getLiveDataRiwayat().observe(this, getRiwayatTabunganUser);
        tabunganUtils.getMutableLiveDataTabungan().observe(this, getTabunganUser);

        loading = ProgressDialog.show(getContext(), null, "Harap Tunggu...", true, false);

        tvTotalUang = view.findViewById(R.id.tv_total_uang);
        tvTotalSampah = view.findViewById(R.id.tv_amount_of_waste);
        tvRiwayatTransaksi = view.findViewById(R.id.tv_riwayat_transaksi);

        tabunganUtils.asyncTabungan(MainActivity.idUser);
    }

    private Observer<ArrayList<TabunganRiwayatModel>> getRiwayatTabunganUser = new Observer<ArrayList<TabunganRiwayatModel>>() {
        @Override
        public void onChanged(ArrayList<TabunganRiwayatModel> riwayat) {
            if (riwayat != null){
//                Glide.with(getContext())
//                        .load(profileUserModel.get(0).getFoto())
//                        .into(imgAvatar);
//                tvRiwayatTransaksi.setText(riwayat.get(riwayat.lastIndexOf(riwayat)).getPenarikan());
                Log.d("TabunganUtils", "Ada Data");
            }
        }
    };

    private Observer<ArrayList<TabunganModel>> getTabunganUser = new Observer<ArrayList<TabunganModel>>() {
        @Override
        public void onChanged(ArrayList<TabunganModel> tabunganModel) {
            if (tabunganModel != null){
                tvTotalSampah.setText(String.valueOf(tabunganModel.get(0).getBerat()));
                tvTotalUang.setText(String.valueOf(tabunganModel.get(0).getSaldo()));
                loading.dismiss();
            }
        }
    };
}
