package com.example.expensetracker.model;

import com.example.expensetracker.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {

    private int id;
    private String name;
    private String category;
    private int price;
    private Date date;
    private DateFormat dateFormat;
    private static final String URL = "https://picsum.photos/300/300/?random";

    public Expense(String name,String category,int price){
        this.id = Util.generateID();
        this.name = name;
        this.price = price;
        this.category = category;
        this.date = new Date();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyy");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate(){
        return dateFormat.format(date);
    }

    public String toString(){
        String data = this.name+"\n"+this.category+"\n"+this.price;
        return data;
    }

    public String getData(){
        String data = this.id+" "+this.name+" "+this.category+" "+this.price+" "+dateFormat.format(this.date)+" "+URL;
        return data;
    }
}
