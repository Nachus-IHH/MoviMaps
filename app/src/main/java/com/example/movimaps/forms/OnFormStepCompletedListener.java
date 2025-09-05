package com.example.movimaps.forms;

import com.example.movimaps.sql.entity.Direccion;

public interface OnFormStepCompletedListener <T> {
    void onNextStep(T data);
    void onAddressSubmitted(Direccion direccion);
}
