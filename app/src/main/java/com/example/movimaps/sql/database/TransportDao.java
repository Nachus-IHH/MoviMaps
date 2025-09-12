package com.example.movimaps.sql.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TransportDao {

    // OPERACIONES PARA USUARIOS (usando tu esquema)
    @Insert
    long insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM User WHERE id_user = :idUser")
    User getUserById(int idUser);

    @Query("SELECT * FROM User WHERE usuario = :usuario AND password = :password")
    User loginUser(String usuario, String password);

    @Query("SELECT COUNT(*) FROM User WHERE usuario = :usuario")
    int checkUsuarioExists(String usuario);

    @Query("SELECT COUNT(*) FROM User WHERE correo = :correo")
    int checkCorreoExists(String correo);

    // OPERACIONES PARA CHOFERES (usando ChoferTransporte de tu BD)
    @Insert
    long insertChofer(ChoferTransporte chofer);

    @Update
    void updateChofer(ChoferTransporte chofer);

    @Delete
    void deleteChofer(ChoferTransporte chofer);

    @Query("SELECT * FROM Chofer_Transporte ORDER BY nombre ASC")
    List<ChoferTransporte> getAllChoferes();

    @Query("SELECT * FROM Chofer_Transporte WHERE id_chofer = :idChofer")
    ChoferTransporte getChoferById(int idChofer);

    // OPERACIONES PARA RUTAS (usando tu esquema)
    @Insert
    long insertRuta(Ruta ruta);

    @Update
    void updateRuta(Ruta ruta); // Añadido para poder actualizar rutas

    @Query("SELECT * FROM Ruta ORDER BY no_ruta ASC")
    List<Ruta> getAllRutas();

    @Query("SELECT * FROM Ruta WHERE id_ruta = :idRuta")
    Ruta getRutaById(int idRuta);

    // OPERACIONES PARA PARADAS (usando tu esquema)
    @Insert
    long insertParada(Parada parada);

    @Update
    void updateParada(Parada parada); // Añadido para poder actualizar paradas

    @Query("SELECT * FROM Parada ORDER BY nombre_parada ASC")
    List<Parada> getAllParadas();

    @Query("SELECT * FROM Parada WHERE nombre_parada LIKE :nombre")
    List<Parada> searchParadasByName(String nombre);

    // OPERACIONES PARA RUTA_HAS_PARADA (NUEVO)
    @Insert
    long insertRutaHasParada(RutaHasParada rutaHasParada);

    @Update
    void updateRutaHasParada(RutaHasParada rutaHasParada);

    @Delete
    void deleteRutaHasParada(RutaHasParada rutaHasParada);

    // CONSULTAS COMBINADAS usando tu esquema de BD
    @Query("SELECT p.* FROM Parada p " +
            "INNER JOIN Ruta_has_Parada rhp ON p.id_parada = rhp.Parada_id_parada " +
            "WHERE rhp.Ruta_id_ruta = :idRuta " +
            "ORDER BY rhp.orden ASC")
    List<Parada> getParadasByRuta(int idRuta);

    @Query("SELECT r.* FROM Ruta r " +
            "INNER JOIN Ruta_has_Parada rhp ON r.id_ruta = rhp.Ruta_id_ruta " +
            "WHERE rhp.Parada_id_parada = :idParada")
    List<Ruta> getRutasByParada(int idParada);

    // Buscar rutas entre dos paradas
    @Query("SELECT DISTINCT r.* FROM Ruta r " +
            "INNER JOIN Ruta_has_Parada rhp1 ON r.id_ruta = rhp1.Ruta_id_ruta " +
            "INNER JOIN Ruta_has_Parada rhp2 ON r.id_ruta = rhp2.Ruta_id_ruta " +
            "WHERE rhp1.Parada_id_parada = :paradaOrigenId " +
            "AND rhp2.Parada_id_parada = :paradaDestinoId " +
            "AND rhp1.orden < rhp2.orden")
    List<Ruta> getRutasEntreParadas(int paradaOrigenId, int paradaDestinoId);
}
