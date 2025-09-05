package com.example.movimaps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movimaps.R;
import com.example.movimaps.osmmap.NativeOsmFragment;

import org.osmdroid.util.GeoPoint;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map_container, new NativeOsmFragment())
                    .commit();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NativeOsmFragment mapFragment = (NativeOsmFragment) getChildFragmentManager().findFragmentById(R.id.map_container);

        if (mapFragment != null) {
            GeoPoint myLocation = new GeoPoint(20.09771615, -98.7687703643131 );
            mapFragment.centerMapOnLocation(myLocation);
            mapFragment.addMarker(myLocation, "", "", 0);
        }
    }
}