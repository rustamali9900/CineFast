package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class Snacks extends AppCompatActivity {

    MaterialButton add_popcorn, remove_popcorn, add_nachos, remove_nachos, add_pepsi, remove_pepsi;
    TextView popcorn_quantity, nachos_quantity, pepsi_quantity;
    String movie, date, selectedRow, selectedSeats;
    int seatTotalPrice = 0;
    int popcornCount = 0;
    int nachosCount = 0;
    int pepsiCount = 0;

    // Hardcoded Prices per item
    final int POPCORN_PRICE = 250;
    final int NACHOS_PRICE = 300;
    final int PEPSI_PRICE = 150;

    Button onSubmit;

    int snackTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_snacks);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.snacks), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        handleIntent();
        handleListeners();
    }

    private void handleListeners() {
        add_popcorn.setOnClickListener(v -> {
            popcornCount++;
            calculateTotals();
        });
        remove_popcorn.setOnClickListener(v -> {
            if (popcornCount > 0) {
                popcornCount--;
                calculateTotals();
            }
        });

        add_nachos.setOnClickListener(v -> {
            nachosCount++;
            calculateTotals();
        });
        remove_nachos.setOnClickListener(v -> {
            if (nachosCount > 0) {
                nachosCount--;
                calculateTotals();
            }
        });

        add_pepsi.setOnClickListener(v -> {
            pepsiCount++;
            calculateTotals();
        });
        remove_pepsi.setOnClickListener(v -> {
            if (pepsiCount > 0) {
                pepsiCount--;
                calculateTotals();
            }
        });

        onSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Snacks.this, Total.class);
                i.putExtra("movie", movie);
                i.putExtra("date", date);
                i.putExtra("selectedRows", selectedRow);
                i.putExtra("selectedSeats", selectedSeats);
                i.putExtra("total", String.valueOf(snackTotal));
                i.putExtra("popcornsQty",String.valueOf(popcornCount));
                i.putExtra("nachosQty",String.valueOf(nachosCount));
                i.putExtra("pepsiQty",String.valueOf(pepsiCount));
                startActivity(i);

            }

        });
    }

    private void calculateTotals() {
        popcorn_quantity.setText(String.valueOf(popcornCount));
        nachos_quantity.setText(String.valueOf(nachosCount));
        pepsi_quantity.setText(String.valueOf(pepsiCount));

        snackTotal = (popcornCount * POPCORN_PRICE) +
                (nachosCount * NACHOS_PRICE) +
                (pepsiCount * PEPSI_PRICE);

    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            movie = intent.getStringExtra("movie");
            date = intent.getStringExtra("date");
            selectedRow = intent.getStringExtra("selectedRows");
            selectedSeats = intent.getStringExtra("selectedSeats");

            String priceStr = intent.getStringExtra("total");
            if (priceStr != null) {
                seatTotalPrice = Integer.parseInt(priceStr);
            }
        }
    }

    private void init() {
        add_popcorn = findViewById(R.id.add_popcorn);
        remove_popcorn = findViewById(R.id.remove_popcorn);
        add_nachos = findViewById(R.id.add_nachos);
        remove_nachos = findViewById(R.id.remove_nachos);
        add_pepsi = findViewById(R.id.add_pepsi);
        remove_pepsi = findViewById(R.id.remove_pepsi);

        onSubmit = findViewById(R.id.onSubmit);

        popcorn_quantity = findViewById(R.id.popcorn_quantity);
        nachos_quantity = findViewById(R.id.nachos_quantity);
        pepsi_quantity = findViewById(R.id.pepsi_quantity);
    }
}