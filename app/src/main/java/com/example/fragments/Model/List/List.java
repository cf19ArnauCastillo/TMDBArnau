package com.example.fragments.Model.List;

public class List {
    public String name;
    public int id;
    public int item_count;


    public List(String name, int item_count) {
        this.name = name;
        this.item_count = item_count;
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return item_count;
    }
}