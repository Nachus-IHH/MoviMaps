package com.example.movimaps.sql.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

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

    private static AppDatabase INSTANCE;

    public abstract TransportDao transportDao();
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
