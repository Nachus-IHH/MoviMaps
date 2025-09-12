package com.example.movimaps.settingsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private LinearLayout layoutFaq, layoutContact, layoutTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        layoutFaq = findViewById(R.id.layout_faq);
        layoutContact = findViewById(R.id.layout_contact);
        layoutTutorial = findViewById(R.id.layout_tutorial);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        layoutFaq.setOnClickListener(v -> {
            // Abrir FAQ
            Intent intent = new Intent(this, FaqActivity.class);
            startActivity(intent);
        });

        layoutContact.setOnClickListener(v -> {
            // Abrir email para contacto
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:soporte@tuapp.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta sobre la aplicación");
            startActivity(Intent.createChooser(intent, "Enviar email"));
        });

        layoutTutorial.setOnClickListener(v -> {
            // Abrir tutorial
            Intent intent = new Intent(this, TutorialActivity.class);
            startActivity(intent);
        });
    }
}
