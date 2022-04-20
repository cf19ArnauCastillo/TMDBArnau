package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.ACCOUNT_ID;
import static com.example.fragments.Config.DefaultConstants.API_KEY;
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
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListDetails;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListRequest;
import com.example.fragments.Model.List.ListResponse;
import com.example.fragments.Recyclers.AddMovieListsRecyclerViewAdapter;
import com.example.fragments.Recyclers.SearchMovieRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {
    TextView txtList, txtDescription;
    public View view;
    RecyclerView recyclerView;
    ArrayList<List> arrayList = new ArrayList<>();

    public ListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerLists);

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
                    ArrayList<List> arrayList = new ArrayList<>();
                    arrayList = response.body().getResults();
                    callRecycler(arrayList);
                }
            }

            @Override
            public void onFailure(Call<ListModel> call, Throwable t) {
                Log.i("MoviesListFragment", "error");
            }
        });

        FloatingActionButton btnAdd = view.findViewById(R.id.btnAddList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_add_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        txtDescription = alertCustomdialog.findViewById(R.id.txtDescription);
        txtList = alertCustomdialog.findViewById(R.id.txtList);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Button btnSaveList = alertCustomdialog.findViewById(R.id.btnSaveList);

        btnSaveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String list = txtList.getText().toString();
                String description = txtList.getText().toString();
                createList(list, description);
                dialog.dismiss();
            }
        });
    }




    public void createList(String name, String description){
        ListRequest request = new ListRequest(name, description);
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<ListResponse> call = apiCall.postList(API_KEY, SESSION_ID, request);

        call.enqueue(new Callback<ListResponse>(){
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                if(response.code()!=201){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {
                    Log.i("crear llista", "añadido correctamente");
                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.i("crear llista", "error");
            }
        });
    }

    public void callRecycler(ArrayList<List> arrayList){
        AddMovieListsRecyclerViewAdapter adapter = new AddMovieListsRecyclerViewAdapter(arrayList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}