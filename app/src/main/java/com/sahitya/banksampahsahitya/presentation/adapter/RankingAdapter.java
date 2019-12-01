package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.ranking.RankingModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    private Context context;
    private ArrayList<RankingModel> listRanking;

    public RankingAdapter(Context context, ArrayList<RankingModel> listRanking) {
        this.context = context;
        this.listRanking = listRanking;
    }

    public void setData(ArrayList<RankingModel> items){
        listRanking.clear();
        listRanking.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peringkat, parent, false);
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        holder.tvName.setText(listRanking.get(position).getName());
        holder.tvNilai.setText(String.valueOf(listRanking.get(position).getBerat()));
    }

    @Override
    public int getItemCount() {
        return listRanking.size();
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name_peringkat_ranking)
        TextView tvName;
        @BindView(R.id.tv_nilai_peringkat_ranking)
        TextView tvNilai;

        private Unbinder unbinder;
        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(itemView);
        }
    }
}
