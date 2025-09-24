package com.example.movimaps.sql.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.movimaps.sql.dao.BusStopDao;
import com.example.movimaps.sql.dao.ChoferTransporteDao;
import com.example.movimaps.sql.dao.DriverDao;
import com.example.movimaps.sql.dao.DriverRouteDao;
import com.example.movimaps.sql.dao.HistoryDao;
import com.example.movimaps.sql.dao.LocationHistoryDao;
import com.example.movimaps.sql.dao.ParadaDao;
import com.example.movimaps.sql.dao.RouteHistoryDao;
import com.example.movimaps.sql.dao.RutaDao;
import com.example.movimaps.sql.dao.RutaHasParadaDao;
import com.example.movimaps.sql.dao.SearchHistoryDao;
import com.example.movimaps.sql.dao.TransportDao;
import com.example.movimaps.sql.dao.UserDao;

@Database(entities = {
        User.class,
        Ruta.class,
        Parada.class,
        Transporte.class,
        ChoferTransporte.class,
        RutaHasParada.class,    // MODIFICADO: Asegurarse que esté aquí
        Driver.class,
        DriverRoute.class,
        BusStop.class,
        RouteHistory.class,
        SearchHistory.class,
        LocationHistory.class
}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LocationHistoryDao LocationHistoryDao();
    public abstract SearchHistoryDao SearchHistoryDao();
    public abstract RouteHistoryDao RouteHistoryDao();
    private static AppDatabase INSTANCE;
    public abstract RutaDao rutaDao();
    public abstract TransportDao transportDao();
    public abstract ParadaDao paradaDao();
    public abstract ChoferTransporteDao choferTransporteDao();
    public abstract RutaHasParadaDao rutaHasParadaDao();
    public abstract DriverRouteDao DriverRouteDao();
    public abstract BusStopDao busStopDao();
    public abstract HistoryDao historyDao();
    public abstract DriverDao driverDao();
    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "transport_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
