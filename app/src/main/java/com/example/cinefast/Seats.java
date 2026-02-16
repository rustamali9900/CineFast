package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Seats extends AppCompatActivity {

    LinearLayout theater_container;
    String movieName;
    String date;
    TextView movie_name;
    TextView movie_date;

    String selectedRow;
    String selectedSeat;

    int TicketPrice = 100;
    int totalAmount = 0;

    Button snacks;

    Button bookSeats;


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

        handleIntent();
        handleBookSeats();
        handleSnacks();

    }


    private void handleSnacks() {
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder rowLettersBuilder = new StringBuilder();
                StringBuilder seatNumbersBuilder = new StringBuilder();
                int rowLetterOffset = 0;

                for (int i = 0; i < theater_container.getChildCount(); i++) {
                    View rowView = theater_container.getChildAt(i);

                    if (rowView instanceof LinearLayout) {
                        LinearLayout row = (LinearLayout) rowView;
                        char rowLetter = (char) ('A' + rowLetterOffset);
                        rowLetterOffset++;

                        int seatNum = 1;
                        for (int j = 0; j < row.getChildCount(); j++) {
                            View seat = row.getChildAt(j);
                            if (seat.getTag() != null) {
                                if (seat.isSelected()) {
                                    rowLettersBuilder.append(rowLetter).append(" ");
                                    seatNumbersBuilder.append(seatNum).append(" ");
                                }
                                seatNum++;
                            }
                        }
                    }
                }

                Intent intent = new Intent(Seats.this, Snacks.class);
                intent.putExtra("movie", movieName);
                intent.putExtra("date", date);
                intent.putExtra("total", String.valueOf(totalAmount));
                intent.putExtra("selectedRows", rowLettersBuilder.toString().trim());
                intent.putExtra("selectedSeats", seatNumbersBuilder.toString().trim());

                startActivity(intent);
            }
        });
    }
    private void handleBookSeats()
    {
        bookSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder rowLettersBuilder = new StringBuilder();
                StringBuilder seatNumbersBuilder = new StringBuilder();
                int rowLetterOffset = 0;

                for (int i = 0; i < theater_container.getChildCount(); i++) {
                    View rowView = theater_container.getChildAt(i);

                    if (rowView instanceof LinearLayout) {
                        LinearLayout row = (LinearLayout) rowView;
                        char rowLetter = (char) ('A' + rowLetterOffset);
                        rowLetterOffset++;

                        int seatNum = 1;
                        for (int j = 0; j < row.getChildCount(); j++) {
                            View seat = row.getChildAt(j);

                            if (seat.getTag() != null) {
                                if (seat.isSelected()) {
                                    rowLettersBuilder.append(rowLetter).append(" ");
                                    seatNumbersBuilder.append(seatNum).append(" ");
                                }
                                seatNum++;
                            }
                        }
                    }
                }

                Intent intent = new Intent(Seats.this, Total.class);
                intent.putExtra("total", String.valueOf(totalAmount));
                intent.putExtra("movie", movieName);
                intent.putExtra("date", date);

                intent.putExtra("selectedRows", rowLettersBuilder.toString().trim());
                intent.putExtra("selectedSeats",seatNumbersBuilder.toString().trim());

                startActivity(intent);
            }
        });
    }


    private void handleIntent()
    {
        Intent intent = getIntent();

        if (intent != null) {
            movieName = intent.getStringExtra("movie");
            date = intent.getStringExtra("date");
        }
        movie_name.setVisibility(View.VISIBLE);
        movie_name.setText(movieName);

        movie_date.setVisibility(View.VISIBLE);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,MM,yyyy");
        String todayStr = today.format(formatter);

        if(date.equals("tomorrow"))
        {
            LocalDate tomorrow = today.plusDays(1);
            String tomorrowStr = tomorrow.format(formatter);
            movie_date.setText(tomorrowStr);
        }
        else {
            movie_date.setText(todayStr);
        }
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

                        if (v.isSelected()) {
                            v.setSelected(false);
                            totalAmount -= TicketPrice;
                        } else {
                            v.setSelected(true);
                            totalAmount += TicketPrice;
                        }
                    }
                });
            }
        }
    }

    private void init()
    {
        theater_container = findViewById(R.id.theater_container);
        movie_name = findViewById(R.id.movie_name);
        movie_date = findViewById(R.id.movie_date);

        bookSeats= findViewById(R.id.bookSeats);

        snacks = findViewById(R.id.snacks);

        movie_name.setVisibility(View.GONE);
        movie_date.setVisibility(View.GONE);
    }
}