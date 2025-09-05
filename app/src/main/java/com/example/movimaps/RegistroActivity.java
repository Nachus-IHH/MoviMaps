package com.example.movimaps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movimaps.util.AuthUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistroActivity extends AppCompatActivity {
    private static final String TAG = "RegistroActivity";
    //private DatabaseManager dbManager;

    // Referencias a las vistas
    private TextInputEditText etNombre;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;
    private Button btnRegistrar;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        // Configuración EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        inicializarVistas();
        inicializarBaseDatos();
    }

    private void inicializarVistas() {
        // Obtener referencias a las vistas usando los IDs correctos de tu layout
        etNombre = findViewById(R.id.textInputEditText);

        // Para los campos que están dentro de TextInputLayout, obtenerlos correctamente
        TextInputLayout emailLayout = findViewById(R.id.usernameLayout);
        TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        TextInputLayout confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);

        etEmail = (TextInputEditText) emailLayout.getEditText();
        etPassword = (TextInputEditText) passwordLayout.getEditText();
        etConfirmPassword = (TextInputEditText) confirmPasswordLayout.getEditText();

        btnRegistrar = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.txtLogin);

        // Configurar listeners
        btnRegistrar.setOnClickListener(v -> procesarRegistro());
        tvLogin.setOnClickListener(v -> volverALogin());
    }

    private void inicializarBaseDatos() {
        //dbManager = new DatabaseManager(this);
    }

    private void procesarRegistro() {
        String nombre = etNombre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validar campos
        if (!validarCampos(nombre, email, password, confirmPassword)) {
            return;
        }

        // Hashear la contraseña
        String passwordHash = AuthUtils.hashPassword(password);

        // Intentar crear la cuenta
        crearCuenta(nombre, email, passwordHash);
    }

    private boolean validarCampos(String nombre, String email, String password, String confirmPassword) {
        // Validar nombre
        if (!AuthUtils.esNombreValido(nombre)) {
            mostrarError("El nombre debe tener entre 2 y 50 caracteres y solo contener letras");
            etNombre.requestFocus();
            return false;
        }

        // Validar email
        if (!AuthUtils.esEmailValido(email)) {
            mostrarError("El formato del email no es válido");
            etEmail.requestFocus();
            return false;
        }

        // Validar contraseña
        if (!AuthUtils.esPasswordValida(password)) {
            mostrarError("La contraseña debe tener al menos 6 caracteres, incluyendo letras y números");
            etPassword.requestFocus();
            return false;
        }

        // Validar confirmación de contraseña
        if (!password.equals(confirmPassword)) {
            mostrarError("Las contraseñas no coinciden");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void crearCuenta(String nombre, String email, String passwordHash) {
        /*
        try {
            dbManager.open();

            // Verificar si el email ya existe
            if (dbManager.emailExiste(email)) {
                mostrarError("Este email ya está registrado");
                etEmail.requestFocus();
                return;
            }

            // Crear la cuenta
            long resultado = dbManager.crearCuenta(nombre, email, passwordHash);

            if (resultado != -1) {
                // Registro exitoso
                Log.i(TAG, "Cuenta creada exitosamente para: " + email);
                mostrarExito("¡Cuenta creada exitosamente!");
                volverALogin();
            } else {
                mostrarError("Error al crear la cuenta. Intenta nuevamente.");
            }

        } catch (Exception e) {
            Log.e(TAG, "Error durante el registro", e);
            mostrarError("Error al crear la cuenta. Intenta nuevamente.");
        } finally {
            if (dbManager != null) {
                dbManager.close();
            }
        }
         */
    }

    private void volverALogin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void mostrarExito(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        /*
        if (dbManager != null) {
            dbManager.close();
        }
         */
        super.onDestroy();
    }
}
