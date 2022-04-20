package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.ACCOUNT_ID;
import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.BASE_IMG_URL;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Config.GlideApp;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Recyclers.AddMovieListsRecyclerViewAdapter;
import com.example.fragments.Recyclers.SearchMovieRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {
    boolean isFavoriteMovie = false;
    ArrayList<List> arrayList = new ArrayList<List>();
    RecyclerView recyclerView;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getArguments();
        Film film = (Film) bundle.getSerializable("Film");

        TextView txtDetailTitle = view.findViewById(R.id.txtDetailTitle);
        TextView txtDetailDesc = view.findViewById(R.id.txtDetailDesc);
        ImageView imgDetail = view.findViewById(R.id.imgDetail);
        ImageButton btnFav = view.findViewById(R.id.btnFav);
        ImageButton btnAddtoList = view.findViewById(R.id.btnAddtoList);

        txtDetailTitle.setText(film.getOriginal_title());
        txtDetailDesc.setText(film.getOverview());

        // Setea si es favorito
        //#region ApiCalll GetFavorites
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<searchFilmModel> call = apiCall.getFavorites(API_KEY, SESSION_ID);

        call.enqueue(new Callback<searchFilmModel>(){
            @Override
            public void onResponse(Call<searchFilmModel> call, Response<searchFilmModel> response) {
                if(response.code()!=200){
                    Log.i("MoviesListFragment", "error");
                    return;
                } else {
                    Log.i("MoviesListFragment", "bien");
                    ArrayList<Film> arraySearch = new ArrayList<>();
                    arraySearch = response.body().getResults();
                    if(arraySearch.size() != 0) {
                        for (Film f : arraySearch) {
                            if (film.getId() == f.getId()) {
                                btnFav.setImageResource(R.drawable.ic_fav_on);
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<searchFilmModel> call, Throwable t) {
                Log.i("MoviesListFragment", "error");
            }
        });
        //#endregion ApiCalll GetFavorites

        GlideApp.with(getContext())
                .load(BASE_IMG_URL + film.getPoster_path())
                .centerCrop()
                .into(imgDetail);

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFav.setImageResource(R.drawable.ic_fav_on);
                FavFilmRequest request = new FavFilmRequest(film.getId(), true);
                ApiCall apiCall = retrofit.create(ApiCall.class);
                Call<FavFilmResponse> call = apiCall.setFavorite(ACCOUNT_ID, API_KEY, SESSION_ID, request);

                call.enqueue(new Callback<FavFilmResponse>(){
                    @Override
                    public void onResponse(Call<FavFilmResponse> call, Response<FavFilmResponse> response) {
                        if(response.code()!=201){
                            Log.i("testApi", "checkConnection");
                            return;
                        }else {
                            Log.i("DetailFragment", "añadido correctamente");
                        }
                    }

                    @Override
                    public void onFailure(Call<FavFilmResponse> call, Throwable t) {
                        Log.i("DetailFragment", "error");
                    }
                });
            }
        });

        btnAddtoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;

    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_movie_to_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        recyclerView = alertCustomdialog.findViewById(R.id.recyclerList);

        //#region ApiCall getLists
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<ListModel> call = apiCall.getLists(API_KEY, SESSION_ID);

        call.enqueue(new Callback<ListModel>(){
            @Override
            public void onResponse(Call<ListModel> call, Response<ListModel> response) {
                if(response.code()!=200){
                    Log.i("ListFragment", "error");
                    return;
                }else {
                    Log.i("ListFragment", "bien");
                    arrayList = response.body().getResults();
                    callRecycler(arrayList);
                    Log.i("ListFragment results", "length: " + arrayList.size());
                }
            }

            @Override
            public void onFailure(Call<ListModel> call, Throwable t) {
                Log.i("MoviesListFragment", "error");
            }
        });
        //#endregion ApiCall getLists

    }
    public void callRecycler(ArrayList<List> arrayList){
        AddMovieListsRecyclerViewAdapter adapter = new AddMovieListsRecyclerViewAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}