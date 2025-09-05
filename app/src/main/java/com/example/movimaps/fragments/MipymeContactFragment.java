package com.example.movimaps.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.movimaps.R;
import com.example.movimaps.pojo.MipymeForm;
import com.example.movimaps.forms.OnFormStepCompletedListener;

public class MipymeContactFragment extends Fragment {

    private OnFormStepCompletedListener listener;

    // Metodo para adjuntar el listener a la Activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFormStepCompletedListener) {
            listener = (OnFormStepCompletedListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement OnFormStepCompletedListener");
        }
    }

    /*
    public static MipymeContactFragment newInstance() {
        MipymeContactFragment fragment = new MipymeContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    */
    public MipymeContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mipyme_contact, container, false);

        // Ejemplo del boton siguiente
        Button buttonGoAddress = view.findViewById(R.id.button_go_address);
        buttonGoAddress.setOnClickListener(v -> {
            // Recopilamos los datos del formulario del fragmento
            String nombreContacto = ((EditText) view.findViewById(R.id.textField_contact_name)).getText().toString();

            MipymeForm mipyme = new MipymeForm();
            mipyme.setNombreContacto(nombreContacto);

            // 1. Validar datos
            // 2. Si es valido, llamar al metodo de la interfaz
            if (listener != null) {
                listener.onNextStep(mipyme);
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}