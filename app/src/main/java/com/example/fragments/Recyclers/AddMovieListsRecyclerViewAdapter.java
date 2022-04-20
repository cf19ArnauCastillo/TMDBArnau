package com.example.fragments.Recyclers;


import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListDetails;
import com.example.fragments.Model.List.ListMovies;
import com.example.fragments.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovieListsRecyclerViewAdapter extends RecyclerView.Adapter<AddMovieListsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<List> arrayList;
    private ArrayList<ListMovies> arrayMovies;
    private Context context;
    private int media_id;
    RecyclerView recyclerView;

    public AddMovieListsRecyclerViewAdapter(ArrayList<List> arrN, Context c){
        this.arrayList = arrN;
        this.context = c;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.listTitle.setText(arrayList.get(i).getName());
        holder.itemCount.setText(String.valueOf(arrayList.get(i).getCount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View alertCustomdialog = inflater.inflate( R.layout.form_movie_to_list, null);

                TextView title = alertCustomdialog.findViewById(R.id.sectionTitle);
                title.setText("List of movies");

                //initialize alert builder.
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                //set our custom alert dialog to tha alertdialog builder
                alert.setView(alertCustomdialog);

                final AlertDialog dialog = alert.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                recyclerView = alertCustomdialog.findViewById(R.id.recyclerList);

                //#region ApiCall getLists
                ApiCall apiCall = retrofit.create(ApiCall.class);
                Log.i("id", "" + arrayList.get(i).getId());
                /*Call<ListDetails> call = apiCall.getMoviesOfList();*/
                Call<ListDetails> call = apiCall.getMoviesOfList(String.valueOf(arrayList.get(i).getId()), API_KEY);

                call.enqueue(new Callback<ListDetails>(){
                    @Override
                    public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {
                        if(response.code()!=200){
                            Log.i("ListFragment", "error");
                            return;
                        }else {
                            Log.i("ListFragment", "bien");
                            arrayMovies = response.body().getItems();
                            callRecyclerMovies(arrayMovies);
                            Log.i("ListFragment results", "length: " + arrayList.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<ListDetails> call, Throwable t) {
                        Log.i("MoviesListFragment", "error" + t.getMessage());
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listTitle;
        TextView itemCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.listTitle);
            itemCount= itemView.findViewById(R.id.itemCount);
        }
    }

    public void callRecyclerMovies(ArrayList<ListMovies> arrayMovies){
        MoviesOfListRecyclerViewAdapter adapter = new MoviesOfListRecyclerViewAdapter(arrayMovies, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}