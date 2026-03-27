package com.example.cinefast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TotalFragment extends Fragment {

    int image, popcornPrice, nachosPrice, pepsiPrice, caramel_popcornPrice, popcornsQty, nachosQty, pepsiQty, caramel_popcornQty;
    String movie, date, selectedRow, selectedSeats, totalPrice;

    ShapeableImageView movie_poster;
    TextView checkout_movie, checkout_date, checkout_bill;
    TextView movie1_seatInfo, movie2_seatInfo, movie3_seatInfo;
    TextView seat1_price, seat2_price, seat3_price;
    TextView snack1_quantityInfo, snack1_priceInfo, snack2_quantityInfo, snack2_priceInfo, snack3_quantityInfo, snack3_priceInfo, snack4_quantityInfo, snack4_priceInfo;
    MaterialButton send_Ticket;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_total, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        handleBundle();
        handleData();
        handleSentTicket();

        // Save the booking for the 3-dots menu requirement
        saveBookingToPreferences();
    }

    private void saveBookingToPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("CineFastPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int numberOfSeats = 0;
        if (selectedRow != null && !selectedRow.trim().isEmpty()) {
            numberOfSeats = selectedRow.trim().split("\\s+").length;
        }

        editor.putString("last_movie", movie);
        editor.putInt("last_seats_count", numberOfSeats);
        editor.putString("last_total_price", totalPrice + "$");
        editor.apply();
    }

    private void handleBundle() {
        if (getArguments() != null) {
            totalPrice = getArguments().getString("total");
            movie = getArguments().getString("movie");
            date = getArguments().getString("date");

            String imageStr = getArguments().getString("image");
            if (imageStr != null && !imageStr.isEmpty()) image = Integer.parseInt(imageStr);

            selectedRow = getArguments().getString("selectedRows");
            selectedSeats = getArguments().getString("selectedSeats");

            String popcornsQtyStr = getArguments().getString("popcornsQty");
            if (popcornsQtyStr != null) popcornsQty = Integer.parseInt(popcornsQtyStr);

            String nachosQtyStr = getArguments().getString("nachosQty");
            if (nachosQtyStr != null) nachosQty = Integer.parseInt(nachosQtyStr);

            String pepsiQtyStr = getArguments().getString("pepsiQty");
            if (pepsiQtyStr != null) pepsiQty = Integer.parseInt(pepsiQtyStr);

            String caramelQtyStr = getArguments().getString("caramel_popcornQty");
            if (caramelQtyStr != null) caramel_popcornQty = Integer.parseInt(caramelQtyStr);

            String popcornPriceStr = getArguments().getString("popcorn_price");
            if (popcornPriceStr != null) popcornPrice = Integer.parseInt(popcornPriceStr);

            String nachosPriceStr = getArguments().getString("nachos_price");
            if (nachosPriceStr != null) nachosPrice = Integer.parseInt(nachosPriceStr);

            String pepsiPriceStr = getArguments().getString("pepsi_price");
            if (pepsiPriceStr != null) pepsiPrice = Integer.parseInt(pepsiPriceStr);

            String caramelPriceStr = getArguments().getString("caramel_popcorn_price");
            if (caramelPriceStr != null) caramel_popcornPrice = Integer.parseInt(caramelPriceStr);
        }
    }

    private void handleData() {
        movie_poster.setVisibility(View.VISIBLE);
        checkout_movie.setVisibility(View.VISIBLE);
        checkout_bill.setVisibility(View.VISIBLE);

        movie_poster.setImageResource(image);
        checkout_movie.setText(movie);
        checkout_bill.setText(totalPrice + "$");

        handleDate();
        handleSeats();
        handleSnacks();
    }

    private void handleSentTicket() {
        send_Ticket.setOnClickListener(v -> {

            savePermanentlyBookedSeats();

            StringBuilder ticketBody = new StringBuilder();
            ticketBody.append("🎬 MOVIE TICKET: ").append(movie).append("\n");
            ticketBody.append("📅 Date: ").append(date).append("\n");
            ticketBody.append("--------------------------\n");

            ticketBody.append("💺 SEATS:\n");
            if (selectedRow != null && !selectedRow.trim().isEmpty()) {
                String[] rows = selectedRow.trim().split("\\s+");
                String[] seats = selectedSeats.trim().split("\\s+");
                for (int i = 0; i < rows.length; i++) {
                    ticketBody.append("Row ").append(rows[i]).append(", Seat ").append(seats[i]).append("\n");
                }
            }

            boolean hasSnacks = (popcornsQty > 0 || nachosQty > 0 || pepsiQty > 0 || caramel_popcornQty > 0);
            if (hasSnacks) {
                ticketBody.append("\n🍿 SNACKS:\n");
                if (popcornsQty > 0) ticketBody.append("x").append(popcornsQty).append(" Salted Popcorn\n");
                if (nachosQty > 0) ticketBody.append("x").append(nachosQty).append(" Nachos\n");
                if (pepsiQty > 0) ticketBody.append("x").append(pepsiQty).append(" Pepsi\n");
                if (caramel_popcornQty > 0) ticketBody.append("x").append(caramel_popcornQty).append(" Caramel Popcorn\n");
            }

            ticketBody.append("--------------------------\n");
            ticketBody.append("💰 TOTAL BILL: ").append(totalPrice).append("$");

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My CineFast Ticket");
            shareIntent.putExtra(Intent.EXTRA_TEXT, ticketBody.toString());
            startActivity(Intent.createChooser(shareIntent, "Share Ticket via"));

            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void savePermanentlyBookedSeats() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("CineFast_BookedSeats", Context.MODE_PRIVATE);
        String key = movie + "_" + date + "_seats";

        // Load any previously booked seats from memory
        java.util.Set<String> alreadyBooked = prefs.getStringSet(key, new java.util.HashSet<>());
        java.util.Set<String> newlyBooked = new java.util.HashSet<>(alreadyBooked);

        if (selectedRow != null && !selectedRow.trim().isEmpty()) {
            String[] rows = selectedRow.trim().split("\\s+");
            String[] seats = selectedSeats.trim().split("\\s+");
            for (int i = 0; i < rows.length; i++) {
                newlyBooked.add(rows[i] + seats[i]);
            }
        }

        // Save it back to the device
        prefs.edit().putStringSet(key, newlyBooked).apply();
    }

    private void handleSeats() {
        if (selectedRow == null || selectedRow.trim().isEmpty()) return;

        String[] rowArray = selectedRow.trim().split("\\s+");
        String[] seatArray = selectedSeats.trim().split("\\s+");

        StringBuilder combinedSeatsInfo = new StringBuilder("Seats: ");
        int seatCount = 0;

        for (int i = 0; i < rowArray.length; i++) {
            combinedSeatsInfo.append(rowArray[i]).append(seatArray[i]);
            if (i < rowArray.length - 1) {
                combinedSeatsInfo.append(", ");
            }
            seatCount++;
        }

        int totalSeatsPrice = seatCount * 100;

        movie1_seatInfo.setVisibility(View.VISIBLE);
        movie1_seatInfo.setText(combinedSeatsInfo.toString());

        seat1_price.setVisibility(View.VISIBLE);
        seat1_price.setText(totalSeatsPrice + "$");

        movie2_seatInfo.setVisibility(View.GONE);
        seat2_price.setVisibility(View.GONE);
        movie3_seatInfo.setVisibility(View.GONE);
        seat3_price.setVisibility(View.GONE);
    }

    private void handleSnacks() {
        int currentSlot = 1;

        if (popcornsQty > 0) {
            updateSnackUI(currentSlot, "x" + popcornsQty + " Salted PopCorn", (popcornPrice * popcornsQty) + "$");
            currentSlot++;
        }
        if (nachosQty > 0) {
            updateSnackUI(currentSlot, "x" + nachosQty + " Nachos", (nachosPrice * nachosQty) + "$");
            currentSlot++;
        }
        if (pepsiQty > 0) {
            updateSnackUI(currentSlot, "x" + pepsiQty + " Pepsi", (pepsiPrice * pepsiQty) + "$");
            currentSlot++;
        }
        if (caramel_popcornQty > 0) {
            updateSnackUI(currentSlot, "x" + caramel_popcornQty + " Caramel PopCorn", (caramel_popcornPrice * caramel_popcornQty) + "$");
            currentSlot++;
        }

        if (currentSlot == 1) {
            String msg = "You did not select any snacks";
            snack1_quantityInfo.setText(msg);
            snack1_quantityInfo.setVisibility(View.VISIBLE);
            snack1_priceInfo.setVisibility(View.GONE);
        }
    }

    private void updateSnackUI(int slot, String info, String price) {
        if (slot == 1) {
            snack1_quantityInfo.setText(info);
            snack1_priceInfo.setText(price);
            snack1_quantityInfo.setVisibility(View.VISIBLE);
            snack1_priceInfo.setVisibility(View.VISIBLE);
        } else if (slot == 2) {
            snack2_quantityInfo.setText(info);
            snack2_priceInfo.setText(price);
            snack2_quantityInfo.setVisibility(View.VISIBLE);
            snack2_priceInfo.setVisibility(View.VISIBLE);
        } else if (slot == 3) {
            snack3_quantityInfo.setText(info);
            snack3_priceInfo.setText(price);
            snack3_quantityInfo.setVisibility(View.VISIBLE);
            snack3_priceInfo.setVisibility(View.VISIBLE);
        } else if (slot == 4) {
            snack4_quantityInfo.setText(info);
            snack4_priceInfo.setText(price);
            snack4_quantityInfo.setVisibility(View.VISIBLE);
            snack4_priceInfo.setVisibility(View.VISIBLE);
        }
    }

    private void handleDate() {
        checkout_date.setVisibility(View.VISIBLE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd,MM,yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String todayStr = formatter.format(calendar.getTime());

        if (date != null && date.equals("tomorrow")) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String tomorrowStr = formatter.format(calendar.getTime());
            checkout_date.setText(tomorrowStr);
        } else {
            checkout_date.setText(todayStr);
        }
    }

    private void init(View view) {
        movie_poster = view.findViewById(R.id.movie_poster);
        checkout_movie = view.findViewById(R.id.checkout_movie);
        checkout_date = view.findViewById(R.id.checkout_date);
        checkout_bill = view.findViewById(R.id.checkout_bill);

        movie1_seatInfo = view.findViewById(R.id.movie1_seatInfo);
        movie2_seatInfo = view.findViewById(R.id.movie2_seatInfo);
        movie3_seatInfo = view.findViewById(R.id.movie3_seatInfo);

        seat1_price = view.findViewById(R.id.seat1_price);
        seat2_price = view.findViewById(R.id.seat2_price);
        seat3_price = view.findViewById(R.id.seat3_price);

        snack1_quantityInfo = view.findViewById(R.id.snack1_quantityInfo);
        snack1_priceInfo = view.findViewById(R.id.snack1_priceInfo);
        snack2_quantityInfo = view.findViewById(R.id.snack2_quantityInfo);
        snack2_priceInfo = view.findViewById(R.id.snack2_priceInfo);
        snack3_quantityInfo = view.findViewById(R.id.snack3_quantityInfo);
        snack3_priceInfo = view.findViewById(R.id.snack3_priceInfo);
        snack4_quantityInfo = view.findViewById(R.id.snack4_quantityInfo);
        snack4_priceInfo = view.findViewById(R.id.snack4_priceInfo);

        send_Ticket = view.findViewById(R.id.send_Ticket);

        movie_poster.setVisibility(View.GONE);
        checkout_movie.setVisibility(View.GONE);
        movie1_seatInfo.setVisibility(View.GONE);
        movie2_seatInfo.setVisibility(View.GONE);
        movie3_seatInfo.setVisibility(View.GONE);
        checkout_date.setVisibility(View.GONE);
        checkout_bill.setVisibility(View.GONE);
        seat1_price.setVisibility(View.GONE);
        seat2_price.setVisibility(View.GONE);
        seat3_price.setVisibility(View.GONE);
        snack1_quantityInfo.setVisibility(View.GONE);
        snack1_priceInfo.setVisibility(View.GONE);
        snack2_quantityInfo.setVisibility(View.GONE);
        snack2_priceInfo.setVisibility(View.GONE);
        snack3_quantityInfo.setVisibility(View.GONE);
        snack3_priceInfo.setVisibility(View.GONE);
        snack4_quantityInfo.setVisibility(View.GONE);
        snack4_priceInfo.setVisibility(View.GONE);
    }
}