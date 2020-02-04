package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.tabungan.TabunganRiwayatModel;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisbursementAdapter extends RecyclerView.Adapter<DisbursementAdapter.DisbursementViewHolder> {

    private Context context;
    private ArrayList<TabunganRiwayatModel> riwayatList;

    public DisbursementAdapter(Context context, ArrayList<TabunganRiwayatModel> riwayatList) {
        this.context = context;
        this.riwayatList = riwayatList;
    }

    public void setData(ArrayList<TabunganRiwayatModel> data){
        riwayatList.clear();
        riwayatList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DisbursementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disbursement, parent, false);
        return new DisbursementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisbursementViewHolder holder, int position) {
        holder.tvUang.setText("Rp "+convertCurrency(riwayatList.get(position).getPenarikan()));
        holder.tvTanggal.setText(riwayatList.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    public class DisbursementViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_uang_pencairan_item_disbursement)
        TextView tvUang;
        @BindView(R.id.tv_tanggal_pencairan_item_disbursement)
        TextView tvTanggal;

        public DisbursementViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public String convertCurrency(double harga){
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(harga);
    }
}
