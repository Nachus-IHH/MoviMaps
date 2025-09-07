package com.example.movimaps.osmmap.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.osmmap.fragments.RouteHistoryFragment;
import com.example.osmmap.fragments.SearchHistoryFragment;
import com.example.osmmap.fragments.LocationHistoryFragment;
public class HistoryPagerAdapter extends FragmentStateAdapter {
    private RouteHistoryFragment routeFragment;
    private SearchHistoryFragment searchFragment;
    private LocationHistoryFragment locationFragment;

    public HistoryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (routeFragment == null) {
                    routeFragment = new RouteHistoryFragment();
                }
                return routeFragment;
            case 1:
                if (searchFragment == null) {
                    searchFragment = new SearchHistoryFragment();
                }
                return searchFragment;
            case 2:
                if (locationFragment == null) {
                    locationFragment = new LocationHistoryFragment();
                }
                return locationFragment;
            default:
                return new RouteHistoryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void refreshCurrentFragment(int position) {
        switch (position) {
            case 0:
                if (routeFragment != null) {
                    routeFragment.refreshData();
                }
                break;
            case 1:
                if (searchFragment != null) {
                    searchFragment.refreshData();
                }
                break;
            case 2:
                if (locationFragment != null) {
                    locationFragment.refreshData();
                }
                break;
        }
    }
}
