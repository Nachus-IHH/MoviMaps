package com.example.movimaps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movimaps.R;
import com.example.movimaps.adapters.DriversAdapter;
import com.example.movimaps.database.AppDatabase;
import com.example.movimaps.database.Driver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class DriversFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvEmptyState;
    private FloatingActionButton fabAddDriver;
    private DriversAdapter adapter;
    private AppDatabase database;

    // NUEVO: Formulario para registro de chofer
    private View formLayout;
    private EditText etDriverId, etName, etUnit, etPhone, etEmail;
    private Button btnSaveDriver, btnCancelDriver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drivers, container, false);

        initViews(view);
        setupRecyclerView();
        setupFormListeners();
        loadDrivers();

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewDrivers);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        fabAddDriver = view.findViewById(R.id.fabAddDriver);

        // NUEVO: Inicializar formulario de chofer
        formLayout = view.findViewById(R.id.formLayout);
        etDriverId = view.findViewById(R.id.etDriverId);
        etName = view.findViewById(R.id.etName);
        etUnit = view.findViewById(R.id.etUnit);
        etPhone = view.findViewById(R.id.etPhone);
        etEmail = view.findViewById(R.id.etEmail);
        btnSaveDriver = view.findViewById(R.id.btnSaveDriver);
        btnCancelDriver = view.findViewById(R.id.btnCancelDriver);

        database = AppDatabase.getInstance(getContext());

        fabAddDriver.setOnClickListener(v -> showDriverForm());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DriversAdapter();
        recyclerView.setAdapter(adapter);
    }

    // NUEVO: Configurar listeners del formulario de chofer
    private void setupFormListeners() {
        btnSaveDriver.setOnClickListener(v -> saveDriver());
        btnCancelDriver.setOnClickListener(v -> hideDriverForm());
    }

    // NUEVO: Mostrar formulario para registrar chofer
    private void showDriverForm() {
        formLayout.setVisibility(View.VISIBLE);
        fabAddDriver.hide();
        clearForm();
    }

    // NUEVO: Ocultar formulario de chofer
    private void hideDriverForm() {
        formLayout.setVisibility(View.GONE);
        fabAddDriver.show();
        clearForm();
    }

    // NUEVO: Limpiar formulario de chofer
    private void clearForm() {
        etDriverId.setText("");
        etName.setText("");
        etUnit.setText("");
        etPhone.setText("");
        etEmail.setText("");
    }

    // NUEVO: Guardar chofer en base de datos
    private void saveDriver() {
        String driverId = etDriverId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Validaciones
        if (driverId.isEmpty()) {
            etDriverId.setError("Ingrese el ID del chofer");
            etDriverId.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            etName.setError("Ingrese el nombre del chofer");
            etName.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            etUnit.setError("Ingrese la unidad asignada");
            etUnit.requestFocus();
            return;
        }

        // Verificar si el ID ya existe
        if (database.driverDao().checkDriverIdExists(driverId) > 0) {
            etDriverId.setError("Este ID de chofer ya existe");
            etDriverId.requestFocus();
            return;
        }

        // NUEVO: Crear y guardar chofer
        Driver newDriver = new Driver(driverId, name, unit, phone, email);
        long result = database.driverDao().insertDriver(newDriver);

        if (result > 0) {
            Toast.makeText(getContext(), "Chofer registrado exitosamente", Toast.LENGTH_SHORT).show();
            hideDriverForm();
            loadDrivers();
        } else {
            Toast.makeText(getContext(), "Error al registrar chofer", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDrivers() {
        List<Driver> drivers = database.driverDao().getAllActiveDrivers();

        if (drivers != null && !drivers.isEmpty()) {
            adapter.setDrivers(drivers);
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyState.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        }
    }

    public void refreshData() {
        loadDrivers();
    }
}
