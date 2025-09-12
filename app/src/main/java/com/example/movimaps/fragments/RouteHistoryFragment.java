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
import com.example.movimaps.adapters.RouteHistoryAdapter;
import com.example.movimaps.database.RouteHistory;
import com.mmt.mapas.utils.HistoryManager;
import java.util.List;

public class RouteHistoryFragment extends Fragment implements RouteHistoryAdapter.OnRouteClickListener {

    private RecyclerView recyclerView;
    private TextView tvEmptyState;
    private RouteHistoryAdapter adapter;
    private HistoryManager historyManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_history, container, false);

        initViews(view);
        setupRecyclerView();
        loadData();

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewRoutes);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        historyManager = new HistoryManager(getContext());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RouteHistoryAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        List<RouteHistory> routes = historyManager.getRecentRoutes(50);

        if (routes != null && !routes.isEmpty()) {
            adapter.setRoutes(routes);
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
    public void onRouteClick(RouteHistory route) {
        // Abrir el mapa con la ruta seleccionada
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("action", "show_route");
        intent.putExtra("origin_lat", route.getOriginLat());
        intent.putExtra("origin_lng", route.getOriginLng());
        intent.putExtra("destination_lat", route.getDestinationLat());
        intent.putExtra("destination_lng", route.getDestinationLng());
        intent.putExtra("origin_name", route.getOrigin());
        intent.putExtra("destination_name", route.getDestination());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(RouteHistory route) {
        boolean newFavoriteStatus = !route.isFavorite();
        historyManager.toggleRouteFavorite(route.getId(), newFavoriteStatus);
        route.setFavorite(newFavoriteStatus);
        adapter.notifyDataSetChanged();
    }
}
