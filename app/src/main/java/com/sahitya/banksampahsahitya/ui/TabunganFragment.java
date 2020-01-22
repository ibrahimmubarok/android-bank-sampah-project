package com.sahitya.banksampahsahitya.ui;


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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganModel;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganRiwayatModel;
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
    @BindView(R.id.tv_riwayat_transaksi)
    TextView tvRiwayatTransaksi;
    @BindView(R.id.tv_created_at_riwayat_transaksi)
    TextView tvTanggalRiwayat;
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

    private Unbinder unbinder;

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

        progressBar.setVisibility(View.VISIBLE);

        tvNamaNasabah.setText(" "+MainActivity.namaNasabah);

        tabunganUtils.asyncTabungan(4, progressBar, linearLayoutTabungan, noKoneksiLayout);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                noKoneksiLayout.setVisibility(View.GONE);
                tabunganUtils.asyncTabungan(MainActivity.idUser, progressBar, linearLayoutTabungan, noKoneksiLayout);
            }
        });
    }

    private Observer<ArrayList<TabunganRiwayatModel>> getRiwayatTabunganUser = new Observer<ArrayList<TabunganRiwayatModel>>() {
        @Override
        public void onChanged(ArrayList<TabunganRiwayatModel> riwayat) {
            if (!riwayat.isEmpty()){
                double riwayatSaldo = riwayat.get(0).getPenarikan();
                tvRiwayatTransaksi.setText(getSaldoNumberFormat(riwayatSaldo));
                tvTanggalRiwayat.setText(riwayat.get(0).getCreatedAt());
                Log.d("CreatedAt", riwayat.get(0).getCreatedAt());
                progressBar.setVisibility(View.GONE);
            }else{
                progressBar.setVisibility(View.GONE);
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
