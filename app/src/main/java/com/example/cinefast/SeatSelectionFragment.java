package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SeatSelectionFragment extends Fragment {

    LinearLayout theater_container;
    String movieName;
    String date = "today"; // Defaulting to today since tabs replaced the date toggle
    String category;
    String trailerQuery;
    int imageResId;

    TextView movie_name, movie_date;
    Button snacks, bookSeats;

    int TicketPrice = 100;
    int totalAmount = 0;
    int selectedSeatCount = 0;
    int max_seats = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        handleBundle();

        // checks which tab the user came from and changes the screen's behavior
        if (category != null && category.equals("Coming Soon")) {
            setupForComingSoon();
        } else {
            setupForNowShowing();
        }
    }

    private void handleBundle() {
        if (getArguments() != null) {
            movieName = getArguments().getString("movieTitle");
            imageResId = getArguments().getInt("movieImage");
            category = getArguments().getString("category");
            trailerQuery = getArguments().getString("movieTrailer");
        }

        movie_name.setVisibility(View.VISIBLE);
        movie_name.setText(movieName);
        movie_date.setVisibility(View.VISIBLE);

        SimpleDateFormat formatter = new SimpleDateFormat("dd,MM,yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        movie_date.setText(formatter.format(calendar.getTime()));
    }

    private void setupForNowShowing() {
        handleClickListeners(theater_container, true);

        snacks.setText("Proceed to Snacks");
        bookSeats.setText("Book Seats");

        snacks.setOnClickListener(v -> navigateData(Snacks.class));

        bookSeats.setOnClickListener(v -> {
            if (selectedSeatCount == 0) {
                Toast.makeText(requireContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(requireContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
            navigateData(Total.class);
        });
    }

    private void setupForComingSoon() {
        handleClickListeners(theater_container, false);

        snacks.setText("Watch Trailer");
        bookSeats.setText("Coming Soon");

        bookSeats.setEnabled(false);
        bookSeats.setAlpha(0.5f); // Make it look visually disabled

        snacks.setOnClickListener(v -> {
            String url = "https://www.youtube.com/results?search_query=" + trailerQuery;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    private void handleClickListeners(LinearLayout parent, boolean isClickable) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            if (child instanceof LinearLayout) {
                handleClickListeners((LinearLayout) child, isClickable);
            } else if (child.getTag() != null) {

                if (!isClickable) {
                    child.setOnClickListener(null); // Remove click listener
                    return;
                }

                child.setOnClickListener(v -> {
                    String tag = v.getTag().toString();
                    if (tag.equals("occupied")) {
                        Toast.makeText(requireContext(), "Seat Taken!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (v.isSelected()) {
                        v.setSelected(false);
                        totalAmount -= TicketPrice;
                        selectedSeatCount--;
                    } else {
                        if (selectedSeatCount < max_seats) {
                            v.setSelected(true);
                            totalAmount += TicketPrice;
                            selectedSeatCount++;
                        } else {
                            Toast.makeText(requireContext(), "Maximum 3 seats allowed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void navigateData(Class<?> targetActivity) {
        if (selectedSeatCount == 0) return;

        StringBuilder rowLettersBuilder = new StringBuilder();
        StringBuilder seatNumbersBuilder = new StringBuilder();
        int rowLetterOffset = 0;

        for (int i = 0; i < theater_container.getChildCount(); i++) {
            View rowView = theater_container.getChildAt(i);
            if (rowView instanceof LinearLayout) {
                LinearLayout row = (LinearLayout) rowView;
                char rowLetter = (char) ('A' + rowLetterOffset);
                rowLetterOffset++;

                int seatNum = 1;
                for (int j = 0; j < row.getChildCount(); j++) {
                    View seat = row.getChildAt(j);
                    if (seat.getTag() != null) {
                        if (seat.isSelected()) {
                            rowLettersBuilder.append(rowLetter).append(" ");
                            seatNumbersBuilder.append(seatNum).append(" ");
                        }
                        seatNum++;
                    }
                }
            }
        }

        Intent intent = new Intent(requireContext(), targetActivity);
        intent.putExtra("movie", movieName);
        intent.putExtra("date", date);
        intent.putExtra("total", String.valueOf(totalAmount));
        intent.putExtra("selectedRows", rowLettersBuilder.toString().trim());
        intent.putExtra("selectedSeats", seatNumbersBuilder.toString().trim());
        intent.putExtra("image", String.valueOf(imageResId));

        startActivity(intent);
    }

    private void init(View view) {
        theater_container = view.findViewById(R.id.theater_container);
        movie_name = view.findViewById(R.id.movie_name);
        movie_date = view.findViewById(R.id.movie_date);

        bookSeats = view.findViewById(R.id.bookSeats);
        snacks = view.findViewById(R.id.snacks);

        movie_name.setVisibility(View.GONE);
        movie_date.setVisibility(View.GONE);
    }
}