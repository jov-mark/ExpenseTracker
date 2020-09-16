package com.example.expensetracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.expensetracker.R;
import com.example.expensetracker.adapter.CategoryAdapter;
import com.example.expensetracker.model.Category;
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

public class CategoryFragment extends Fragment {

    private MainViewModel viewModel;
    private EditText etxtCategory;
    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;

    public static CategoryFragment newInstance(){
        return new CategoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);

        etxtCategory = view.findViewById(R.id.etext_name_fragment_category);

        ImageButton btn = view.findViewById(R.id.btn_add_fragment_category);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = etxtCategory.getText().toString();
                if(verifyInput(input)){
                    Category category = new Category(input,0);
                    viewModel.addCategory(category);
                    toast(input+" category added");
                    etxtCategory.setText("");
                }
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_fragment_category);
        categoryAdapter = new CategoryAdapter(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList = new ArrayList<>(categories);
                categoryAdapter.setData(categoryList);
            }
        });
    }

    private boolean verifyInput(String input){
        boolean allGood = true;
        if(input.trim().equals("")) {
            toast("Name can't be null");
            allGood = false;
        } else {
            for (Category cat : viewModel.getCategoryList())
                if (cat.getName().toLowerCase().equals(input.toLowerCase())) {
                    toast("Category already exists");
                    allGood = false;
                }
        }
        return allGood;
    }

    private void toast(String mess){
        Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
    }

}
