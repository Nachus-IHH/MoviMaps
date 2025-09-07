package com.example.movimaps.sql.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.movimaps.sql.converter.DateConverter;
import com.example.movimaps.sql.dao.DireccionDao;
import com.example.movimaps.sql.dao.ParadaDao;
import com.example.movimaps.sql.dao.RegistroTrayectoUsuarioDao;
import com.example.movimaps.sql.dao.RutaDao;
import com.example.movimaps.sql.dao.RutaHasParadaDao;
import com.example.movimaps.sql.dao.UserDao;
import com.example.movimaps.sql.dao.TransportDao; // 🚀 Importado
import com.example.movimaps.sql.entity.Direccion;
import com.example.movimaps.sql.entity.Parada;
import com.example.movimaps.sql.entity.RegistroTrayectoUsuario;
import com.example.movimaps.sql.entity.Ruta;
import com.example.movimaps.sql.entity.RutaHasParada;
import com.example.movimaps.sql.entity.User;

@Database(entities = {User.class, Ruta.class, Parada.class, RutaHasParada.class, RegistroTrayectoUsuario.class, Direccion.class}, version = 1)
@TypeConverters({DateConverter.class})   // Aquí se especifican los conversors
public abstract class AppDatabase extends RoomDatabase {
    // DAOs que necesita la app
    public abstract UserDao userDao();
    public abstract RutaDao rutaDao();
    public abstract ParadaDao paradaDao();
    public abstract RutaHasParadaDao rutaHasParadaDao();
    public abstract RegistroTrayectoUsuarioDao registroTrayectoUsuarioDao();
    public abstract DireccionDao direccionDao();

    // 🚀 Nuevo DAO
    public abstract TransportDao transportDao();

    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "db-local-movimaps")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
