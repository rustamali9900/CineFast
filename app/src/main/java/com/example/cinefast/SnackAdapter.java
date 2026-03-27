package com.example.cinefast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SnackAdapter extends ArrayAdapter<Snack> {

    public SnackAdapter(@NonNull Context context, @NonNull List<Snack> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_snack, parent, false);
        }

        Snack currentSnack = getItem(position);

        ShapeableImageView image = convertView.findViewById(R.id.snack_image);
        TextView name = convertView.findViewById(R.id.snack_name);
        TextView desc = convertView.findViewById(R.id.snack_desc);
        TextView quantityText = convertView.findViewById(R.id.tv_quantity);
        MaterialButton btnAdd = convertView.findViewById(R.id.btn_add);
        MaterialButton btnRemove = convertView.findViewById(R.id.btn_remove);

        if (currentSnack != null) {
            image.setImageResource(currentSnack.getImageResId());
            name.setText(currentSnack.getName());
            desc.setText(currentSnack.getDescription());
            quantityText.setText(String.valueOf(currentSnack.getQuantity()));

            btnAdd.setOnClickListener(v -> {
                currentSnack.setQuantity(currentSnack.getQuantity() + 1);
                notifyDataSetChanged();
            });

            btnRemove.setOnClickListener(v -> {
                if (currentSnack.getQuantity() > 0) {
                    currentSnack.setQuantity(currentSnack.getQuantity() - 1);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }
}