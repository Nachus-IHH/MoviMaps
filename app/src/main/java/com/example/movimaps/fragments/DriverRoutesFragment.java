package com.example.movimaps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movimaps.R;

public class DriverRoutesFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_routes, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDriverRoutes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: Set up adapter for driver routes

        return view;
    }
}
