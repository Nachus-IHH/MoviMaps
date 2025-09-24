package com.example.movimaps.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movimaps.R;
import com.example.movimaps.sql.database.LocationHistory;
import java.util.ArrayList;
import java.util.List;

public class LocationHistoryAdapter extends RecyclerView.Adapter<LocationHistoryAdapter.LocationViewHolder> {

    private List<LocationHistory> locations = new ArrayList<>();
    private OnLocationClickListener listener;

    public interface OnLocationClickListener {
        void onLocationClick(LocationHistory location);
        void onFavoriteClick(LocationHistory location);
    }

    public LocationHistoryAdapter(OnLocationClickListener listener) {
        this.listener = listener;
    }

    public void setLocations(List<LocationHistory> locations) {
        this.locations = locations != null ? locations : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location_history, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationHistory location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLocationName;
        private TextView tvLocationAddress;
        private TextView tvTimestamp;
        private ImageView ivFavorite;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            tvLocationAddress = itemView.findViewById(R.id.tvLocationAddress);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }

        public void bind(LocationHistory location) {
            tvLocationName.setText(location.getName());
            tvLocationAddress.setText(location.getAddress());
            tvTimestamp.setText(location.getFormattedTimestamp());

            // Set favorite icon
            ivFavorite.setImageResource(location.isFavorite() ?
                    R.drawable.ic_favorite_outline : R.drawable.ic_favorite_outline);

            // Set click listeners
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLocationClick(location);
                }
            });

            ivFavorite.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFavoriteClick(location);
                }
            });
        }
    }
}
