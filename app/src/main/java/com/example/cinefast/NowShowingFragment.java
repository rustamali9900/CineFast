package com.example.cinefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NowShowingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_showing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_nowShowing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Dark Knight Rises", "Action | 2h 44m", R.drawable.darknight, "Dark Knight Rises trailer"));
        movieList.add(new Movie("Inception", "Sci-Fi | 2h 28m", R.drawable.inception, "Inception trailer"));
        movieList.add(new Movie("Interstellar", "Sci-Fi | 2h 49m", R.drawable.interstellar, "Interstellar trailer"));

        MovieAdapter adapter = new MovieAdapter(movieList, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onBookSeatsClick(Movie movie) {
                Bundle bundle = new Bundle();
                bundle.putString("movieTitle", movie.getTitle());
                bundle.putInt("movieImage", movie.getImageResId());
                bundle.putString("movieTrailer", movie.getTrailerQuery());
                bundle.putString("category", "Now Showing"); // Crucial for the Seat Screen logic

                SeatSelectionFragment fragment = new SeatSelectionFragment();
                fragment.setArguments(bundle);

                // Swap the screen
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .addToBackStack(null) // Allows the user to press 'Back'
                        .commit();
            }
        });

        recyclerView.setAdapter(adapter);
    }
}