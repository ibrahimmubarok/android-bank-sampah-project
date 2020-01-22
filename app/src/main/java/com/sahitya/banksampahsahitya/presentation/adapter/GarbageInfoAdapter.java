package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.GarbageInfoModel;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GarbageInfoAdapter extends RecyclerView.Adapter<GarbageInfoAdapter.GarbageInfoViewHolder> {

    private Context context;
    private ArrayList<GarbageInfoModel> mData;

    public void setData(ArrayList<GarbageInfoModel> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public GarbageInfoAdapter(Context context, ArrayList<GarbageInfoModel> mData) {
        this.context = context;
        this.mData = mData;
    }

    private ArrayList<GarbageInfoModel> getmData() {
        return mData;
    }

    @NonNull
    @Override
    public GarbageInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_garbage_info, parent, false);
        return new GarbageInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GarbageInfoViewHolder holder, int position) {
        holder.tvNomorSampah.setText(String.valueOf(position+1));
        holder.tvNamaSampah.setText(mData.get(position).getNama());
        holder.tvHargaSampah.setText(convertCurrency(mData.get(position).getHargaPerKilo()));

    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    public class GarbageInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nomor_sampah)
        TextView tvNomorSampah;
        @BindView(R.id.tv_nama_sampah)
        TextView tvNamaSampah;
        @BindView(R.id.tv_harga_kilo_sampah)
        TextView tvHargaSampah;

        public GarbageInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public String convertCurrency(double harga){
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(harga);
    }
}
