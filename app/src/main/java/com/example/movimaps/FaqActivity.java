package com.example.movimaps;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FaqAdapter faqAdapter;
    private List<FaqItem> faqItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        initViews();
        setupFaqData();
        setupRecyclerView();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewFaq);
    }

    private void setupFaqData() {
        faqItems = new ArrayList<>();

        faqItems.add(new FaqItem(
                "¿Cómo agregar un pin al mapa?",
                "Para agregar un pin, mantén presionado en cualquier ubicación del mapa y selecciona 'Agregar Pin' en el menú que aparece."
        ));

        faqItems.add(new FaqItem(
                "¿Cómo crear una ruta?",
                "Toca el botón de rutas, selecciona tu punto de origen y destino, y la aplicación calculará automáticamente la mejor ruta."
        ));

        faqItems.add(new FaqItem(
                "¿Cómo cambiar el tipo de mapa?",
                "En la esquina superior derecha del mapa, encontrarás un botón para cambiar entre vista satelital, terreno y mapa estándar."
        ));

        faqItems.add(new FaqItem(
                "¿La aplicación funciona sin internet?",
                "Algunas funciones básicas funcionan offline, pero necesitas conexión a internet para buscar ubicaciones y calcular rutas."
        ));

        faqItems.add(new FaqItem(
                "¿Cómo actualizar mi perfil?",
                "Ve a la sección de perfil desde el menú principal, donde podrás editar tu información personal y foto de perfil."
        ));
    }

    private void setupRecyclerView() {
        faqAdapter = new FaqAdapter(faqItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(faqAdapter);
    }
}
