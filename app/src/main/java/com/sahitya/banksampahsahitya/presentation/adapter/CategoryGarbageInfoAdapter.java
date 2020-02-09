package com.sahitya.banksampahsahitya.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahitya.banksampahsahitya.R;
;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryGarbageInfoAdapter extends RecyclerView.Adapter<CategoryGarbageInfoAdapter.CategoryGarbageInfoViewHolder>{

    private List<String> categoryList;
    private Context context;

    private int selectedItems = -1;

    private OnCategoryGarbageInfoClickListener categoryGarbageInfoClickListener;

    public CategoryGarbageInfoAdapter(Context context, List<String> categoryList) {
        this.categoryList = categoryList;
        this.context = context;
    }

    public void setData(List<String> list){
        this.categoryList = list;
        notifyDataSetChanged();
    }

    public OnCategoryGarbageInfoClickListener getCategoryGarbageInfoClickListener() {
        return categoryGarbageInfoClickListener;
    }

    public void setCategoryGarbageInfoClickListener(OnCategoryGarbageInfoClickListener categoryGarbageInfoClickListener) {
        this.categoryGarbageInfoClickListener = categoryGarbageInfoClickListener;
    }

    @NonNull
    @Override
    public CategoryGarbageInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_garbage_info, parent, false);
        return new CategoryGarbageInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryGarbageInfoViewHolder holder, int position) {
        holder.tvCategory.setText(categoryList.get(position));
        holder.tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItems = position;
                notifyDataSetChanged();
                getCategoryGarbageInfoClickListener().onCategoryClicked(view, position ,categoryList.get(position));
            }
        });
        if (selectedItems == position){
            holder.tvCategory.setBackgroundResource(R.drawable.bg_category_style_clicked);
        }else{
            holder.tvCategory.setBackgroundResource(R.drawable.bg_category_style);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryGarbageInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_category_item_garbage_info)
        TextView tvCategory;

        public CategoryGarbageInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCategoryGarbageInfoClickListener{
        void onCategoryClicked(View view, int position, String category);
    }
}
