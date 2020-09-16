package com.example.expensetracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensetracker.R;
import com.example.expensetracker.adapter.CategoryAdapter;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.view.custom.PercentageTextView;
import com.example.expensetracker.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StatsFragment extends Fragment {

    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    private PercentageTextView percentageTextView;

    public static StatsFragment newInstance(){
        return new StatsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats,container,false);

        percentageTextView = view.findViewById(R.id.percentview_fragment_stats);

        categoryAdapter = new CategoryAdapter(true);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_fragment_stats);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModel viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        percentageTextView.addCategoryList(viewModel.getCategoryList());
        viewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList = new ArrayList<>(categories);
                categoryAdapter.setData(categoryList);
                percentageTextView.setText(findSum());
            }
        });
    }

    private String findSum(){
        int sumInt = 0;
        for(Category cat: categoryList){
            sumInt+=cat.getPrice();
        }
        return Integer.toString(sumInt);
    }
}
