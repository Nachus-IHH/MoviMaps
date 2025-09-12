package com.example.movimaps.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.movimaps.MainActivity;
import com.example.movimaps.ProfileActivity;
import com.example.movimaps.R;
import java.util.Random;

public class NotificationHelper {

    // Canales de notificación
    public static final String CHANNEL_MAP_EVENTS = "map_events";
    public static final String CHANNEL_USER_EVENTS = "user_events";
    public static final String CHANNEL_LOCATION_UPDATES = "location_updates";
    public static final String CHANNEL_GENERAL = "general";

    // IDs de notificación
    public static final int NOTIFICATION_MAP_EVENT = 1001;
    public static final int NOTIFICATION_USER_EVENT = 1002;
    public static final int NOTIFICATION_LOCATION_UPDATE = 1003;
    public static final int NOTIFICATION_GENERAL = 1004;

    private Context context;
    private NotificationManagerCompat notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);

            // Canal para eventos del mapa
            NotificationChannel mapChannel = new NotificationChannel(
                    CHANNEL_MAP_EVENTS,
                    "Eventos del Mapa",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            mapChannel.setDescription("Notificaciones sobre nuevos pines, rutas y ubicaciones");
            mapChannel.enableLights(true);
            mapChannel.enableVibration(true);

            // Canal para eventos de usuario
            NotificationChannel userChannel = new NotificationChannel(
                    CHANNEL_USER_EVENTS,
                    "Eventos de Usuario",
                    NotificationManager.IMPORTANCE_HIGH
            );
            userChannel.setDescription("Notificaciones sobre perfil, login y configuraciones");
            userChannel.enableLights(true);
            userChannel.enableVibration(true);

            // Canal para actualizaciones de ubicación
            NotificationChannel locationChannel = new NotificationChannel(
                    CHANNEL_LOCATION_UPDATES,
                    "Actualizaciones de Ubicación",
                    NotificationManager.IMPORTANCE_LOW
            );
            locationChannel.setDescription("Notificaciones sobre cambios de ubicación");
            locationChannel.enableLights(false);
            locationChannel.enableVibration(false);

            // Canal general
            NotificationChannel generalChannel = new NotificationChannel(
                    CHANNEL_GENERAL,
                    "General",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            generalChannel.setDescription("Notificaciones generales de la aplicación");

            manager.createNotificationChannel(mapChannel);
            manager.createNotificationChannel(userChannel);
            manager.createNotificationChannel(locationChannel);
            manager.createNotificationChannel(generalChannel);
        }
    }

    // Notificación para nuevo pin agregado
    public void showNewPinNotification(String title, String location, double lat, double lng) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("action", "show_pin");
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = createPendingIntent(intent, NOTIFICATION_MAP_EVENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_MAP_EVENTS)
                .setSmallIcon(R.drawable.ic_location_pin)
                .setContentTitle("📍 " + title)
                .setContentText("Nuevo pin agregado en: " + location)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Se ha agregado un nuevo pin en: " + location +
                                "\nCoordenadas: " + String.format("%.4f, %.4f", lat, lng)))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_map, "Ver en Mapa", pendingIntent);

        notificationManager.notify(generateNotificationId(), builder.build());
    }

    // Notificación para nueva ruta creada
    public void showNewRouteNotification(String origin, String destination, String distance, String duration) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("action", "show_route");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = createPendingIntent(intent, NOTIFICATION_MAP_EVENT + 1);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_MAP_EVENTS)
                .setSmallIcon(R.drawable.ic_route)
                .setContentTitle("🛣️ Nueva Ruta Calculada")
                .setContentText("De " + origin + " a " + destination)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Ruta calculada exitosamente:\n" +
                                "📍 Origen: " + origin + "\n" +
                                "🎯 Destino: " + destination + "\n" +
                                "📏 Distancia: " + distance + "\n" +
                                "⏱️ Tiempo estimado: " + duration))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_navigation, "Ver Ruta", pendingIntent);

        notificationManager.notify(generateNotificationId(), builder.build());
    }

    // Notificación para perfil actualizado
    public void showProfileUpdatedNotification(String username) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = createPendingIntent(intent, NOTIFICATION_USER_EVENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_USER_EVENTS)
                .setSmallIcon(R.drawable.ic_person_check)
                .setContentTitle("✅ Perfil Actualizado")
                .setContentText("Tu perfil ha sido actualizado correctamente, " + username)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("¡Hola " + username + "!\n\n" +
                                "Tu perfil ha sido actualizado exitosamente. " +
                                "Todos los cambios han sido guardados de forma segura."))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_person, "Ver Perfil", pendingIntent);

        notificationManager.notify(generateNotificationId(), builder.build());
    }

    // Notificación de bienvenida
    public void showWelcomeNotification(String username) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = createPendingIntent(intent, NOTIFICATION_USER_EVENT + 1);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_welcome);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_USER_EVENTS)
                .setSmallIcon(R.drawable.ic_welcome)
                .setLargeIcon(largeIcon)
                .setContentTitle("🎉 ¡Bienvenido!")
                .setContentText("¡Hola " + username + "! Explora el mundo con nuestros mapas")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("¡Bienvenido " + username + "!\n\n" +
                                "Gracias por unirte a nuestra comunidad. " +
                                "Explora mapas, agrega pines, crea rutas y descubre nuevos lugares. " +
                                "¡Que tengas una excelente experiencia!"))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_explore, "Explorar", pendingIntent);

        notificationManager.notify(generateNotificationId(), builder.build());
    }

    // Notificación de ubicación actualizada
    public void showLocationUpdateNotification(String locationName, double lat, double lng) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("action", "show_location");
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = createPendingIntent(intent, NOTIFICATION_LOCATION_UPDATE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_LOCATION_UPDATES)
                .setSmallIcon(R.drawable.ic_location_update)
                .setContentTitle("📍 Ubicación Actualizada")
                .setContentText("Ahora estás en: " + locationName)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Tu ubicación ha sido actualizada:\n" +
                                "📍 " + locationName + "\n" +
                                "Coordenadas: " + String.format("%.4f, %.4f", lat, lng)))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true)
                .setOngoing(false);

        notificationManager.notify(NOTIFICATION_LOCATION_UPDATE, builder.build());
    }

    // Notificación de error
    public void showErrorNotification(String title, String message) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = createPendingIntent(intent, NOTIFICATION_GENERAL);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_GENERAL)
                .setSmallIcon(R.drawable.ic_error)
                .setContentTitle("⚠️ " + title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setColor(context.getResources().getColor(R.color.error_color));

        notificationManager.notify(generateNotificationId(), builder.build());
    }

    // Notificación de éxito
    public void showSuccessNotification(String title, String message) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = createPendingIntent(intent, NOTIFICATION_GENERAL + 1);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_GENERAL)
                .setSmallIcon(R.drawable.ic_success)
                .setContentTitle("✅ " + title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setColor(context.getResources().getColor(R.color.success_color));

        notificationManager.notify(generateNotificationId(), builder.build());
    }

    // Notificación con progreso (para operaciones largas)
    public void showProgressNotification(String title, String message, int progress, int maxProgress) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = createPendingIntent(intent, NOTIFICATION_GENERAL + 2);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_GENERAL)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle(title)
                .setContentText(message)
                .setProgress(maxProgress, progress, false)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true);

        notificationManager.notify(NOTIFICATION_GENERAL + 2, builder.build());
    }

    // Cancelar notificación de progreso
    public void cancelProgressNotification() {
        notificationManager.cancel(NOTIFICATION_GENERAL + 2);
    }

    // Crear PendingIntent
    private PendingIntent createPendingIntent(Intent intent, int requestCode) {
        int flag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                PendingIntent.FLAG_IMMUTABLE : 0;
        return PendingIntent.getActivity(context, requestCode, intent, flag);
    }

    // Generar ID único para notificaciones
    private int generateNotificationId() {
        return new Random().nextInt(10000) + 2000;
    }

    // Cancelar todas las notificaciones
    public void cancelAllNotifications() {
        notificationManager.cancelAll();
    }

    // Cancelar notificación específica
    public void cancelNotification(int notificationId) {
        notificationManager.cancel(notificationId);
    }
}


