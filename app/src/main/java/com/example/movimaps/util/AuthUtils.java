package com.example.movimaps.util;

import android.util.Patterns;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilidades para autenticación y validación
 */
public class AuthUtils {

    /**
     * Validar formato de email
     * @param email Email a validar
     * @return true si el formato es válido
     */
    public static boolean esEmailValido(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validar fortaleza de contraseña
     * @param password Contraseña a validar
     * @return true si la contraseña es suficientemente fuerte
     */
    public static boolean esPasswordValida(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }

        // Verificar que tenga al menos una letra y un número
        boolean tieneLetra = password.matches(".*[a-zA-Z].*");
        boolean tieneNumero = password.matches(".*\\d.*");

        return tieneLetra && tieneNumero;
    }

    /**
     * Hash de contraseña usando SHA-256
     * NOTA: En producción, usa bcrypt o similar
     * @param password Contraseña en texto plano
     * @return Hash de la contraseña
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }

    /**
     * Validar nombre de usuario
     * @param nombre Nombre a validar
     * @return true si el nombre es válido
     */
    public static boolean esNombreValido(String nombre) {
        return nombre != null &&
                nombre.trim().length() >= 2 &&
                nombre.trim().length() <= 50 &&
                nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }
}
