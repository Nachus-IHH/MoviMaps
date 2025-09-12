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
import java.util.ArrayList;
import java.util.List;

public class BusStopsFragment extends Fragment {

    private RecyclerView recyclerViewStops;
    private List<String> busStopsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_stops, container, false);

        initViews(view);
        setupRecyclerView();
        loadBusStops();

        return view;
    }

    private void initViews(View view) {
        recyclerViewStops = view.findViewById(R.id.recyclerViewStops);
    }

    private void setupRecyclerView() {
        recyclerViewStops.setLayoutManager(new LinearLayoutManager(getContext()));
        busStopsList = new ArrayList<>();
    }

    private void loadBusStops() {
        // Datos de ejemplo para paradas de autobús
        busStopsList.add("Parada Central");
        busStopsList.add("Parada Universidad");
        busStopsList.add("Parada Hospital");
        busStopsList.add("Parada Mercado");
        busStopsList.add("Parada Terminal");
        busStopsList.add("Parada Plaza");

        // Aquí puedes agregar un adaptador personalizado para mostrar las paradas
        // BusStopAdapter adapter = new BusStopAdapter(busStopsList);
        // recyclerViewStops.setAdapter(adapter);
    }
}

