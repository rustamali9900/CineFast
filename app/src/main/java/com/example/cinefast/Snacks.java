package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Snacks extends AppCompatActivity {

    TextView snacks_Total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_snacks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        snacks_Total = findViewById(R.id.snacks_Total);
        Intent intent = getIntent();

        if (intent != null) {

            String totalPrice = intent.getStringExtra("total");
            String movie = intent.getStringExtra("movie");
            String date = intent.getStringExtra("date");
            String selectedRow = intent.getStringExtra("selectedRows");
            String selectedSeats = intent.getStringExtra("selectedSeats");


            snacks_Total.setText("Total: Rs. " + totalPrice + "name is " + movie + "date is " + date + "selected rows are " + selectedRow + "selected seats are " + selectedSeats);


        }
    }}