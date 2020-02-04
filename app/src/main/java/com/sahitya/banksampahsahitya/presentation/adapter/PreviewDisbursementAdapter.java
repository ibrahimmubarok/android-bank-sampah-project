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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PreviewDisbursementAdapter extends RecyclerView.Adapter<PreviewDisbursementAdapter.PreviewDisbursementViewHolder> {

    private Context context;
    private ArrayList<TabunganRiwayatModel> riwayatList;

    public PreviewDisbursementAdapter(Context context, ArrayList<TabunganRiwayatModel> riwayatList) {
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
    public PreviewDisbursementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview_riwayat_transaksi, parent, false);
        return new PreviewDisbursementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewDisbursementViewHolder holder, int position) {
        holder.tvCreated.setText(convertDate(riwayatList.get(position).getCreatedAt()));
        holder.tvSaldo.setText(convertCurrency(riwayatList.get(position).getPenarikan()));
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    public class PreviewDisbursementViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_created_at_riwayat_transaksi)
        TextView tvCreated;
        @BindView(R.id.tv_riwayat_transaksi)
        TextView tvSaldo;

        private Unbinder unbinder;

        public PreviewDisbursementViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);

        }
    }

    public String convertCurrency(double harga){
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(harga);
    }

    public String convertDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm - dd-MMM-yyyy");
        Date d = null;
        try {
            d = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern("dd-MMM-yyyy");
        return sdf.format(d);
    }
}
