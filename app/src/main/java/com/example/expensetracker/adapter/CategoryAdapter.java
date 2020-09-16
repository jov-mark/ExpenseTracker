package com.example.expensetracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensetracker.R;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private List<Category> categoryList;
    private boolean stats;

    public CategoryAdapter(boolean stats){
        this.categoryList = new ArrayList<>();
        this.stats = stats;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tViewData.setText(category.getName());
        if(stats)
            holder.tViewPrice.setText(category.getSumPrice());
    }


    public void setData(List<Category> categories){
        this.categoryList.clear();
        this.categoryList.addAll(categories);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{

        TextView tViewData;
        TextView tViewPrice;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            tViewData = itemView.findViewById(R.id.textview_name_listitem_category);
            tViewPrice = itemView.findViewById(R.id.textview_price_listitem_category);
        }
    }
}
