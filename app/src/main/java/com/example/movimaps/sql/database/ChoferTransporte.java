package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;

@Entity(tableName = "Chofer_Transporte",
        foreignKeys = @ForeignKey(entity = Transporte.class,
                parentColumns = "id_transporte",
                childColumns = "Transporte_id_transporte",
                onDelete = ForeignKey.CASCADE))
public class ChoferTransporte {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_chofer")
    private int idChofer;

    private String nombre;
    private String paterno;
    private String materno;

    @ColumnInfo(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @ColumnInfo(name = "Transporte_id_transporte")
    private int transporteIdTransporte; // FK

    // Constructor
    public ChoferTransporte() {}

    public ChoferTransporte(String nombre, String paterno, String materno,
                            String fechaNacimiento, int transporteIdTransporte) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.fechaNacimiento = fechaNacimiento;
        this.transporteIdTransporte = transporteIdTransporte;
    }

    // Getters y Setters
    public int getIdChofer() { return idChofer; }
    public void setIdChofer(int idChofer) { this.idChofer = idChofer; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPaterno() { return paterno; }
    public void setPaterno(String paterno) { this.paterno = paterno; }

    public String getMaterno() { return materno; }
    public void setMaterno(String materno) { this.materno = materno; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public int getTransporteIdTransporte() { return transporteIdTransporte; }
    public void setTransporteIdTransporte(int transporteIdTransporte) { this.transporteIdTransporte = transporteIdTransporte; }

    // Método helper para nombre completo
    public String getNombreCompleto() {
        return nombre + " " + paterno + " " + materno;
    }
}
