package com.example.movimaps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movimaps.R;
import java.util.List;

public class OptimizedRoutesAdapter extends RecyclerView.Adapter<OptimizedRoutesAdapter.RouteViewHolder> {

    private List<String> routes;
    private OnRouteClickListener listener;

    public interface OnRouteClickListener {
        void onRouteClick(String route, int position);
    }

    public OptimizedRoutesAdapter(List<String> routes) {
        this.routes = routes;
    }

    public void setOnRouteClickListener(OnRouteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_optimized_route, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        String route = routes.get(position);
        holder.bind(route, position);
    }

    @Override
    public int getItemCount() {
        return routes != null ? routes.size() : 0;
    }

    public void updateRoutes(List<String> newRoutes) {
        this.routes = newRoutes;
        notifyDataSetChanged();
    }

    class RouteViewHolder extends RecyclerView.ViewHolder {
        private TextView routeNameText;
        private TextView routeDetailsText;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeNameText = itemView.findViewById(R.id.routeNameText);
            routeDetailsText = itemView.findViewById(R.id.routeDetailsText);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onRouteClick(routes.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }

        public void bind(String route, int position) {
            routeNameText.setText("Ruta " + (position + 1));
            routeDetailsText.setText(route);
        }
    }
}
