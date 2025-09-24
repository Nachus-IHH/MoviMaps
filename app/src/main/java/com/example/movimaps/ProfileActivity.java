package com.example.movimaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.bumptech.glide.Glide;
import com.example.movimaps.sql.database.AppDatabase;
import com.example.movimaps.sql.database.User;
import com.example.movimaps.utils.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView ivProfileImage;
    private ImageView ivEditPhoto;
    private EditText etFullName, etUsername, etEmail, etPhone, etBio;
    private Button btnSaveProfile, btnLogout;

    private AppDatabase database;
    private SessionManager sessionManager;
    private User currentUser;

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 102;
    private static final int REQUEST_STORAGE_PERMISSION = 103;

    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        initDatabase();
        loadUserData();
        setupClickListeners();
    }

    private void initViews() {
        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivEditPhoto = findViewById(R.id.ivEditPhoto);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etBio = findViewById(R.id.etBio);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void initDatabase() {
        database = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);
    }

    private void loadUserData() {
        int userId = sessionManager.getUserId();
        if (userId != -1) {
            currentUser = database.userDao().getUserById(userId);
            if (currentUser != null) {
                populateUserData();
            }
        }
    }

    private void populateUserData() {
        etFullName.setText(currentUser.getFullName());
        etUsername.setText(currentUser.getUsername());
        etEmail.setText(currentUser.getEmail());
        etPhone.setText(currentUser.getPhone());
        etBio.setText(currentUser.getBio());

        // Cargar imagen de perfil
        if (currentUser.getProfileImagePath() != null && !currentUser.getProfileImagePath().isEmpty()) {
            File imageFile = new File(currentUser.getProfileImagePath());
            if (imageFile.exists()) {
                Glide.with(this)
                        .load(imageFile)
                        .placeholder(R.drawable.ic_default_avatar)
                        .error(R.drawable.ic_default_avatar)
                        .into(ivProfileImage);
            }
        }
    }

    private void setupClickListeners() {
        ivEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar imagen");
        builder.setItems(new String[]{"Cámara", "Galería"}, (dialog, which) -> {
            if (which == 0) {
                checkCameraPermission();
            } else {
                checkStoragePermission();
            }
        });
        builder.show();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        } else {
            openGallery();
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.osmmap.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                // Imagen desde cámara
                if (currentPhotoPath != null) {
                    File imageFile = new File(currentPhotoPath);
                    if (imageFile.exists()) {
                        Glide.with(this)
                                .load(imageFile)
                                .into(ivProfileImage);
                    }
                }
            } else if (requestCode == REQUEST_GALLERY) {
                // Imagen desde galería
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        currentPhotoPath = saveImageToInternalStorage(bitmap);
                        Glide.with(this)
                                .load(selectedImageUri)
                                .into(ivProfileImage);
                    } catch (IOException e) {
                        Toast.makeText(this, "Error al cargar imagen", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String saveImageToInternalStorage(Bitmap bitmap) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "profile_" + timeStamp + ".jpg";
            File directory = new File(getFilesDir(), "profile_images");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File imageFile = new File(directory, imageFileName);
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();

            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_CAMERA_PERMISSION) {
                openCamera();
            } else if (requestCode == REQUEST_STORAGE_PERMISSION) {
                openGallery();
            }
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfile() {
        String fullName = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String bio = etBio.getText().toString().trim();

        // Validaciones básicas
        if (fullName.isEmpty()) {
            etFullName.setError("Ingrese su nombre completo");
            etFullName.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            etUsername.setError("Ingrese un nombre de usuario");
            etUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Ingrese su email");
            etEmail.requestFocus();
            return;
        }

        // Verificar si el username ya existe (excepto el actual)
        User existingUser = database.userDao().getUserByUsername(username);
        if (existingUser != null && existingUser.getId() != currentUser.getId()) {
            etUsername.setError("Este nombre de usuario ya existe");
            etUsername.requestFocus();
            return;
        }

        // Actualizar datos del usuario
        currentUser.setFullName(fullName);
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setBio(bio);
        currentUser.setUpdatedAt(System.currentTimeMillis());

        if (currentPhotoPath != null) {
            currentUser.setProfileImagePath(currentPhotoPath);
        }

        database.userDao().updateUser(currentUser);

        // Actualizar sesión
        sessionManager.createLoginSession(currentUser.getId(), currentUser.getUsername(),
                currentUser.getEmail(), currentUser.getFullName());

        Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Estás seguro de que quieres cerrar sesión?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            sessionManager.logout();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}