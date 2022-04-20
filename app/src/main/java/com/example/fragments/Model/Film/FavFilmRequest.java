package com.example.fragments.Model.Film;

public class FavFilmRequest {
    public String media_type;
    public int media_id;
    public boolean favorite;

    public FavFilmRequest(int media_id, boolean favorite) {
        this.media_type = "movie";
        this.media_id = media_id;
        this.favorite = favorite;
    }

    public String getMedia_type() { return media_type; }

    public int getMedia_id() { return media_id; }

    public boolean isFavorite() { return favorite; }
}