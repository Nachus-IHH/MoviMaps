package com.example.movimaps.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.movimaps.R;
import com.example.movimaps.pojo.EventoForm;
import com.example.movimaps.forms.OnFormStepCompletedListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class EventInfoFragment extends Fragment {

    private OnFormStepCompletedListener<EventoForm> listener;
    private TextInputEditText editTextFechaHoraInicio;
    private TextInputEditText editTextFechaHoraFin;
    private Calendar calendar;

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

    public EventInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        editTextFechaHoraInicio = view.findViewById(R.id.datetime_start);
        editTextFechaHoraFin = view.findViewById(R.id.datetime_fin);

        calendar = Calendar.getInstance();

        editTextFechaHoraInicio.setOnClickListener(v -> showDateTimePicker(editTextFechaHoraInicio));
        editTextFechaHoraFin.setOnClickListener(v -> showDateTimePicker(editTextFechaHoraFin));
        // Ejemplo del boton siguiente
        Button buttonGoAddress = view.findViewById(R.id.button_go_address);
        buttonGoAddress.setOnClickListener(v -> {
            // Recopilamos los datos del formulario del fragmento
            //String nombre = ((EditText) view.findViewById(R.id.textField_info_name)).getText().toString();
            EventoForm event = new EventoForm();

            // 1. Validar datos
            // 2. Si es valido, llamar al metodo de la interfaz
            if (listener != null) {
                listener.onNextStep(event);
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

    private void showDateTimePicker(final TextInputEditText targetEditText) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showTimePicker(targetEditText);
        };

        new DatePickerDialog(getContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker(final TextInputEditText targetEditText) {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            targetEditText.setText(simpleDateFormat.format(calendar.getTime()));
        };

        new TimePickerDialog(getContext(), timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true).show();
    }
}