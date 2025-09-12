package com.example.movimaps.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movimaps.R;
import com.example.movimaps.sql.entity.RouteHistory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class RouteHistoryAdapter extends RecyclerView.Adapter<RouteHistoryAdapter.ViewHolder> {
    private List<RouteHistory> routes = new ArrayList<>();
    private OnRouteClickListener listener;

    public interface OnRouteClickListener {
        void onRouteClick(RouteHistory route);
        void onFavoriteClick(RouteHistory route);
    }

    public RouteHistoryAdapter(OnRouteClickListener listener) {
        this.listener = listener;
    }

    public void setRoutes(List<RouteHistory> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RouteHistory route = routes.get(position);
        holder.bind(route);
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrigin, tvDestination, tvDistance, tvDuration, tvDate, tvRouteType;
        private ImageView ivFavorite, ivRouteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrigin = itemView.findViewById(R.id.tvOrigin);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvRouteType = itemView.findViewById(R.id.tvRouteType);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            ivRouteIcon = itemView.findViewById(R.id.ivRouteIcon);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRouteClick(routes.get(getAdapterPosition()));
                }
            });

            ivFavorite.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFavoriteClick(routes.get(getAdapterPosition()));
                }
            });
        }

        public void bind(RouteHistory route) {
            tvOrigin.setText(route.getOrigin());
            tvDestination.setText(route.getDestination());
            tvDistance.setText(route.getDistance());
            tvDuration.setText(route.getDuration());

            // Formatear fecha
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
            tvDate.setText(sdf.format(new Date(route.getTimestamp())));

            // Tipo de ruta
            String routeTypeText = "";
            switch (route.getRouteType()) {
                case "driving":
                    routeTypeText = "🚗 Conduciendo";
                    break;
                case "walking":
                    routeTypeText = "🚶 Caminando";
                    break;
                case "cycling":
                    routeTypeText = "🚴 Ciclismo";
                    break;
                default:
                    routeTypeText = "🛣️ Ruta";
                    break;
            }
            tvRouteType.setText(routeTypeText);

            // Estado de favorito
            ivFavorite.setImageResource(route.isFavorite() ?
                    R.drawable.ic_favorite_filled : R.drawable.ic_favorite_outline);
        }
    }
}
