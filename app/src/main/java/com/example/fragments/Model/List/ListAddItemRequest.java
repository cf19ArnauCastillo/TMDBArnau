package com.example.fragments.Model.List;

public class ListAddItemRequest {
    int media_id;

    public ListAddItemRequest(int media_id) {
        this.media_id = media_id;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }
}