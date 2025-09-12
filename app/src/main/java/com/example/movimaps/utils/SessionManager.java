package com.example.movimaps.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USUARIO = "usuario";
    private static final String KEY_CORREO = "correo";
    private static final String KEY_NOMBRE_COMPLETO = "nombreCompleto";
    private static final String KEY_TIPO_USUARIO = "tipoUsuario"; // NUEVO: Para roles

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // MODIFICADO: Crear sesión con roles
    public void createLoginSession(int userId, String usuario, String correo,
                                   String nombreCompleto, String tipoUsuario) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USUARIO, usuario);
        editor.putString(KEY_CORREO, correo);
        editor.putString(KEY_NOMBRE_COMPLETO, nombreCompleto);
        editor.putString(KEY_TIPO_USUARIO, tipoUsuario); // NUEVO
        editor.commit();
    }

    // Métodos existentes...
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public int getUserId() {
        return pref.getInt(KEY_USER_ID, -1);
    }

    public String getUsuario() {
        return pref.getString(KEY_USUARIO, "");
    }

    public String getCorreo() {
        return pref.getString(KEY_CORREO, "");
    }

    public String getNombreCompleto() {
        return pref.getString(KEY_NOMBRE_COMPLETO, "");
    }

    // NUEVOS: Métodos para roles
    public String getTipoUsuario() {
        return pref.getString(KEY_TIPO_USUARIO, "USER");
    }

    public boolean isAdmin() {
        return "ADMIN".equals(getTipoUsuario());
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}