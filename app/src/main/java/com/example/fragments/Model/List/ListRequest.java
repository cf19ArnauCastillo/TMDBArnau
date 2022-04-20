package com.example.fragments.Model.List;

public class ListRequest {
    public String name;
    public String description;
    public String language;

    public ListRequest(String name, String description) {
        this.name = name;
        this.description = description;
        this.language = "es";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }
}