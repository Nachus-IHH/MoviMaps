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
import com.example.movimaps.adapters.SearchHistoryAdapter;
import com.example.movimaps.sql.database.SearchHistory;
import com.example.movimaps.utils.HistoryManager;
import java.util.List;

public class SearchHistoryFragment extends Fragment implements SearchHistoryAdapter.OnSearchClickListener {

    private RecyclerView recyclerView;
    private TextView tvEmptyState;
    private SearchHistoryAdapter adapter;
    private HistoryManager historyManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, container, false);

        initViews(view);
        setupRecyclerView();
        loadData();

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewSearches);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        historyManager = new HistoryManager(getContext());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchHistoryAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        List<SearchHistory> searches = historyManager.getRecentSearches(50);

        if (searches != null && !searches.isEmpty()) {
            adapter.setSearches(searches);
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
    public void onSearchClick(SearchHistory search) {
        // Open map with selected search location
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("action", "show_location");
        intent.putExtra("lat", search.getLatitude());
        intent.putExtra("lng", search.getLongitude());
        intent.putExtra("location_name", search.getSearchQuery());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(SearchHistory search) {
        // This method can be implemented later if favorite functionality is added to SearchHistory
    }
}
