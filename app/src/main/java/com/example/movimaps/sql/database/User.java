package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "User")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_user")
    private int idUser;

    private String nombre;
    private String paterno;
    private String materno;

    @ColumnInfo(name = "fecha_nacimiento")
    private String fechaNacimiento;

    private String usuario;
    private String correo;
    private String password;
    private String tipo; // ENUM como String
    private String telefono;

    // Constructor
    public User() {}

    public User(String nombre, String paterno, String materno, String fechaNacimiento,
                String usuario, String correo, String password, String tipo, String telefono) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.correo = correo;
        this.password = password;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPaterno() { return paterno; }
    public void setPaterno(String paterno) { this.paterno = paterno; }

    public String getMaterno() { return materno; }
    public void setMaterno(String materno) { this.materno = materno; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    // Método helper para nombre completo
    public String getNombreCompleto() {
        return nombre + " " + paterno + " " + materno;
    }
}

