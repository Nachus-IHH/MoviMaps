package com.example.movimaps.osmmap.ai;
import java.util.List;



/**
 * Resultado de la optimización de rutas con IA
 */
public class OptimizedRouteResult {

    private OptimizedRoute mejorRuta;
    private List<OptimizedRoute> alternativas;
    private String explicacion;
    private double confianzaTotal;
    private long tiempoCalculoMs;

    public OptimizedRouteResult(OptimizedRoute mejorRuta, List<OptimizedRoute> alternativas) {
        this.mejorRuta = mejorRuta;
        this.alternativas = alternativas;
        this.tiempoCalculoMs = System.currentTimeMillis();
        generateExplicacion();
        calculateConfianza();
    }

    /**
     * Genera explicación de por qué se seleccionó esta ruta
     */
    private void generateExplicacion() {
        if (mejorRuta == null) {
            explicacion = "No se encontraron rutas válidas";
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Ruta ").append(mejorRuta.getRutaOriginal().getNoRuta())
                .append(" seleccionada por ");

        // Identificar el factor más importante
        double maxFactor = 0;
        String razonPrincipal = "";

        if (mejorRuta.getNivelComodidad() > maxFactor) {
            maxFactor = mejorRuta.getNivelComodidad();
            razonPrincipal = "alta comodidad";
        }

        if (mejorRuta.getConfiabilidad() > maxFactor) {
            maxFactor = mejorRuta.getConfiabilidad();
            razonPrincipal = "alta confiabilidad";
        }

        if (1.0 - mejorRuta.getTiempoNormalizado() > maxFactor) {
            razonPrincipal = "menor tiempo de viaje";
        }

        if (1.0 - mejorRuta.getCostoNormalizado() > maxFactor) {
            razonPrincipal = "menor costo";
        }

        sb.append(razonPrincipal);

        // Agregar información adicional
        if (mejorRuta.getFactorTrafico() > 1.2) {
            sb.append(". Considera tráfico alto actual");
        } else if (mejorRuta.getFactorTrafico() < 0.8) {
            sb.append(". Aprovecha tráfico ligero actual");
        }

        explicacion = sb.toString();
    }

    /**
     * Calcula la confianza total del resultado
     */
    private void calculateConfianza() {
        if (mejorRuta == null) {
            confianzaTotal = 0.0;
            return;
        }

        // Base de confianza en la predicción
        confianzaTotal = mejorRuta.getConfianzaPrediccion();

        // Ajustar por número de alternativas consideradas
        if (alternativas != null && alternativas.size() > 1) {
            confianzaTotal += 0.1; // Más opciones = más confianza
        }

        // Ajustar por diferencia con segunda mejor opción
        if (alternativas != null && alternativas.size() > 1) {
            OptimizedRoute segundaMejor = alternativas.get(1);
            double diferencia = mejorRuta.getPuntuacionTotal() - segundaMejor.getPuntuacionTotal();
            if (diferencia > 0.2) {
                confianzaTotal += 0.1; // Clara diferencia = más confianza
            }
        }

        confianzaTotal = Math.max(0.0, Math.min(1.0, confianzaTotal));
    }

    /**
     * Obtiene resumen del resultado
     */
    public String getResumen() {
        if (mejorRuta == null) {
            return "No se encontraron rutas disponibles";
        }

        return String.format("Mejor ruta: %s (%.0f min, $%.2f) - Confianza: %.0f%%",
                mejorRuta.getRutaOriginal().getNoRuta(),
                mejorRuta.getTiempoEstimado(),
                mejorRuta.getCostoEstimado(),
                confianzaTotal * 100);
    }

    /**
     * Verifica si el resultado es confiable
     */
    public boolean esConfiable(double umbralMinimo) {
        return confianzaTotal >= umbralMinimo;
    }

    // Getters y Setters
    public OptimizedRoute getMejorRuta() { return mejorRuta; }
    public void setMejorRuta(OptimizedRoute mejorRuta) { this.mejorRuta = mejorRuta; }

    public List<OptimizedRoute> getAlternativas() { return alternativas; }
    public void setAlternativas(List<OptimizedRoute> alternativas) { this.alternativas = alternativas; }

    public String getExplicacion() { return explicacion; }
    public void setExplicacion(String explicacion) { this.explicacion = explicacion; }

    public double getConfianzaTotal() { return confianzaTotal; }
    public void setConfianzaTotal(double confianzaTotal) { this.confianzaTotal = confianzaTotal; }

    public long getTiempoCalculoMs() { return tiempoCalculoMs; }
    public void setTiempoCalculoMs(long tiempoCalculoMs) { this.tiempoCalculoMs = tiempoCalculoMs; }



}
