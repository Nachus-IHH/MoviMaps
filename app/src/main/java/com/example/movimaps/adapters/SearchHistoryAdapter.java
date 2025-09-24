package com.example.movimaps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movimaps.R;
import com.example.movimaps.sql.database.SearchHistory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private List<SearchHistory> searches = new ArrayList<>();
    private OnSearchClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    public interface OnSearchClickListener {
        void onSearchClick(SearchHistory search);
        void onFavoriteClick(SearchHistory search);
    }

    public SearchHistoryAdapter(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public void setSearches(List<SearchHistory> searches) {
        this.searches = searches != null ? searches : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchHistory search = searches.get(position);

        holder.tvQuery.setText(search.getSearchQuery());
        holder.tvAddress.setText(search.getResultName());
        holder.tvTimestamp.setText(dateFormat.format(search.getTimestamp()));

        holder.ivFavorite.setVisibility(View.GONE);

        // Click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSearchClick(search);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuery, tvAddress, tvTimestamp;
        ImageView ivFavorite;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuery = itemView.findViewById(R.id.tvQuery);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }
}
