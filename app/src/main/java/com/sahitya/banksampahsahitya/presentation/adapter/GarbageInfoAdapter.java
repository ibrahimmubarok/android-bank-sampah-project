package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.GarbageInfoModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GarbageInfoAdapter extends RecyclerView.Adapter<GarbageInfoAdapter.GarbageInfoViewHolder> implements Filterable {

    private Context context;
    private List<GarbageInfoModel> mData;
    private List<GarbageInfoModel> mDataFilter;

    private String nameCategory = "Semua";

    public void setData(List<GarbageInfoModel> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void setNameCategory(String name){
        this.nameCategory = name;
    }

    public void setCategoryGarbage(String category){
        List<GarbageInfoModel> categoryList = new ArrayList<>();
        for (GarbageInfoModel row : mData){
            if (row.getKategori().contains(category)){
                categoryList.add(row);
            }
        }
        mDataFilter = categoryList;
        notifyDataSetChanged();
    }

    public void setAllCategoryGarbage(){
        mDataFilter = mData;
        notifyDataSetChanged();
    }

    public GarbageInfoAdapter(Context context, List<GarbageInfoModel> mData) {
        this.context = context;
        this.mData = mData;
        this.mDataFilter = mData;
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
        holder.tvNamaSampah.setText(mDataFilter.get(position).getNama());
        holder.tvHargaSampah.setText("Rp "+convertCurrency(mDataFilter.get(position).getHargaPerKilo()));
    }

    @Override
    public int getItemCount() {
        return mDataFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()){
                    if (nameCategory.equals("Semua")) {
                        mDataFilter = mData;
                    }else{
                        List<GarbageInfoModel> filteredList = new ArrayList<>();
                        for (GarbageInfoModel row : mData){
                            if (row.getKategori().contains(nameCategory)) {
                                filteredList.add(row);
                            }
                        }
                        mDataFilter = filteredList;
                    }
                }else{
                    List<GarbageInfoModel> filteredList = new ArrayList<>();
                    for (GarbageInfoModel row : mData){
                        String harga = String.valueOf(row.getHargaPerKilo());
                        if (nameCategory.equals("Semua")){
                            if (row.getNama().toLowerCase().contains(charString.toLowerCase()) || harga.contains(charSequence)){
                                filteredList.add(row);
                            }
                        }else {
                            if ((row.getNama().toLowerCase().contains(charString.toLowerCase()) || harga.contains(charSequence)) && row.getKategori().contains(nameCategory)) {
                                filteredList.add(row);
                            }
                        }
                    }
                    mDataFilter = filteredList;
                    System.out.println(nameCategory);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataFilter = (ArrayList<GarbageInfoModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
