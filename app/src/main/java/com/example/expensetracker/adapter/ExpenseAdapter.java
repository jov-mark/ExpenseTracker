package com.example.expensetracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.expensetracker.R;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.util.ExpenseDiffCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>{

    private List<Expense> expenseList;
    private ExpenseDetailsCallback detailsCallback;
    private ExpenseRemoveCallback removeCallback;

    public ExpenseAdapter(){
        this.expenseList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_expense,parent,false);
        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.tViewData.setText(expense.toString());
        holder.tViewDate.setText(expense.getDate());
        holder.bind(expenseList.get(position));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public void setData(List<Expense> expenses){
        ExpenseDiffCallback callback = new ExpenseDiffCallback(expenseList, expenses);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        this.expenseList.clear();
        this.expenseList.addAll(expenses);
        result.dispatchUpdatesTo(this);
    }

    public class ExpenseHolder extends RecyclerView.ViewHolder{

        TextView tViewData;
        TextView tViewDate;
        ImageButton btnRemove;
        ImageButton btnDetails;
        Expense expense;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            tViewData = itemView.findViewById(R.id.textview_data_listitem_expense);
            tViewDate = itemView.findViewById(R.id.textview_date_listitem_expense);

            btnRemove = itemView.findViewById(R.id.btn_remove_listitem_expense);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Expense exp = expenseList.get(position);
                    if(position!=RecyclerView.NO_POSITION){
                        if(removeCallback != null)
                            removeCallback.onBtnClick(exp);
                    }
                }
            });

            btnDetails = itemView.findViewById(R.id.btn_details_listitem_expense);
            btnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Expense exp = expenseList.get(position);
                    if(position!=RecyclerView.NO_POSITION){
                        if(detailsCallback != null)
                            detailsCallback.onBtnClick(exp);
                    }
                }
            });
        }
        public void bind(Expense expense){
            this.expense = expense;
        }
    }

    public interface ExpenseDetailsCallback{
        void onBtnClick(Expense expense);
    }

    public interface ExpenseRemoveCallback{
        void onBtnClick(Expense expense);
    }


    public void setDetailsCallback(ExpenseDetailsCallback detailsCallback) {
        this.detailsCallback = detailsCallback;
    }

    public void setRemoveCallback(ExpenseRemoveCallback removeCallback) {
        this.removeCallback = removeCallback;
    }
}
