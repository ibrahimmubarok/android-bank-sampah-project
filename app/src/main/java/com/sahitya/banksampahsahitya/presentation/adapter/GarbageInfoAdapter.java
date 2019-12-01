package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.model.GarbageInfoModel;

import java.util.ArrayList;

public class GarbageInfoAdapter extends RecyclerView.Adapter<GarbageInfoAdapter.GarbageInfoViewHolder> {

    private Context context;
    private ArrayList<GarbageInfoModel> garbageList;

    public void setData(ArrayList<GarbageInfoModel> items){
        garbageList.clear();
        garbageList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GarbageInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GarbageInfoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GarbageInfoViewHolder extends RecyclerView.ViewHolder {
        public GarbageInfoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
