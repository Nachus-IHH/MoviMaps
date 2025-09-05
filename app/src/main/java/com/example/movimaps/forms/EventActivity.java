package com.example.movimaps.forms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movimaps.MainActivity;
import com.example.movimaps.R;
import com.example.movimaps.sql.entity.Direccion;
import com.example.movimaps.pojo.EventoForm;
import com.example.movimaps.fragments.AddressFormFragment;
import com.example.movimaps.fragments.EventInfoFragment;

public class EventActivity extends AppCompatActivity
        implements OnFormStepCompletedListener<EventoForm>{

    private EventoForm currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_container, new EventInfoFragment())
                    .commit();
        }

        currentEvent = new EventoForm();

        Button buttonBack = findViewById(R.id.buttonBackMain);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(EventActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onNextStep(EventoForm data) {
        currentEvent = data;

        // Logica de navegacion
        if (getSupportFragmentManager().findFragmentById(R.id.form_container) instanceof EventInfoFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_container, new AddressFormFragment().newInstance(AddressFormFragment.FORM_TYPE_EVENT))
                    .commit();
        }
    }

    @Override
    public void onAddressSubmitted(Direccion direccion) {
        currentEvent.setDireccion(direccion);
        // saveEventToDatabae();
        Intent intent = new Intent(EventActivity.this, MainActivity.class);
        startActivity(intent);
    }
}