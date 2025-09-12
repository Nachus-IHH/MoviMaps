package com.example.movimaps.adapters;

// reponer las clases faltantes para que funcione
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.movimaps.fragments.DriversFragment.java;
import com.example.movimaps.fragments.DriverRoutesFragment;
import com.example.movimaps.fragments.BusStopsFragment;

public class DriverPagerAdapter extends FragmentStateAdapter {
    // NUEVO: Adaptador para tabs de choferes
    public DriverPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DriversFragment();
            case 1:
                return new DriverRoutesFragment();
            case 2:
                return new BusStopsFragment();
            default:
                return new DriversFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
