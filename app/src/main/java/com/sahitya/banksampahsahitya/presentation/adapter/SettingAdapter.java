package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

    private Context context;
    private ArrayList<String> settingList;

    public OnSettingClickListener onSettingClickListener;

    public SettingAdapter(Context context, ArrayList<String> settingList) {
        this.context = context;
        this.settingList = settingList;
    }

    public OnSettingClickListener getOnSettingClickListener() {
        return onSettingClickListener;
    }

    public void setOnSettingClickListener(OnSettingClickListener onSettingClickListener) {
        this.onSettingClickListener = onSettingClickListener;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_setting, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        holder.tvSetting.setText(settingList.get(position));
    }

    @Override
    public int getItemCount() {
        return settingList.size();
    }

    public class SettingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_setting)
        TextView tvSetting;
        @BindView(R.id.container_setting)
        LinearLayout container;

        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnSettingClickListener().onSettingClicked(view, getAdapterPosition());
                }
            });
        }
    }

    public interface OnSettingClickListener{
        void onSettingClicked(View view, int position);
    }
}
