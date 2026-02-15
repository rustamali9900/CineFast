package com.example.cinefast;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat; // Added for best practice
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.TextViewCompat;

import com.google.android.material.button.MaterialButton;

public class Movies extends AppCompatActivity {

    AppCompatButton btnToday, btnTomorrow;
    String Curr_active = "today";
    MaterialButton trailer_darkNight;
    MaterialButton trailer_inception;
    MaterialButton trailer_interstellar;
    MaterialButton trailer_shawshank;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movies);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.movies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();


        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Curr_active.equals("today")) {
                    return;
                }

                Curr_active = "today";
                handleToggle(btnToday, btnTomorrow);
            }
        });

        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Curr_active.equals("tomorrow")) {
                    return;
                }

                Curr_active = "tomorrow";
                handleToggle(btnTomorrow, btnToday);
            }
        });


        trailer_darkNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "Dark Knight trailer";
                String url = "https://www.youtube.com/results?search_query=" + query;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        trailer_inception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "Inception trailer";
                String url = "https://www.youtube.com/results?search_query=" + query;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        trailer_interstellar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "Interstellar trailer";
                String url = "https://www.youtube.com/results?search_query=" + query;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        trailer_shawshank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "The Shawshank Redemption trailer";
                String url = "https://www.youtube.com/results?search_query=" + query;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    private void handleToggle(AppCompatButton active, AppCompatButton inactive) {
        active.setBackgroundResource(R.drawable.button_capsule_active);
        active.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_filled, 0, 0, 0);
        active.setTextColor(Color.WHITE);
        TextViewCompat.setCompoundDrawableTintList(active, ColorStateList.valueOf(Color.WHITE));

        inactive.setBackgroundResource(R.drawable.button_capsule_inactive);
        inactive.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dot_hollow, 0, 0, 0);
        inactive.setTextColor(Color.parseColor("#888888"));
        TextViewCompat.setCompoundDrawableTintList(inactive, ColorStateList.valueOf(Color.parseColor("#888888")));
    }

    private void init() {
        btnToday = findViewById(R.id.btnToday);
        btnTomorrow = findViewById(R.id.btnTomorrow);
        trailer_darkNight = findViewById(R.id.trailer_darkNight);
        trailer_inception = findViewById(R.id.trailer_inception);
        trailer_interstellar = findViewById(R.id.trailer_interstellar);
        trailer_shawshank = findViewById(R.id.trailer_shawshank);


        handleToggle(btnToday, btnTomorrow);
    }
}