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


    private void handleSeats() {

            String[] rowArray = selectedRow.trim().split("\\s+");
            String[] seatArray = selectedSeats.trim().split("\\s+");
            String seatPrice = "100$";

            for (int i = 0; i < rowArray.length; i++) {
                String combinedInfo = "Row " + rowArray[i] + ", Seat " + seatArray[i];

                if (i == 0) {
                    movie1_seatInfo.setVisibility(View.VISIBLE);
                    movie1_seatInfo.setText(combinedInfo);
                    seat1_price.setText(seatPrice);
                } else if (i == 1) {
                    movie2_seatInfo.setVisibility(View.VISIBLE);
                    movie2_seatInfo.setText(combinedInfo);
                    seat2_price.setText(seatPrice);
                } else if (i == 2) {
                    movie3_seatInfo.setVisibility(View.VISIBLE);
                    movie3_seatInfo.setText(combinedInfo);
                    seat3_price.setText(seatPrice);
                }
            }
    }

    private void handleSnacks()
    {
    
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
    }

}