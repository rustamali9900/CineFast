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

public class ComingSoonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_comingSoon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> upcomingMovies = new ArrayList<>();
        upcomingMovies.add(new Movie("The Shawshank Redemption", "Drama | 2h 22m", R.drawable.shawshank, "The Shawshank Redemption trailer"));
        upcomingMovies.add(new Movie("Ironman", "Action | 1h 49m", R.drawable.ironman, "Ironman trailer"));
        upcomingMovies.add(new Movie("Dune: Part Three", "Sci-Fi | 1h 57m", R.drawable.dune, "Dune Messiah trailer"));

        MovieAdapter adapter = new MovieAdapter(upcomingMovies, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onBookSeatsClick(Movie movie) {
                Bundle bundle = new Bundle();
                bundle.putString("movieTitle", movie.getTitle());
                bundle.putInt("movieImage", movie.getImageResId());
                bundle.putString("movieTrailer", movie.getTrailerQuery());
                bundle.putString("category", "Coming Soon"); // Crucial for the Seat Screen logic

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