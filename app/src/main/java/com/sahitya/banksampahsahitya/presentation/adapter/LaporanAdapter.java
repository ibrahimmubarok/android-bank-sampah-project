package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.LaporanModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder> {

    private Context context;
    private ArrayList<LaporanModel> laporanList;

    private OnLaporanClickListener onLaporanClickListener;

    public LaporanAdapter(Context context, ArrayList<LaporanModel> laporanList) {
        this.context = context;
        this.laporanList = laporanList;
    }

    public OnLaporanClickListener getOnLaporanClickListener() {
        return onLaporanClickListener;
    }

    public void setOnLaporanClickListener(OnLaporanClickListener onLaporanClickListener) {
        this.onLaporanClickListener = onLaporanClickListener;
    }

    @NonNull
    @Override
    public LaporanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_tabungan, parent, false);
        return new LaporanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanViewHolder holder, int position) {
        holder.tvWeek.setText(laporanList.get(position).getMinggu());
    }

    @Override
    public int getItemCount() {
        return laporanList.size();
    }

    public class LaporanViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_week_laporan)
        TextView tvWeek;
        @BindView(R.id.tv_berat_laporan)
        TextView tvBerat;
        @BindView(R.id.tv_total_uang_laporan)
        TextView tvUang;
        @BindView(R.id.container_laporan)
        RelativeLayout container;

        private Unbinder unbinder;

        public LaporanViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnLaporanClickListener().onLaporanClicked(view, getAdapterPosition());
                }
            });
        }
    }

    public interface OnLaporanClickListener{
        void onLaporanClicked(View view, int position);
    }
}
