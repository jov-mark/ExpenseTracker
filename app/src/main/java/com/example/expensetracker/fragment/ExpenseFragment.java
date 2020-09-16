package com.example.expensetracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.expensetracker.R;
import com.example.expensetracker.adapter.CategoryAdapter;
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

public class ExpenseFragment extends Fragment {

    private MainViewModel viewModel;
    private CategoryAdapter adapter;
    private ArrayAdapter<String> arrayAdapter;
    private List<Category> categoryList = new ArrayList<>();
    private List<String> spinnerCategories = new ArrayList<>();

    private Spinner spinner;

    private EditText etxtName;
    private EditText etxtPrice;

    public static ExpenseFragment newInstance(){
        return new ExpenseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense,container,false);

        etxtName = view.findViewById(R.id.etext_name_fragment_expense);
        etxtPrice = view.findViewById(R.id.etext_price_fragment_expense);

        spinner = view.findViewById(R.id.spinner_fragment_expense);
        arrayAdapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1);
        spinner.setAdapter(arrayAdapter);

        initButton(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new CategoryAdapter(false);

        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList = new ArrayList<>(categories);
                adapter.setData(categoryList);
                spinnerCategories.clear();
                for(Category cat : categoryList)
                    spinnerCategories.add(cat.getName());
                arrayAdapter.clear();
                arrayAdapter.addAll(spinnerCategories);
            }
        });
    }

    private void initButton(View view){
        ImageButton btnAdd = view.findViewById(R.id.btn_add_fragment_expense);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()) {
                    Expense expense = new Expense(etxtName.getText().toString(), spinner.getSelectedItem().toString(), Integer.parseInt(etxtPrice.getText().toString()));
                    viewModel.addExpense(expense);
                    Toast.makeText(getContext(), "Expense added - "+expense.getPrice()+" rsd", Toast.LENGTH_SHORT).show();
                    etxtName.setText("");
                    etxtPrice.setText("");
                } else{
                    Toast.makeText(getContext(), "Incorrect data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(){
        return !(etxtName.getText().toString().isEmpty() || etxtPrice.getText().toString().isEmpty() || spinner.getSelectedItem()==null);
    }

}
