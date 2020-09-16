package com.example.expensetracker.util;

import com.example.expensetracker.model.Expense;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class ExpenseDiffCallback extends DiffUtil.Callback {
    private List<Expense> oldExpenseList;
    private List<Expense> newExpenseList;

    public ExpenseDiffCallback(List<Expense> oldExpenseList, List<Expense> newExpenseList){
        this.oldExpenseList = oldExpenseList;
        this.newExpenseList = newExpenseList;
    }

    @Override
    public int getOldListSize() {
        return oldExpenseList.size();
    }

    @Override
    public int getNewListSize() {
        return newExpenseList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Expense oldExpense = oldExpenseList.get(oldItemPosition);
        Expense newExpense = newExpenseList.get(newItemPosition);
        return oldExpense.getId() == newExpense.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Expense oldExpense = oldExpenseList.get(oldItemPosition);
        Expense newExpense = newExpenseList.get(newItemPosition);
        return oldExpense.getName().equals(newExpense.getName());
    }
}
