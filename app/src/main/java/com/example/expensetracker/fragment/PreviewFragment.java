package com.example.expensetracker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.ExpenseDetailActivity;
import com.example.expensetracker.adapter.CategoryAdapter;
import com.example.expensetracker.adapter.ExpenseAdapter;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
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

public class PreviewFragment extends Fragment {

    private static final int REQUEST_CODE_USERNAME = 100;

    private List<Category> categoryList = new ArrayList<>();
    private List<String> spinnerCategories = new ArrayList<>();
    private List<Expense> expenseList = new ArrayList<>();
    private MainViewModel viewModel;
    private Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;
    private CategoryAdapter categoryAdapter;
    private ExpenseAdapter expenseAdapter;

    private EditText etxtFilter;
    private String selectedCategory;

    public static PreviewFragment newInstance(){
        return new PreviewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview,container,false);

        initAdapters();
        initFilter(view);
        initSpinner(view);
        initRecycler(view);

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
                spinnerCategories.clear();
                for(Category cat : categoryList)
                    spinnerCategories.add(cat.getName());
                arrayAdapter.clear();
                arrayAdapter.add("All");
                arrayAdapter.addAll(spinnerCategories);
            }});

        viewModel.getExpenseLiveData().observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                expenseList = new ArrayList<>(expenses);
                viewModel.sortExpenses(expenseList);
                expenseAdapter.setData(expenseList);
            }});
    }

    private void initFilter(View view){
        etxtFilter = view.findViewById(R.id.etext_filter_fragment_preview);
        etxtFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.filterExpenses(charSequence.toString(),selectedCategory);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void initSpinner(View view){
        spinner = view.findViewById(R.id.spinner_fragment_preview);
        arrayAdapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1);
        arrayAdapter.add("All");
        selectedCategory = "All";
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = spinner.getItemAtPosition(i).toString();
                viewModel.filterExpenses(etxtFilter.getText().toString(),selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_fragment_preview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(expenseAdapter);
    }

    private void initAdapters(){
        categoryAdapter = new CategoryAdapter(false);
        expenseAdapter = new ExpenseAdapter();
        expenseAdapter.setDetailsCallback(new ExpenseAdapter.ExpenseDetailsCallback() {
            @Override
            public void onBtnClick(Expense expense) {
                Intent intent = new Intent(getContext(),ExpenseDetailActivity.class);
                intent.putExtra("expenseKey",expense.getData());
                startActivityForResult(intent,REQUEST_CODE_USERNAME);
            }
        });
        expenseAdapter.setRemoveCallback(new ExpenseAdapter.ExpenseRemoveCallback() {
            @Override
            public void onBtnClick(Expense expense) {
                removeExpense(expense);
            }
        });
    }

    private void removeExpense(Expense expense){
        viewModel.removeExpense(expense);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CODE_USERNAME)
            return;

        if (resultCode == -1)
            remove(data);
    }
    private void remove(Intent intent){
        int id = Integer.parseInt(intent.getStringExtra("removeExpenseKey"));
        Expense expense = null;
        for(Expense exp: viewModel.getExpenseList())
            if(exp.getId()==id)
                expense = exp;
        removeExpense(expense);
    }
}
