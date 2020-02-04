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
import butterknife.Unbinder;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    private Context context;
    private ArrayList<String> helpList;

    public OnHelpClickListener onHelpClickListener;

    public HelpAdapter(Context context, ArrayList<String> helpList) {
        this.context = context;
        this.helpList = helpList;
    }

    public OnHelpClickListener getOnHelpClickListener() {
        return onHelpClickListener;
    }

    public void setOnHelpClickListener(OnHelpClickListener onHelpClickListener) {
        this.onHelpClickListener = onHelpClickListener;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_help, parent, false);
        return new HelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        holder.tvHelp.setText(helpList.get(position));
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }

    public class HelpViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_help)
        TextView tvHelp;
        @BindView(R.id.container_help)
        LinearLayout container;

        private Unbinder unbinder;

        public HelpViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnHelpClickListener().onHelpClicked(view, getAdapterPosition());
                }
            });
        }
    }

    public interface OnHelpClickListener {
        void onHelpClicked(View view, int position);
    }
}
