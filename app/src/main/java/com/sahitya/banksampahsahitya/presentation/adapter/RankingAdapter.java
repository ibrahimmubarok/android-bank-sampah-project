package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.ranking.RankingModel;

import java.text.NumberFormat;
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
        holder.tvNomor.setText(String.valueOf(position+1));
        if (position == 0){
            holder.tvName.setText(listRanking.get(position).getName());
            holder.tvNilai.setText(convertCurrency(listRanking.get(position).getBerat()));
            holder.relativeLayout.setBackgroundResource(R.drawable.bg_ranking_1);
            holder.linearLayout.setBackgroundResource(R.drawable.bg_shadow_ranking_1);
        }
        if (position == 1) {
            holder.tvName.setText(listRanking.get(position).getName());
            holder.tvNilai.setText(convertCurrency(listRanking.get(position).getBerat()));
            holder.relativeLayout.setBackgroundResource(R.drawable.bg_ranking_2);
            holder.linearLayout.setBackgroundResource(R.drawable.bg_shadow_ranking_2);
        }else if (position == 2){
            holder.tvName.setText(listRanking.get(position).getName());
            holder.tvNilai.setText(convertCurrency(listRanking.get(position).getBerat()));
            holder.relativeLayout.setBackgroundResource(R.drawable.bg_ranking_3);
            holder.linearLayout.setBackgroundResource(R.drawable.bg_shadow_ranking_3);
        }
    }

    @Override
    public int getItemCount() {
        return listRanking.size();
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nomor_ranking)
        TextView tvNomor;
        @BindView(R.id.tv_name_peringkat_ranking)
        TextView tvName;
        @BindView(R.id.tv_nilai_peringkat_ranking)
        TextView tvNilai;
        @BindView(R.id.relative_layout)
        RelativeLayout relativeLayout;
        @BindView(R.id.linear_layout_shadow)
        LinearLayout linearLayout;

        private Unbinder unbinder;
        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public String convertCurrency(double harga){
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(harga);
    }
}
