package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.entity.RegistroTrayectoUsuario;

@Dao
public interface RegistroTrayectoUsuarioDao {
    @Insert
    long insertar(RegistroTrayectoUsuario registroTrayectoUsuario);

    @Update
    int actualizar(RegistroTrayectoUsuario registroTrayectoUsuario);

    @Delete
    int delete(RegistroTrayectoUsuario registroTrayectoUsuario);

    @Query("SELECT * FROM Registro_Trayecto_Usuario WHERE id_registro = :id")
    RegistroTrayectoUsuario getPorId(int id);
}
