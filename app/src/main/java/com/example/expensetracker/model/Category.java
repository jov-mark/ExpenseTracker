package com.example.expensetracker.model;

public class Category {

    private String name;
    private int sumPrice;

    public Category(String name,int price){
        this.name = name;
        this.sumPrice = price;
    }

    public void updatePrice(int price,boolean bool){
        if(bool)
            this.sumPrice += price;
        else
            this.sumPrice -= price;
    }
    public String getName() {
        return name;
    }

    public String getSumPrice() {
        return Integer.toString(sumPrice);
    }

    public int getPrice(){
        return this.sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }
}
