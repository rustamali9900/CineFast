package com.example.cinefast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_view_last_booking) {
            showLastBookingDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLastBookingDialog() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("CineFastPrefs", Context.MODE_PRIVATE);
        String movie = prefs.getString("last_movie", null);
        int seats = prefs.getInt("last_seats_count", 0);
        String price = prefs.getString("last_total_price", null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Last Booking");

        if (movie == null) {
            builder.setMessage("No previous booking found.");
        } else {
            String message = "Movie: " + movie + "\nSeats: " + seats + "\nTotal Price: " + price;
            builder.setMessage(message);
        }

        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView menuIcon = view.findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireContext(), v);
            popup.getMenuInflater().inflate(R.menu.home_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_view_last_booking) {
                    showLastBookingDialog();
                    return true;
                }
                return false;
            });
            popup.show();
        });

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Now Showing");
            } else {
                tab.setText("Coming Soon");
            }
        }).attach();
    }
}