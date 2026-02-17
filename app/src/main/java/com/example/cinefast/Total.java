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

public class Total extends AppCompatActivity {

    int totalPrice,image, popcornPrice, nachosPrice, pepsiPrice, caramel_popcornPrice,popcornsQty, nachosQty, pepsiQty, caramel_popcornQty;

    String movie, date, selectedRow, selectedSeats;


    ShapeableImageView movie_poster;
    TextView checkout_movie;


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
            String totalPriceStr = intent.getStringExtra("total");
            if(totalPriceStr != null) totalPrice = Integer.parseInt(totalPriceStr);


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

        movie_poster.setImageResource(image);
        checkout_movie.setText(movie);
    }


    private void init()
    {
        movie_poster = findViewById(R.id.movie_poster);
        checkout_movie = findViewById(R.id.checkout_movie);

        movie_poster.setVisibility(View.GONE);
        checkout_movie.setVisibility(View.GONE);
    }

}