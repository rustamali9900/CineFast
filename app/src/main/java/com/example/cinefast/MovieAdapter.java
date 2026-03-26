package com.example.cinefast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private OnMovieClickListener listener;

    // 1. This interface lets the Adapter talk to the Fragment when a button is clicked
    public interface OnMovieClickListener {
        void onBookSeatsClick(Movie movie);
    }

    // 2. Constructor: The Adapter receives the list of movies and the click listener
    public MovieAdapter(List<Movie> movieList, OnMovieClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 3. This grabs your item_movie.xml template!
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // 4. This gets the specific movie for this row
        Movie movie = movieList.get(position);

        // 5. Fill the template with the actual movie data
        holder.title.setText(movie.getTitle());
        holder.details.setText(movie.getDetails());
        holder.poster.setImageResource(movie.getImageResId());

        // 6. Trailer Logic (Opens YouTube just like Assignment 1)
        holder.btnTrailer.setOnClickListener(v -> {
            Context context = v.getContext();
            String url = "https://www.youtube.com/results?search_query=" + movie.getTrailerQuery();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        });

        // 7. Book Seats Logic (Passes the movie back to the Fragment so we can navigate)
        holder.btnBookSeats.setOnClickListener(v -> {
            listener.onBookSeatsClick(movie);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // 8. The ViewHolder finds and holds the Views from item_movie.xml so we don't have to keep searching for them
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView poster;
        TextView title, details;
        MaterialButton btnBookSeats, btnTrailer;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
            details = itemView.findViewById(R.id.movie_details);
            btnBookSeats = itemView.findViewById(R.id.btn_book_seats);
            btnTrailer = itemView.findViewById(R.id.btn_trailer);
        }
    }
}