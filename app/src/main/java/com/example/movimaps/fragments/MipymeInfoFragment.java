package com.example.movimaps.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.movimaps.R;
import com.example.movimaps.pojo.MipymeForm;
import com.example.movimaps.forms.OnFormStepCompletedListener;

public class MipymeInfoFragment extends Fragment {

    private OnFormStepCompletedListener<MipymeForm> listener;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    // Metodo para adjuntar el listener a la Activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFormStepCompletedListener) {
            listener = (OnFormStepCompletedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFormStepCompletedListener<Mipyme>");
        }
    }

    public MipymeInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mipyme_info, container, false);

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Log.i("aris", "Imagen seleccionada");
            }
            else {
                Log.i("aris", "No selecciono una imagen");
            }
        });
        Button buttonLoadImage = view.findViewById(R.id.buttonLoadImage);
        buttonLoadImage.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        // Ejemplo del boton siguiente
        Button buttonGoContact = view.findViewById(R.id.button_go_contact);
        buttonGoContact.setOnClickListener(v -> {
            // Recopilamos los datos del formulario del fragmento
            String nombreMipyme = ((EditText) view.findViewById(R.id.textField_info_name)).getText().toString();
            String tipoGiro = ((EditText) view.findViewById(R.id.textField_info_type_turn)).getText().toString();
            String infoMipyme = ((EditText) view.findViewById(R.id.textField_info_info_mipyme)).getText().toString();
            String urlLogo = "url-generada";

            MipymeForm mipyme = new MipymeForm();
            mipyme.setMipyme(nombreMipyme);
            mipyme.setTipoGiro(tipoGiro);
            mipyme.setInfo(infoMipyme);
            mipyme.setUrlLogo(urlLogo);

            // 1. Validar datos
            // 2. Si es valido, llamar al metodo de la interfaz
            if (listener != null) {
                listener.onNextStep(mipyme);
            }
        });

        return view;
    }

    // Metodo para desvincular el listener cuando el fragmento se separa de la Activity
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}