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
import com.example.movimaps.sql.entity.Direccion;
import com.example.movimaps.forms.OnFormStepCompletedListener;
import com.example.movimaps.osmmap.NativeOsmFragment;

public class AddressFormFragment extends Fragment {

    private static final String ARG_FORM_TYPE = "form_type";
    public static final String FORM_TYPE_MIPYME = "mipyme";
    public static final String FORM_TYPE_EVENT = "event";
    private OnFormStepCompletedListener listener;

    // Metodo de fabrica para crear el fragmento
    public static AddressFormFragment newInstance(String form_type){
        AddressFormFragment fragment = new AddressFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FORM_TYPE, form_type);
        fragment.setArguments(args);
        return fragment;
    }

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

    public AddressFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_form, container, false);

        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map_container, new NativeOsmFragment())
                    .commit();
        }

        // Modificar
        Button buttonSubmit = view.findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(v -> {
            // 1. Validar datos
            // Recopilamos los datos del formulario del fragmento
            String calle = ((EditText) view.findViewById(R.id.textField_address_street)).getText().toString();
            String numero = ((EditText) view.findViewById(R.id.textField_address_number)).getText().toString();
            String codePostal = ((EditText) view.findViewById(R.id.textField_address_code_postal)).getText().toString();
            // Buscar metodo en osmmap para que regrese latitud (lat) y longitud (lng)
            float lat = 0;
            float lng = 0;
            int idCiudad = 0; //Verificar en la DB

            Direccion direccion = new Direccion();

            // 2. Usamos la interfaz para devolver el objeto de direccion
            if (listener != null) {
                listener.onAddressSubmitted(direccion);
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