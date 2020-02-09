package com.sahitya.banksampahsahitya.presentation.membership.disbursement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganModel;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganRiwayatModel;
import com.sahitya.banksampahsahitya.presentation.adapter.DisbursementAdapter;
import com.sahitya.banksampahsahitya.utils.TabunganUtils;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DisbursementActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_disbursement)
    Toolbar toolbar;
    @BindView(R.id.tv_jumlah_pencairan_disbursement)
    TextView tvPencairan;
    @BindView(R.id.tv_kosong_disbursement)
    TextView tvKosong;
    @BindView(R.id.progress_bar_disbursement)
    ProgressBar progressBar;
    @BindView(R.id.layout_disbursement)
    LinearLayout linearLayoutDisbursement;
    @BindView(R.id.rv_disbursement)
    RecyclerView rvDisbursement;
    @BindView(R.id.layout_no_koneksi)
    FrameLayout noKoneksiLayout;
    @BindView(R.id.tv_refresh_connection)
    TextView tvRefresh;

    private Unbinder unbinder;

    private DisbursementAdapter adapter;
    private ArrayList<TabunganRiwayatModel> dataList;

    public static void start(Context context) {
        Intent starter = new Intent(context, DisbursementActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);

        TabunganUtils tabunganUtils = ViewModelProviders.of(this).get(TabunganUtils.class);
        tabunganUtils.getLiveDataRiwayat().observe(this, getRiwayatTabunganUser);
        tabunganUtils.getMutableLiveDataTabungan().observe(this, getTabunganUser);

        unbinder = ButterKnife.bind(this);

        dataList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);

        tabunganUtils.asyncTabungan(4, progressBar, linearLayoutDisbursement, noKoneksiLayout);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_button_back_black);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        adapter = new DisbursementAdapter(DisbursementActivity.this, dataList);

        rvDisbursement.setHasFixedSize(true);
        rvDisbursement.setLayoutManager(new LinearLayoutManager(this));
        rvDisbursement.setAdapter(adapter);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                linearLayoutDisbursement.setVisibility(View.VISIBLE);
                noKoneksiLayout.setVisibility(View.GONE);
                tabunganUtils.asyncTabungan(MainActivity.idUser, progressBar, linearLayoutDisbursement, noKoneksiLayout);
            }
        });
    }

    private Observer<ArrayList<TabunganRiwayatModel>> getRiwayatTabunganUser = new Observer<ArrayList<TabunganRiwayatModel>>() {
        @Override
        public void onChanged(ArrayList<TabunganRiwayatModel> riwayat) {
            if (!riwayat.isEmpty()){
                adapter.setData(riwayat);
                progressBar.setVisibility(View.GONE);
                tvKosong.setVisibility(View.GONE);
                rvDisbursement.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.GONE);
                tvKosong.setVisibility(View.VISIBLE);
                rvDisbursement.setVisibility(View.GONE);
            }
        }
    };

    private Observer<ArrayList<TabunganModel>> getTabunganUser = new Observer<ArrayList<TabunganModel>>() {
        @Override
        public void onChanged(ArrayList<TabunganModel> tabunganModel) {
            if (!tabunganModel.isEmpty()){
                double saldoUser = tabunganModel.get(0).getSaldo();
                tvPencairan.setText("Rp. "+getSaldoNumberFormat(saldoUser));
                progressBar.setVisibility(View.GONE);
            }else{
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private String getSaldoNumberFormat(double saldo){
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(saldo);
    }
}
