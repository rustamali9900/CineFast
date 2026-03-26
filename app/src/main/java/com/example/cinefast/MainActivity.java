package com.example.cinefast;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Tell the Activity to load your new HomeFragment!
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, new HomeFragment())
                    .commit();
        }
    }
}