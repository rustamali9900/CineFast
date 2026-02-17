package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Total extends AppCompatActivity {

    int image, popcornPrice, nachosPrice, pepsiPrice, caramel_popcornPrice,popcornsQty, nachosQty, pepsiQty, caramel_popcornQty;

    String movie, date, selectedRow, selectedSeats,totalPrice;

    ShapeableImageView movie_poster;
    TextView checkout_movie, checkout_date,checkout_bill;

    TextView movie1_seatInfo, movie2_seatInfo, movie3_seatInfo;

    TextView seat1_price, seat2_price, seat3_price;

    TextView snack1_quantityInfo, snack1_priceInfo, snack2_quantityInfo, snack2_priceInfo, snack3_quantityInfo, snack3_priceInfo, snack4_quantityInfo, snack4_priceInfo;

    MaterialButton send_Ticket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_total);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.total), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        handleIntent();

        handleData();

        handleSentTicket();


    }

    private void handleIntent()
    {
        Intent intent = getIntent();

        if (intent != null) {
            totalPrice = intent.getStringExtra("total");

            movie = intent.getStringExtra("movie");
            date = intent.getStringExtra("date");

            String imageStr = intent.getStringExtra("image");
            if(imageStr != null) image = Integer.parseInt(imageStr);


            selectedRow = intent.getStringExtra("selectedRows");
            selectedSeats = intent.getStringExtra("selectedSeats");

            String popcornsQtyStr = intent.getStringExtra("popcornsQty");
            if(popcornsQtyStr != null) popcornsQty = Integer.parseInt(popcornsQtyStr);

            String nachosQtyStr = intent.getStringExtra("nachosQty");
            if(nachosQtyStr != null) nachosQty = Integer.parseInt(nachosQtyStr);

            String pepsiQtyStr = intent.getStringExtra("pepsiQty");
            if(pepsiQtyStr != null) pepsiQty = Integer.parseInt(pepsiQtyStr);

            String caramel_popcornQtyStr = intent.getStringExtra("caramel_popcornQty");
            if(caramel_popcornQtyStr != null) caramel_popcornQty = Integer.parseInt(caramel_popcornQtyStr);


            String popcornsPriceStr = intent.getStringExtra("popcorn_price");
            if(popcornsPriceStr != null) popcornPrice = Integer.parseInt(popcornsPriceStr);

            String nachosPriceStr = intent.getStringExtra("nachos_price");
            if(nachosPriceStr != null) nachosPrice = Integer.parseInt(nachosPriceStr);

            String pepsiPriceStr = intent.getStringExtra("pepsi_price");
            if(pepsiPriceStr!=null) pepsiPrice = Integer.parseInt(pepsiPriceStr);

            String caramel_popcornPriceStr = intent.getStringExtra("caramel_popcorn_price");
            if(caramel_popcornPriceStr!=null) caramel_popcornPrice = Integer.parseInt(caramel_popcornPriceStr);

        }

    }

    private void handleData()
    {
        movie_poster.setVisibility(View.VISIBLE);
        checkout_movie.setVisibility(View.VISIBLE);
        checkout_bill.setVisibility(View.VISIBLE);

        movie_poster.setImageResource(image);
        checkout_movie.setText(movie);

        checkout_bill.setText(totalPrice+"$");

        handleDate();

        handleSeats();

        handleSnacks();


    }

    private void handleSentTicket() {
        send_Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder ticketBody = new StringBuilder();
                ticketBody.append("🎬 MOVIE TICKET: ").append(movie).append("\n");
                ticketBody.append("📅 Date: ").append(date).append("\n");
                ticketBody.append("--------------------------\n");

                ticketBody.append("💺 SEATS:\n");
                String[] rows = selectedRow.trim().split("\\s+");
                String[] seats = selectedSeats.trim().split("\\s+");
                for (int i = 0; i < rows.length; i++) {
                    ticketBody.append("Row ").append(rows[i]).append(", Seat ").append(seats[i]).append("\n");
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
            }
        });
    }


    private void handleSeats() {

            String[] rowArray = selectedRow.trim().split("\\s+");
            String[] seatArray = selectedSeats.trim().split("\\s+");
            String seatPrice = "100$";

            for (int i = 0; i < rowArray.length; i++) {
                String combinedInfo = "Row " + rowArray[i] + ", Seat " + seatArray[i];

                if (i == 0) {
                    movie1_seatInfo.setVisibility(View.VISIBLE);
                    movie1_seatInfo.setText(combinedInfo);
                    seat1_price.setVisibility(View.VISIBLE);
                    seat1_price.setText(seatPrice);
                } else if (i == 1) {
                    movie2_seatInfo.setVisibility(View.VISIBLE);
                    movie2_seatInfo.setText(combinedInfo);
                    seat2_price.setVisibility(View.VISIBLE);
                    seat2_price.setText(seatPrice);
                } else if (i == 2) {
                    movie3_seatInfo.setVisibility(View.VISIBLE);
                    movie3_seatInfo.setText(combinedInfo);
                    seat3_price.setVisibility(View.VISIBLE);
                    seat3_price.setText(seatPrice);
                }
            }
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

    private void handleDate()
    {
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


    private void init()
    {
        movie_poster = findViewById(R.id.movie_poster);
        checkout_movie = findViewById(R.id.checkout_movie);
        checkout_date = findViewById(R.id.checkout_date);
        checkout_bill = findViewById(R.id.checkout_bill);

        movie1_seatInfo = findViewById(R.id.movie1_seatInfo);
        movie2_seatInfo = findViewById(R.id.movie2_seatInfo);
        movie3_seatInfo = findViewById(R.id.movie3_seatInfo);

        seat1_price = findViewById(R.id.seat1_price);
        seat2_price = findViewById(R.id.seat2_price);
        seat3_price = findViewById(R.id.seat3_price);

        snack1_quantityInfo = findViewById(R.id.snack1_quantityInfo);
        snack1_priceInfo = findViewById(R.id.snack1_priceInfo);
        snack2_quantityInfo = findViewById(R.id.snack2_quantityInfo);
        snack2_priceInfo = findViewById(R.id.snack2_priceInfo);
        snack3_quantityInfo = findViewById(R.id.snack3_quantityInfo);
        snack3_priceInfo = findViewById(R.id.snack3_priceInfo);
        snack4_quantityInfo = findViewById(R.id.snack4_quantityInfo);
        snack4_priceInfo = findViewById(R.id.snack4_priceInfo);

        send_Ticket = findViewById(R.id.send_Ticket);

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