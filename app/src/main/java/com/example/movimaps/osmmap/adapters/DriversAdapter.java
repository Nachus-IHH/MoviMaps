package com.example.movimaps.osmmap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.osmmap.R;
import com.example.osmmap.database.Driver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.ViewHolder>{
    // NUEVO: Adaptador para lista de choferes
    private List<Driver> drivers = new ArrayList<>();

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_driver, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Driver driver = drivers.get(position);
        holder.bind(driver);
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDriverId, tvName, tvUnit, tvPhone, tvEmail, tvCreatedDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDriverId = itemView.findViewById(R.id.tvDriverId);
            tvName = itemView.findViewById(R.id.tvName);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
        }

        // NUEVO: Vincular datos del chofer
        public void bind(Driver driver) {
            tvDriverId.setText("ID: " + driver.getDriverId());
            tvName.setText(driver.getName());
            tvUnit.setText("Unidad: " + driver.getUnit());

            // Mostrar teléfono si existe
            if (driver.getPhoneNumber() != null && !driver.getPhoneNumber().isEmpty()) {
                tvPhone.setText("📞 " + driver.getPhoneNumber());
                tvPhone.setVisibility(View.VISIBLE);
            } else {
                tvPhone.setVisibility(View.GONE);
            }

            // Mostrar email si existe
            if (driver.getEmail() != null && !driver.getEmail().isEmpty()) {
                tvEmail.setText("📧 " + driver.getEmail());
                tvEmail.setVisibility(View.VISIBLE);
            } else {
                tvEmail.setVisibility(View.GONE);
            }

            // Fecha de registro
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            tvCreatedDate.setText("Registrado: " + sdf.format(new Date(driver.getCreatedAt())));
        }
    }
}
