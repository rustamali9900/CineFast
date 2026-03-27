package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

public class SeatSelectionFragment extends Fragment {

    LinearLayout theater_container;
    String movieName;
    String date = "today";
    String category;
    String trailerQuery;
    int imageResId;

    TextView movie_name, movie_date;
    Button snacks, bookSeats;

    int TicketPrice = 100;

    int totalAmount = 0;
    int selectedSeatCount = 0;


    private HashSet<Integer> selectedSeatIndices = new HashSet<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        handleBundle();

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
        setupSeatInteractions(true);

        snacks.setText("Proceed to Snacks");
        bookSeats.setText("Book Seats");

        snacks.setOnClickListener(v -> {
            if (selectedSeatCount == 0) {
                Toast.makeText(requireContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }

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

            Bundle bundle = getArguments() != null ? new Bundle(getArguments()) : new Bundle();
            bundle.putString("total", String.valueOf(totalAmount));
            bundle.putString("selectedRows", rowLettersBuilder.toString().trim());
            bundle.putString("selectedSeats", seatNumbersBuilder.toString().trim());

            SnacksFragment snacksFragment = new SnacksFragment();
            snacksFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, snacksFragment)
                    .addToBackStack(null)
                    .commit();
        });

        bookSeats.setOnClickListener(v -> {
            if (selectedSeatCount == 0) {
                Toast.makeText(requireContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(requireContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
            navigateToTotalFragment();
        });
    }

    private void setupForComingSoon() {
        setupSeatInteractions(false);

        snacks.setText("Watch Trailer");
        bookSeats.setText("Coming Soon");

        bookSeats.setEnabled(false);
        bookSeats.setAlpha(0.5f);

        snacks.setOnClickListener(v -> {
            String url = "https://www.youtube.com/results?search_query=" + trailerQuery;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    private void setupSeatInteractions(boolean isClickable) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("CineFast_BookedSeats", Context.MODE_PRIVATE);
        String key = movieName + "_" + date + "_seats";
        java.util.Set<String> permanentlyBookedSeats = prefs.getStringSet(key, new java.util.HashSet<>());

        int flatIndex = 0;
        int rowLetterOffset = 0; // Tracks Row A, B, C...

        for (int i = 0; i < theater_container.getChildCount(); i++) {
            View child = theater_container.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout row = (LinearLayout) child;
                char rowLetter = (char) ('A' + rowLetterOffset);
                rowLetterOffset++;

                int seatNum = 1; // Tracks Seat 1, 2, 3...

                for (int j = 0; j < row.getChildCount(); j++) {
                    View seat = row.getChildAt(j);

                    if (seat.getTag() != null) {
                        final int currentIndex = flatIndex;
                        String currentSeatId = String.valueOf(rowLetter) + seatNum; // Generates "A1", "B2", etc.

                        if (permanentlyBookedSeats.contains(currentSeatId)) {
                            seat.setTag("occupied"); // Mark it permanently occupied

                            seat.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.RED));

                            selectedSeatIndices.remove(currentIndex);
                        }

                        if (selectedSeatIndices.contains(currentIndex) && !seat.getTag().toString().equals("occupied")) {
                            seat.setSelected(true);
                        } else {
                            seat.setSelected(false);
                        }

                        // Handle Click Events
                        if (!isClickable) {
                            seat.setOnClickListener(null);
                        } else {
                            seat.setOnClickListener(v -> {
                                String tag = v.getTag().toString();
                                if (tag.equals("occupied")) {
                                    Toast.makeText(requireContext(), "This seat is already booked!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (v.isSelected()) {
                                    v.setSelected(false);
                                    totalAmount -= TicketPrice;
                                    selectedSeatCount--;
                                    selectedSeatIndices.remove(currentIndex);
                                } else {
                                    v.setSelected(true);
                                    totalAmount += TicketPrice;
                                    selectedSeatCount++;
                                    selectedSeatIndices.add(currentIndex);
                                }
                            });
                        }
                        flatIndex++;
                        seatNum++; // Move to the next seat number
                    }
                }
            }
        }
    }

    private void navigateToTotalFragment() {
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

        Bundle bundle = getArguments() != null ? new Bundle(getArguments()) : new Bundle();
        bundle.putString("movie", movieName);
        bundle.putString("date", date);
        bundle.putString("total", String.valueOf(totalAmount));
        bundle.putString("selectedRows", rowLettersBuilder.toString().trim());
        bundle.putString("selectedSeats", seatNumbersBuilder.toString().trim());
        bundle.putString("image", String.valueOf(imageResId));

        TotalFragment totalFragment = new TotalFragment();
        totalFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, totalFragment)
                .addToBackStack(null)
                .commit();
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