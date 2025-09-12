package com.example.movimaps.settingsapp;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView recyclerViewFaq;
    private FaqAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        initViews();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        recyclerViewFaq = findViewById(R.id.recycler_view_faq);
    }

    private void setupRecyclerView() {
        List<FaqItem> faqItems = createFaqItems();
        faqAdapter = new FaqAdapter(faqItems);
        recyclerViewFaq.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFaq.setAdapter(faqAdapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private List<FaqItem> createFaqItems() {
        List<FaqItem> items = new ArrayList<>();

        items.add(new FaqItem(
                "¿Cómo cambio el idioma de la aplicación?",
                "Ve a Ajustes > Ajustes Generales > Idioma y selecciona tu idioma preferido."
        ));

        items.add(new FaqItem(
                "¿Cómo activo el modo oscuro?",
                "En Ajustes Generales encontrarás la opción 'Modo oscuro'. Actívala para cambiar la apariencia."
        ));

        items.add(new FaqItem(
                "¿Cómo desactivo las notificaciones?",
                "En la pantalla principal de Ajustes, en la sección Preferencias, puedes desactivar 'Recibir notificaciones'."
        ));

        items.add(new FaqItem(
                "¿Cómo contacto al soporte técnico?",
                "Ve a Ajustes > Ajustes Generales > Resolver dudas > Contactar Soporte."
        ));

        items.add(new FaqItem(
                "¿Puedo recuperar mi cuenta después de eliminarla?",
                "No, la eliminación de cuenta es permanente y no se puede deshacer."
        ));

        return items;
    }
}

