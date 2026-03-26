package com.example.cinefast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new ComingSoonFragment();
        }
        return new NowShowingFragment();
    }

    @Override
    public int getItemCount() {
        return 2; // We have exactly 2 tabs (Now Showing and Coming Soon)
    }
}