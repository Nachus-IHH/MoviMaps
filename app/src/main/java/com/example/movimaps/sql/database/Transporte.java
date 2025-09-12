package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;

@Entity(tableName = "Transporte",
        foreignKeys = {
                @ForeignKey(entity = Ruta.class,
                        parentColumns = "id_ruta",
                        childColumns = "Ruta_id_ruta",
                        onDelete = ForeignKey.CASCADE)
        })
public class Transporte {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_transporte")
    private int idTransporte;

    private String unidad;
    private String estado; // ENUM como String
    private boolean accesibilidad; // TINYINT(1)

    @ColumnInfo(name = "max_pasajeros")
    private int maxPasajeros; // SMALLINT

    @ColumnInfo(name = "Ruta_id_ruta")
    private int rutaIdRuta; // FK

    @ColumnInfo(name = "Empresa_id_empresa")
    private int empresaIdEmpresa; // FK

    @ColumnInfo(name = "Tipo_Transporte_id_tipo")
    private int tipoTransporteIdTipo; // FK

    // Constructor
    public Transporte() {}

    public Transporte(String unidad, String estado, boolean accesibilidad, int maxPasajeros,
                      int rutaIdRuta, int empresaIdEmpresa, int tipoTransporteIdTipo) {
        this.unidad = unidad;
        this.estado = estado;
        this.accesibilidad = accesibilidad;
        this.maxPasajeros = maxPasajeros;
        this.rutaIdRuta = rutaIdRuta;
        this.empresaIdEmpresa = empresaIdEmpresa;
        this.tipoTransporteIdTipo = tipoTransporteIdTipo;
    }

    // Getters y Setters
    public int getIdTransporte() { return idTransporte; }
    public void setIdTransporte(int idTransporte) { this.idTransporte = idTransporte; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean isAccesibilidad() { return accesibilidad; }
    public void setAccesibilidad(boolean accesibilidad) { this.accesibilidad = accesibilidad; }

    public int getMaxPasajeros() { return maxPasajeros; }
    public void setMaxPasajeros(int maxPasajeros) { this.maxPasajeros = maxPasajeros; }

    public int getRutaIdRuta() { return rutaIdRuta; }
    public void setRutaIdRuta(int rutaIdRuta) { this.rutaIdRuta = rutaIdRuta; }

    public int getEmpresaIdEmpresa() { return empresaIdEmpresa; }
    public void setEmpresaIdEmpresa(int empresaIdEmpresa) { this.empresaIdEmpresa = empresaIdEmpresa; }

    public int getTipoTransporteIdTipo() { return tipoTransporteIdTipo; }
    public void setTipoTransporteIdTipo(int tipoTransporteIdTipo) { this.tipoTransporteIdTipo = tipoTransporteIdTipo; }
}