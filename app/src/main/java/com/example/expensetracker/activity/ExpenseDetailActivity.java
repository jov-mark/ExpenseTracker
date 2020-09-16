package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensetracker.R;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.viewmodel.MainViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExpenseDetailActivity extends AppCompatActivity {

    private Expense expense;
    private MainViewModel viewModel;
    private String data;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);

        data = getIntent().getStringExtra("expenseKey");
        String[] split = data.split(" ");

        ((TextView)findViewById(R.id.textview_data_details)).setText(parseData(split));
        ImageView imgView = findViewById(R.id.image_details);

        Picasso.get().load(split[5]).into(imgView);

        findViewById(R.id.btn_remove_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("removeExpenseKey", split[0]);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public String parseData(String[] split){
        data = split[1]+ "\n";
        data += "Category: "+split[2]+"\n";
        data += "Paid: "+split[3]+"\n";
        data += "Date: "+split[4];
        return data;
    }
}
