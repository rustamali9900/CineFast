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

    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_total);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        total = findViewById(R.id.total);

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

        }

    }
}