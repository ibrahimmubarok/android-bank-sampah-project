package com.sahitya.banksampahsahitya.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.LaporanModel;
import com.sahitya.banksampahsahitya.presentation.adapter.LaporanAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaporanFragment extends Fragment {


    @BindView(R.id.layout_no_koneksi)
    FrameLayout noKoneksiLayout;
    @BindView(R.id.main_layout_laporan)
    LinearLayout linearLayoutLaporan;
    @BindView(R.id.rv_laporan_tabungan)
    RecyclerView rvLaporan;

    private Unbinder unbinder;

    public LaporanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laporan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        setListLaporan();

        linearLayoutLaporan.setVisibility(View.VISIBLE);
    }

    private void setListLaporan() {
        ArrayList<LaporanModel> laporanList = new ArrayList<>();

        laporanList.add(new LaporanModel("Minggu 1"));
        laporanList.add(new LaporanModel("Minggu 2"));
        laporanList.add(new LaporanModel("Minggu 3"));
        laporanList.add(new LaporanModel("Minggu 4"));
        laporanList.add(new LaporanModel("Total"));

        LaporanAdapter laporanAdapter = new LaporanAdapter(getContext(), laporanList);

        rvLaporan.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvLaporan.setAdapter(laporanAdapter);

        laporanAdapter.setOnLaporanClickListener(new LaporanAdapter.OnLaporanClickListener() {
            @Override
            public void onLaporanClicked(View view, int position) {
                switch (position){
                    case 0 :
                        Toast.makeText(getContext(), "Minggu 1", Toast.LENGTH_SHORT).show();
                        break;

                    case 1 :
                        Toast.makeText(getContext(), "Minggu 2", Toast.LENGTH_SHORT).show();
                        break;

                    case 2 :
                        Toast.makeText(getContext(), "Minggu 3", Toast.LENGTH_SHORT).show();
                        break;

                    case 3 :
                        Toast.makeText(getContext(), "Minggu 4", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
