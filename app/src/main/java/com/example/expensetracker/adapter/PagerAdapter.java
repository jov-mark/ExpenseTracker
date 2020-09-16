package com.example.expensetracker.adapter;

import android.content.Context;

import com.example.expensetracker.fragment.CategoryFragment;
import com.example.expensetracker.fragment.ExpenseFragment;
import com.example.expensetracker.fragment.PreviewFragment;
import com.example.expensetracker.fragment.StatsFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private List<String> tabTitles;

    public PagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);

        this.tabTitles = new ArrayList<>();
        this.tabTitles.add("Expenses");
        this.tabTitles.add("Preview");
        this.tabTitles.add("Stats");
        this.tabTitles.add("Category");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ExpenseFragment.newInstance();
            case 1:
                return PreviewFragment.newInstance();
            case 2:
                return StatsFragment.newInstance();
            case 3:
                return CategoryFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
