package com.sahitya.banksampahsahitya.ui;


import android.app.ProgressDialog;
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

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.GarbageInfoModel;
import com.sahitya.banksampahsahitya.presentation.adapter.GarbageInfoAdapter;
import com.sahitya.banksampahsahitya.utils.GarbageInfoApiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoSampahFragment extends Fragment {

    private static final String TAG = InfoSampahFragment.class.getSimpleName();

    @BindView(R.id.main_layout_garbage_info)
    LinearLayout linearLayoutInfoSampah;
    @BindView(R.id.recycler_view_garbage_info)
    RecyclerView rvGarbageInfo;
    @BindView(R.id.layout_no_koneksi)
    FrameLayout noKoneksiLayout;
    @BindView(R.id.tv_refresh_connection)
    TextView tvRefresh;
    @BindView(R.id.progress_bar_garbage_info)
    ProgressBar progressBar;

    private Unbinder unbinder;

    private ArrayList<GarbageInfoModel> garbageInfoList;
    private GarbageInfoAdapter adapter;

    public InfoSampahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garbage_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GarbageInfoApiUtils garbageInfoApiUtils = ViewModelProviders.of(this).get(GarbageInfoApiUtils.class);
        garbageInfoApiUtils.getLiveDataGarbageInfo().observe(this, getGarbageInfo);

        unbinder = ButterKnife.bind(this, view);

        garbageInfoList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);

        adapter = new GarbageInfoAdapter(this.getContext(), garbageInfoList);
        adapter.notifyDataSetChanged();

        rvGarbageInfo.setHasFixedSize(true);
        rvGarbageInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGarbageInfo.setAdapter(adapter);

        garbageInfoApiUtils.asyncGarbageInfoData(progressBar, linearLayoutInfoSampah , noKoneksiLayout);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                noKoneksiLayout.setVisibility(View.GONE);
                garbageInfoApiUtils.asyncGarbageInfoData(progressBar, linearLayoutInfoSampah , noKoneksiLayout);
            }
        });
    }

    private Observer<ArrayList<GarbageInfoModel>> getGarbageInfo = new Observer<ArrayList<GarbageInfoModel>>() {
        @Override
        public void onChanged(ArrayList<GarbageInfoModel> riwayat) {
            if (!riwayat.isEmpty()){
                adapter.setData(riwayat);
                Log.d("Masuk Pak Eko", riwayat.get(0).getNama());
                progressBar.setVisibility(View.GONE);
            }else{
                Log.d("Gaada Data Pak Eko", "0");
                progressBar.setVisibility(View.GONE);
            }
        }
    };
}