package com.example.fragments.Recyclers;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fragments.Model.List.ListMovies;
import com.example.fragments.R;


public class MoviesOfListRecyclerViewAdapter extends RecyclerView.Adapter<MoviesOfListRecyclerViewAdapter.ViewHolder>{
    private ArrayList<ListMovies> arrayMovies;
    private Context context;

    public MoviesOfListRecyclerViewAdapter(ArrayList<ListMovies> arrayMovies, Context context) {
        this.arrayMovies = arrayMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesOfListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        MoviesOfListRecyclerViewAdapter.ViewHolder holder = new MoviesOfListRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesOfListRecyclerViewAdapter.ViewHolder holder, int i) {
        holder.listTitle.setText(arrayMovies.get(i).getTitle());
        holder.itemCount.setText(arrayMovies.get(i).getVote_count());
    }

    @Override
    public int getItemCount() {
        return arrayMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listTitle, itemCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.listTitle);
            itemCount = itemView.findViewById(R.id.itemCount);
        }
    }
}