package com.example.cinefast;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Seats extends AppCompatActivity {

    LinearLayout theater_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.seats), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        handleClickListeners(theater_container);
    }

    private void handleClickListeners(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            if (child instanceof ViewGroup) {
                handleClickListeners((ViewGroup) child);
            }

            else if (child.getTag() != null) {

                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tag = v.getTag().toString();

                        if (tag.equals("occupied")) {
                            Toast.makeText(Seats.this, "Seat Taken!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        v.setSelected(!v.isSelected());
                    }
                });
            }
        }
    }

    private void init()
    {
        theater_container = findViewById(R.id.theater_container);

    }
}