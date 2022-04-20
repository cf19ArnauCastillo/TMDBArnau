package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Recyclers.SearchMovieRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    public View view;
    RecyclerView recyclerView;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        TextView txtSearch = view.findViewById(R.id.txtList);
        ImageButton btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.recyclerSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = txtSearch.getText().toString();
                Log.i("asd", query);
                if(!query.equals("")){

                    ApiCall apiCall = retrofit.create(ApiCall.class);
                    Call<searchFilmModel> call = apiCall.getData(API_KEY, query);

                    call.enqueue(new Callback<searchFilmModel>(){
                        @Override
                        public void onResponse(Call<searchFilmModel> call, Response<searchFilmModel> response) {
                            if(response.code()!=200){
                                Log.i("testApi", "checkConn");
                                return;
                            }else {
                                ArrayList<Film> arraySearch = new ArrayList<>();
                                arraySearch = response.body().getResults();
                                callRecycler(arraySearch);
                            }
                        }

                        @Override
                        public void onFailure(Call<searchFilmModel> call, Throwable t) {

                        }
                    });
                }
            }
        });

        return view;
    }

    public void callRecycler(ArrayList<Film> arraySearch){
        SearchMovieRecyclerViewAdapter adapter = new SearchMovieRecyclerViewAdapter(arraySearch, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
    }
}