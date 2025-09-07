package com.example.movimaps.osmmap.ai;

import com.example.movimaps.sql.entity.Ruta;
import com.example.movimaps.sql.entity.Parada;
import java.util.List;

/**
 * Representa una ruta optimizada con métricas de IA
 */
public class OptimizedRoute {
    private Ruta rutaOriginal;
    private List<Parada> paradas;
    private double distanciaTotal;
    private double tiempoEstimado;
    private double costoEstimado;
    private double nivelComodidad;
    private double confiabilidad;
    private double factorTrafico;
    private double puntuacionTotal;

    // Métricas normalizadas para comparación
    private double tiempoNormalizado;
    private double costoNormalizado;
    private double distanciaNormalizada;

    // Información adicional de IA
    private String razonOptimizacion;
    private double confianzaPrediccion;
    private List<String> alternativasConsideradas;

    // Constructor vacío
    public OptimizedRoute() {}

    // Getters y Setters
    public Ruta getRutaOriginal() { return rutaOriginal; }
    public void setRutaOriginal(Ruta rutaOriginal) { this.rutaOriginal = rutaOriginal; }

    public List<Parada> getParadas() { return paradas; }
    public void setParadas(List<Parada> paradas) { this.paradas = paradas; }

    public double getDistanciaTotal() { return distanciaTotal; }
    public void setDistanciaTotal(double distanciaTotal) { this.distanciaTotal = distanciaTotal; }

    public double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }

    public double getCostoEstimado() { return costoEstimado; }
    public void setCostoEstimado(double costoEstimado) { this.costoEstimado = costoEstimado; }

    public double getNivelComodidad() { return nivelComodidad; }
    public void setNivelComodidad(double nivelComodidad) { this.nivelComodidad = nivelComodidad; }

    public double getConfiabilidad() { return confiabilidad; }
    public void setConfiabilidad(double confiabilidad) { this.confiabilidad = confiabilidad; }

    public double getFactorTrafico() { return factorTrafico; }
    public void setFactorTrafico(double factorTrafico) { this.factorTrafico = factorTrafico; }

    public double getPuntuacionTotal() { return puntuacionTotal; }
    public void setPuntuacionTotal(double puntuacionTotal) { this.puntuacionTotal = puntuacionTotal; }

    public double getTiempoNormalizado() { return tiempoNormalizado; }
    public void setTiempoNormalizado(double tiempoNormalizado) { this.tiempoNormalizado = tiempoNormalizado; }

    public double getCostoNormalizado() { return costoNormalizado; }
    public void setCostoNormalizado(double costoNormalizado) { this.costoNormalizado = costoNormalizado; }

    public double getDistanciaNormalizada() { return distanciaNormalizada; }
    public void setDistanciaNormalizada(double distanciaNormalizada) { this.distanciaNormalizada = distanciaNormalizada; }

    public String getRazonOptimizacion() { return razonOptimizacion; }
    public void setRazonOptimizacion(String razonOptimizacion) { this.razonOptimizacion = razonOptimizacion; }

    public double getConfianzaPrediccion() { return confianzaPrediccion; }
    public void setConfianzaPrediccion(double confianzaPrediccion) { this.confianzaPrediccion = confianzaPrediccion; }

    public List<String> getAlternativasConsideradas() { return alternativasConsideradas; }
    public void setAlternativasConsideradas(List<String> alternativasConsideradas) {
        this.alternativasConsideradas = alternativasConsideradas;
    }

    /**
     * Obtiene una descripción legible de la optimización
     */
    public String getDescripcionOptimizacion() {
        StringBuilder desc = new StringBuilder();
        desc.append("Ruta ").append(rutaOriginal.getNoRuta());
        desc.append(" - Tiempo: ").append(String.format("%.0f min", tiempoEstimado));
        desc.append(", Costo: $").append(String.format("%.2f", costoEstimado));
        desc.append(", Comodidad: ").append(String.format("%.0f%%", nivelComodidad * 100));

        if (factorTrafico > 1.2) {
            desc.append(" (Tráfico alto)");
        } else if (factorTrafico < 0.8) {
            desc.append(" (Tráfico ligero)");
        }

        return desc.toString();
    }

    /**
     * Calcula la eficiencia general de la ruta (0-1)
     */
    public double getEficienciaGeneral() {
        // Combinar todas las métricas en una puntuación de eficiencia
        double eficiencia = 0.0;
        eficiencia += (1.0 - tiempoNormalizado) * 0.3; // Menos tiempo es mejor
        eficiencia += (1.0 - costoNormalizado) * 0.2;  // Menos costo es mejor
        eficiencia += nivelComodidad * 0.2;            // Más comodidad es mejor
        eficiencia += confiabilidad * 0.2;             // Más confiabilidad es mejor
        eficiencia += (1.0 - distanciaNormalizada) * 0.1; // Menos distancia es mejor

        return Math.max(0.0, Math.min(1.0, eficiencia));
    }


}
