package com.example.fragments.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultConstants {

    public static final String API_KEY = "aa097aa721a927fce689e12fed1d47c3";
    //AQUI TIENES QUE SEGUIR
    public static final String SESSION_ID = "fa97e30674cf87b33afc172a2884fc1f37700f1b";
    public static final String ACCOUNT_ID = "ArnauCastillo";

    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500/";

    public static final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.themoviedb.org/3/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

}
