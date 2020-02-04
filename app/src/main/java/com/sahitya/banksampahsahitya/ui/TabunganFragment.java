package com.sahitya.banksampahsahitya.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganModel;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganRiwayatModel;
import com.sahitya.banksampahsahitya.presentation.adapter.PreviewDisbursementAdapter;
import com.sahitya.banksampahsahitya.presentation.membership.disbursement.DisbursementActivity;
import com.sahitya.banksampahsahitya.utils.TabunganUtils;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabunganFragment extends Fragment {

    public static final String TAG = TabunganFragment.class.getSimpleName();

    @BindView(R.id.tv_total_uang)
    TextView tvTotalUang;
    @BindView(R.id.tv_amount_of_waste)
    TextView tvTotalSampah;
    @BindView(R.id.main_layout_tabungan)
    LinearLayout linearLayoutTabungan;
    @BindView(R.id.layout_no_koneksi)
    FrameLayout noKoneksiLayout;
    @BindView(R.id.tv_refresh_connection)
    TextView tvRefresh;
    @BindView(R.id.progress_bar_tabungan)
    ProgressBar progressBar;
    @BindView(R.id.tv_detail_riwayat_transaksi_tabungan)
    TextView tvDetail;
    @BindView(R.id.tv_nama_nasabah)
    TextView tvNamaNasabah;
    @BindView(R.id.view_riwayat_transaksi)
    LinearLayout viewRiwayatTransaksi;
    @BindView(R.id.rv_preview_riwayat_transaksi)
    RecyclerView rvPreview;
    @BindView(R.id.tv_preview_riwayat_kosong)
    TextView tvKosong;

    private Unbinder unbinder;

    private PreviewDisbursementAdapter adapter;
    private ArrayList<TabunganRiwayatModel> riwayatList;

    public TabunganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabungan, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabunganUtils tabunganUtils = ViewModelProviders.of(this).get(TabunganUtils.class);
        tabunganUtils.getLiveDataRiwayat().observe(this, getRiwayatTabunganUser);
        tabunganUtils.getMutableLiveDataTabungan().observe(this, getTabunganUser);

        riwayatList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);

        tvNamaNasabah.setText(" "+MainActivity.namaNasabah);

        adapter = new PreviewDisbursementAdapter(getContext(), riwayatList);
        adapter.notifyDataSetChanged();

        rvPreview.setHasFixedSize(true);
        rvPreview.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPreview.setAdapter(adapter);

        tabunganUtils.asyncTabungan(MainActivity.idUser, progressBar, linearLayoutTabungan, noKoneksiLayout);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                noKoneksiLayout.setVisibility(View.GONE);
                tabunganUtils.asyncTabungan(MainActivity.idUser, progressBar, linearLayoutTabungan, noKoneksiLayout);
            }
        });

        viewRiwayatTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisbursementActivity.start(getContext());
            }
        });
    }

    private Observer<ArrayList<TabunganRiwayatModel>> getRiwayatTabunganUser = new Observer<ArrayList<TabunganRiwayatModel>>() {
        @Override
        public void onChanged(ArrayList<TabunganRiwayatModel> riwayat) {
            if (!riwayat.isEmpty()){

                ArrayList<TabunganRiwayatModel> tempList = new ArrayList<>();

                if (riwayat.size() > 3){
                    int length = riwayat.size();
                    int k = 1;
                    for (int i=0; i<length; i++){
                        int j=0;
                        if (riwayat.size() > 3) {
                            riwayat.remove(j);
                        }else{
                            tempList.add(riwayat.get(riwayat.size()-k));
                            k++;
                        }
                    }
                    Log.d("TEMPE", String.valueOf(tempList.size()));
                    adapter.setData(tempList);
                }else {
                    adapter.setData(riwayat);
                }
                Log.d("CreatedAt", riwayat.get(0).getCreatedAt());
                progressBar.setVisibility(View.GONE);
                tvKosong.setVisibility(View.GONE);
                rvPreview.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.GONE);
                tvKosong.setVisibility(View.VISIBLE);
                rvPreview.setVisibility(View.GONE);
            }
        }
    };

    private Observer<ArrayList<TabunganModel>> getTabunganUser = new Observer<ArrayList<TabunganModel>>() {
        @Override
        public void onChanged(ArrayList<TabunganModel> tabunganModel) {
            if (!tabunganModel.isEmpty()){
                double saldoUser = tabunganModel.get(0).getSaldo();
                tvTotalSampah.setText(String.valueOf(tabunganModel.get(0).getBerat()));
                tvTotalUang.setText(getSaldoNumberFormat(saldoUser));
                progressBar.setVisibility(View.GONE);
            }else{
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    private String getSaldoNumberFormat(double saldo){
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(saldo);
    }
}
