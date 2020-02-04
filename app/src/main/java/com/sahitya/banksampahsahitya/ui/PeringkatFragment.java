package com.sahitya.banksampahsahitya.ui;


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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.presentation.adapter.RankingAdapter;
import com.sahitya.banksampahsahitya.model.ranking.RankingModel;
import com.sahitya.banksampahsahitya.utils.RankingUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeringkatFragment extends Fragment {

    private final String TAG = PeringkatFragment.class.getSimpleName();

    @BindView(R.id.rv_ranking)
    RecyclerView rvRanking;
    @BindView(R.id.tv_name_random_ranking)
    TextView tvNamaRandom;
    @BindView(R.id.tv_nilai_random_ranking)
    TextView tvNilaiRandom;
    @BindView(R.id.tv_periode_ranking)
    TextView tvPeriode;
    @BindView(R.id.layout_no_koneksi)
    FrameLayout noKoneksiLayout;
    @BindView(R.id.layout_ranking_unavailable)
    FrameLayout rankingUnavailble;
    @BindView(R.id.main_layout_peringkat)
    RelativeLayout relativeLayout;
    @BindView(R.id.progress_bar_ranking)
    ProgressBar progressBar;
    @BindView(R.id.tv_refresh_connection)
    TextView tvRefresh;

    private Unbinder unbinder;

    private ArrayList<RankingModel> rankingArrayList;
    private RankingAdapter adapter;

    public PeringkatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peringkat, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RankingUtils rankingModel = ViewModelProviders.of(this).get(RankingUtils.class);
        rankingModel.getLiveDataRanking().observe(this, getRankingData);

        rankingArrayList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);

        adapter = new RankingAdapter(getContext(), rankingArrayList);
        adapter.notifyDataSetChanged();

        rvRanking.setHasFixedSize(true);
        rvRanking.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRanking.setAdapter(adapter);

        rankingModel.asyncRanking(progressBar, relativeLayout, noKoneksiLayout);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                noKoneksiLayout.setVisibility(View.GONE);
                rankingModel.asyncRanking(progressBar, relativeLayout, noKoneksiLayout);
            }
        });
    }

    private Observer<ArrayList<RankingModel>> getRankingData = new Observer<ArrayList<RankingModel>>() {
        @Override
        public void onChanged(ArrayList<RankingModel> rankingModels) {
            if (!rankingModels.isEmpty()){
                adapter.setData(rankingModels);
                Log.d(TAG, "Ada Data");
                Log.d(TAG, String.valueOf(rankingModels.size()));
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.GONE);
                rankingUnavailble.setVisibility(View.VISIBLE);
                Log.d("Jancuk", String.valueOf(rankingModels.size()));
            }
        }
    };
}
