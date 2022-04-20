package com.example.fragments.Model.List;

import com.example.fragments.Model.Film.Film;

import java.util.ArrayList;

public class ListModel {
    public int page;
    public ArrayList<List> results;

    public int getPage() {
        return page;
    }

    public ArrayList<List> getResults() {
        return results;
    }
}