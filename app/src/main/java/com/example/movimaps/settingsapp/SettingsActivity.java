package com.example.movimaps.settingsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchNotifications;
    private Button btnGeneralSettings, btnLogout, btnDeleteAccount;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupListeners();
        loadPreferences();
    }

    private void initViews() {
        switchNotifications = findViewById(R.id.switch_notifications);
        btnGeneralSettings = findViewById(R.id.btn_general_settings);
        btnLogout = findViewById(R.id.btn_logout);
        btnDeleteAccount = findViewById(R.id.btn_delete_account);

        sharedPreferences = getSharedPreferences("SettingsPrefs", MODE_PRIVATE);
    }

    private void setupListeners() {
        btnGeneralSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, GeneralSettingsActivity.class);
            startActivity(intent);
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("notifications_enabled", isChecked);
            editor.apply();

            String message = isChecked ? "Notificaciones activadas" : "Notificaciones desactivadas";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> showLogoutDialog());

        btnDeleteAccount.setOnClickListener(v -> showDeleteAccountDialog());
    }

    private void loadPreferences() {
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", true);
        switchNotifications.setChecked(notificationsEnabled);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Lógica para cerrar sesión
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                    // Aquí puedes redirigir a la pantalla de login
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Cuenta")
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    // Lógica para eliminar cuenta
                    Toast.makeText(this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
