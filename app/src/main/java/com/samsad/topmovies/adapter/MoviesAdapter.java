package com.samsad.topmovies.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.samsad.topmovies.R;
import com.samsad.topmovies.databinding.ListItemMovieBinding;
import com.samsad.topmovies.models.Movie;

import java.util.ArrayList;

public class MoviesAdapter  extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private ArrayList<Movie> movieList;
    private LayoutInflater inflater;
    private Context context;

    public MoviesAdapter(ArrayList<Movie> movies, Context context) {
        this.movieList = movies;
        this.context =context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ListItemMovieBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.list_item_movie, viewGroup, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        holder.binding.setMovie(movie);
        holder.binding.topLlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,movie.getTitle(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemMovieBinding binding;

        public ViewHolder(final ListItemMovieBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
