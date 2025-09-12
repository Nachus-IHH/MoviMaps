package com.example.movimaps.settingsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class GeneralSettingsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private LinearLayout layoutLanguage, layoutRateApp, layoutHelp;
    private TextView tvSelectedLanguage;
    private Switch switchDarkMode;
    private SharedPreferences sharedPreferences;

    private String[] languages = {"ESPAÑOL", "ENGLISH", "FRANÇAIS", "DEUTSCH"};
    private String[] languageCodes = {"es", "en", "fr", "de"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);

        initViews();
        setupListeners();
        loadPreferences();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        layoutLanguage = findViewById(R.id.layout_language);
        layoutRateApp = findViewById(R.id.layout_rate_app);
        layoutHelp = findViewById(R.id.layout_help);
        tvSelectedLanguage = findViewById(R.id.tv_selected_language);
        switchDarkMode = findViewById(R.id.switch_dark_mode);

        sharedPreferences = getSharedPreferences("SettingsPrefs", MODE_PRIVATE);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        layoutLanguage.setOnClickListener(v -> showLanguageDialog());

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();

            // Aplicar tema inmediatamente
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            String message = isChecked ? "Modo oscuro activado" : "Modo claro activado";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        layoutRateApp.setOnClickListener(v -> openPlayStore());

        layoutHelp.setOnClickListener(v -> openHelpActivity());
    }

    private void loadPreferences() {
        // Cargar idioma seleccionado
        String selectedLanguage = sharedPreferences.getString("selected_language", "ESPAÑOL");
        tvSelectedLanguage.setText(selectedLanguage);

        // Cargar modo oscuro
        boolean darkMode = sharedPreferences.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(darkMode);
    }

    private void showLanguageDialog() {
        int currentSelection = 0;
        String currentLanguage = sharedPreferences.getString("selected_language", "ESPAÑOL");

        for (int i = 0; i < languages.length; i++) {
            if (languages[i].equals(currentLanguage)) {
                currentSelection = i;
                break;
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("Seleccionar Idioma")
                .setSingleChoiceItems(languages, currentSelection, (dialog, which) -> {
                    String selectedLanguage = languages[which];
                    String languageCode = languageCodes[which];

                    // Guardar selección
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("selected_language", selectedLanguage);
                    editor.putString("language_code", languageCode);
                    editor.apply();

                    // Actualizar UI
                    tvSelectedLanguage.setText(selectedLanguage);

                    Toast.makeText(this, "Idioma cambiado a " + selectedLanguage, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void openPlayStore() {
        try {
            // Intenta abrir la página de la app en Play Store
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            // Si no puede abrir Play Store, abre en navegador
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(intent);
        }
    }

    private void openHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}

