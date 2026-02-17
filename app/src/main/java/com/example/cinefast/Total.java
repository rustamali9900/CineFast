package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Total extends AppCompatActivity {

    String totalPrice;

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

        handleIntent();

    }

    private void handleIntent()
    {
        Intent intent = getIntent();

        if (intent != null) {
            totalPrice = intent.getStringExtra("total");
            String movie = intent.getStringExtra("movie");
            String date = intent.getStringExtra("date");

            String selectedRow = intent.getStringExtra("selectedRows");
            String selectedSeats = intent.getStringExtra("selectedSeats");

            String popcornsQty = intent.getStringExtra("popcornsQty");
            String nachosQty = intent.getStringExtra("nachosQty");
            String pepsiQty = intent.getStringExtra("pepsiQty");
            String caramel_popcornQty = intent.getStringExtra("caramel_popcornQty");

            String popcornsPrice = intent.getStringExtra("popcorn_price");
            String nachosPrice = intent.getStringExtra("nachos_price");
            String pepsiPrice = intent.getStringExtra("pepsi_price");
            String caramel_popcornPrice = intent.getStringExtra("caramel_popcorn_price");

        }

    }
}