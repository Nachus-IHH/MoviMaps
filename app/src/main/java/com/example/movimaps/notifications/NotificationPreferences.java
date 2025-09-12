package com.example.movimaps.notifications;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreferences {

    private static final String PREF_NAME = "NotificationPreferences";
    private static final String KEY_MAP_EVENTS = "map_events_enabled";
    private static final String KEY_USER_EVENTS = "user_events_enabled";
    private static final String KEY_LOCATION_UPDATES = "location_updates_enabled";
    private static final String KEY_GENERAL = "general_enabled";
    private static final String KEY_SOUND = "sound_enabled";
    private static final String KEY_VIBRATION = "vibration_enabled";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public NotificationPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    // Getters
    public boolean isMapEventsEnabled() {
        return preferences.getBoolean(KEY_MAP_EVENTS, true);
    }

    public boolean isUserEventsEnabled() {
        return preferences.getBoolean(KEY_USER_EVENTS, true);
    }

    public boolean isLocationUpdatesEnabled() {
        return preferences.getBoolean(KEY_LOCATION_UPDATES, false);
    }

    public boolean isGeneralEnabled() {
        return preferences.getBoolean(KEY_GENERAL, true);
    }

    public boolean isSoundEnabled() {
        return preferences.getBoolean(KEY_SOUND, true);
    }

    public boolean isVibrationEnabled() {
        return preferences.getBoolean(KEY_VIBRATION, true);
    }

    // Setters
    public void setMapEventsEnabled(boolean enabled) {
        editor.putBoolean(KEY_MAP_EVENTS, enabled).apply();
    }

    public void setUserEventsEnabled(boolean enabled) {
        editor.putBoolean(KEY_USER_EVENTS, enabled).apply();
    }

    public void setLocationUpdatesEnabled(boolean enabled) {
        editor.putBoolean(KEY_LOCATION_UPDATES, enabled).apply();
    }

    public void setGeneralEnabled(boolean enabled) {
        editor.putBoolean(KEY_GENERAL, enabled).apply();
    }

    public void setSoundEnabled(boolean enabled) {
        editor.putBoolean(KEY_SOUND, enabled).apply();
    }

    public void setVibrationEnabled(boolean enabled) {
        editor.putBoolean(KEY_VIBRATION, enabled).apply();
    }
}