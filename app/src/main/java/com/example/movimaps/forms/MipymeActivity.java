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
import com.example.movimaps.pojo.MipymeForm;
import com.example.movimaps.fragments.AddressFormFragment;
import com.example.movimaps.fragments.MipymeContactFragment;
import com.example.movimaps.fragments.MipymeInfoFragment;
public class MipymeActivity extends AppCompatActivity
        implements OnFormStepCompletedListener<MipymeForm> {

    Button buttonBack;
    private MipymeForm currentMipyme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mipyme);

        currentMipyme = new MipymeForm();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_container, new MipymeInfoFragment())
                    .commit();
        }

        buttonBack = findViewById(R.id.buttonBackMain);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(MipymeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Metodo que implementa la interfaz
    @Override
    public void onNextStep(MipymeForm data) {
        currentMipyme = data;   //Guarda los datos recibidos

        // Logica de navegacion
        if (getSupportFragmentManager().findFragmentById(R.id.form_container) instanceof MipymeInfoFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_container, new MipymeContactFragment())
                    .addToBackStack(null) // Para que el botón de atrás funcione
                    .commit();
        } else if (getSupportFragmentManager().findFragmentById(R.id.form_container) instanceof MipymeContactFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_container, new AddressFormFragment().newInstance(AddressFormFragment.FORM_TYPE_MIPYME))
                    .addToBackStack(null) // Para que el botón de atrás funcione
                    .commit();
        } else if (getSupportFragmentManager().findFragmentById(R.id.form_container) instanceof AddressFormFragment) {
            // Por aqui ya no pasa
            Intent intent = new Intent(MipymeActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onAddressSubmitted(Direccion direccion) {
        currentMipyme.setDireccion(direccion);
        // Aqui se podria avanzar al siguiente paso o guardar todo
        // saveMipymeToDatabase
        Intent intent = new Intent(MipymeActivity.this, MainActivity.class);
        startActivity(intent);
    }

}