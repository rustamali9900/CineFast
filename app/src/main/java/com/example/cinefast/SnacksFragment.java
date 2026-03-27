package com.example.cinefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    private ListView snacksList;
    private Button onSubmit;
    private ArrayList<Snack> snackArrayList;
    private SnackAdapter adapter;

    private String movie, date, selectedRows, selectedSeats, image;
    private int seatTotalPrice = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_snacks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        snacksList = view.findViewById(R.id.snacks_list);
        onSubmit = view.findViewById(R.id.onSubmit);

        handleBundle();
        populateSnacks();

        onSubmit.setOnClickListener(v -> calculateAndProceed());
    }

    private void handleBundle() {
        if (getArguments() != null) {
            movie = getArguments().getString("movieTitle");
            date = getArguments().getString("date", "today");
            image = String.valueOf(getArguments().getInt("movieImage"));
            selectedRows = getArguments().getString("selectedRows", "");
            selectedSeats = getArguments().getString("selectedSeats", "");

            String priceStr = getArguments().getString("total");
            if (priceStr != null && !priceStr.isEmpty()) {
                seatTotalPrice = Integer.parseInt(priceStr);
            }
        }
    }

    private void populateSnacks() {
        snackArrayList = new ArrayList<>();
        snackArrayList.add(new Snack("popcorn", "Popcorn", "Freshly popped buttery goodness", R.drawable.popcorn, 250));
        snackArrayList.add(new Snack("nachos", "Nachos", "Crispy chips with hot cheese", R.drawable.nachos, 300));
        snackArrayList.add(new Snack("pepsi", "Pepsi", "Chilled refreshing beverage", R.drawable.pepsi, 150));
        snackArrayList.add(new Snack("caramel", "Caramel Popcorn", "Sweet and crunchy caramel treat", R.drawable.caramel_popcorn, 300));

        adapter = new SnackAdapter(requireContext(), snackArrayList);
        snacksList.setAdapter(adapter);
    }

    private void calculateAndProceed() {
        int snackTotal = 0;
        int popcornCount = 0, nachosCount = 0, pepsiCount = 0, caramelCount = 0;
        int popcornPrice = 250, nachosPrice = 300, pepsiPrice = 150, caramelPrice = 300;

        for (Snack snack : snackArrayList) {
            int qty = snack.getQuantity();
            if (qty > 0) {
                snackTotal += (qty * snack.getPrice());

                switch (snack.getId()) {
                    case "popcorn": popcornCount = qty; break;
                    case "nachos": nachosCount = qty; break;
                    case "pepsi": pepsiCount = qty; break;
                    case "caramel": caramelCount = qty; break;
                }
            }
        }

        int finalTotal = seatTotalPrice + snackTotal;

        Bundle bundle = new Bundle();
        bundle.putString("movie", movie);
        bundle.putString("date", date);
        bundle.putString("image", image);
        bundle.putString("selectedRows", selectedRows);
        bundle.putString("selectedSeats", selectedSeats);
        bundle.putString("total", String.valueOf(finalTotal));

        bundle.putString("popcornsQty", String.valueOf(popcornCount));
        bundle.putString("nachosQty", String.valueOf(nachosCount));
        bundle.putString("pepsiQty", String.valueOf(pepsiCount));
        bundle.putString("caramel_popcornQty", String.valueOf(caramelCount));

        bundle.putString("popcorn_price", String.valueOf(popcornPrice));
        bundle.putString("nachos_price", String.valueOf(nachosPrice));
        bundle.putString("pepsi_price", String.valueOf(pepsiPrice));
        bundle.putString("caramel_popcorn_price", String.valueOf(caramelPrice));

        TotalFragment totalFragment = new TotalFragment();
        totalFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, totalFragment)
                .addToBackStack(null)
                .commit();
    }
}