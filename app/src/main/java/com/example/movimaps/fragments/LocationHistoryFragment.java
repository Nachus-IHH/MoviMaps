package com.example.movimaps.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movimaps.MainActivity;
import com.example.movimaps.R;
import com.example.movimaps.adapters.LocationHistoryAdapter;
import com.example.movimaps.sql.database.LocationHistory;
import com.example.movimaps.utils.HistoryManager;
import java.util.List;

public class LocationHistoryFragment extends Fragment implements LocationHistoryAdapter.OnLocationClickListener {

    private RecyclerView recyclerView;
    private TextView tvEmptyState;
    private LocationHistoryAdapter adapter;
    private HistoryManager historyManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_history, container, false);

        initViews(view);
        setupRecyclerView();
        loadData();

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewLocations);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        historyManager = new HistoryManager(getContext());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LocationHistoryAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        List<LocationHistory> locations = historyManager.getRecentLocations(50);

        if (locations != null && !locations.isEmpty()) {
            adapter.setLocations(locations);
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyState.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        }
    }

    public void refreshData() {
        loadData();
    }

    @Override
    public void onLocationClick(LocationHistory location) {
        // Open map with selected location
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("action", "show_location");
        intent.putExtra("lat", location.getLatitude());
        intent.putExtra("lng", location.getLongitude());
        intent.putExtra("location_name", location.getName());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(LocationHistory location) {
        boolean newFavoriteStatus = !location.isFavorite();
        historyManager.toggleLocationFavorite(location.getId(), newFavoriteStatus);
        location.setFavorite(newFavoriteStatus);
        adapter.notifyDataSetChanged();
    }
}
