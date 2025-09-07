package com.example.movimaps.osmmap.ai;

import android.content.Context;
import android.util.Log;
import com.example.movimaps.sql.entity.Parada;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Predictor de tráfico usando machine learning simple
 */

public class TrafficPredictor {

    private static final String TAG = "TrafficPredictor";
    private Context context;
    private Map<String, TrafficPattern> trafficPatterns;

    public TrafficPredictor(Context context) {
        this.context = context;
        this.trafficPatterns = new ConcurrentHashMap<>();
        initializeTrafficPatterns();
    }

    /**
     * Predice el factor de tráfico para una ruta en un momento específico
     */
    public double predictTrafficFactor(List<Parada> paradas, int hora, int diaSemana) {
        try {
            double factorPromedio = 1.0;
            int contadorPatrones = 0;

            // Analizar cada segmento de la ruta
            for (int i = 0; i < paradas.size() - 1; i++) {
                String segmentoKey = generateSegmentKey(paradas.get(i), paradas.get(i + 1));
                TrafficPattern pattern = trafficPatterns.get(segmentoKey);

                if (pattern != null) {
                    double factor = pattern.getTrafficFactor(hora, diaSemana);
                    factorPromedio += factor;
                    contadorPatrones++;
                }
            }

            if (contadorPatrones > 0) {
                factorPromedio = factorPromedio / contadorPatrones;
            }

            // Aplicar factores generales por hora y día
            factorPromedio *= getGeneralTimeFactor(hora, diaSemana);

            Log.d(TAG, "Factor de tráfico predicho: " + factorPromedio);
            return Math.max(0.5, Math.min(3.0, factorPromedio)); // Limitar entre 0.5x y 3x

        } catch (Exception e) {
            Log.e(TAG, "Error prediciendo tráfico", e);
            return 1.0; // Factor neutro en caso de error
        }
    }

    /**
     * Inicializa patrones de tráfico base
     */
    private void initializeTrafficPatterns() {
        // Patrones base que se pueden mejorar con datos reales
        TrafficPattern patronCentro = new TrafficPattern();
        patronCentro.setHorasPico(Arrays.asList(7, 8, 9, 17, 18, 19));
        patronCentro.setFactorHoraPico(1.8);
        patronCentro.setFactorHoraNormal(1.0);
        patronCentro.setFactorFinDeSemana(0.7);

        TrafficPattern patronPeriferia = new TrafficPattern();
        patronPeriferia.setHorasPico(Arrays.asList(7, 8, 17, 18));
        patronPeriferia.setFactorHoraPico(1.4);
        patronPeriferia.setFactorHoraNormal(0.9);
        patronPeriferia.setFactorFinDeSemana(0.8);

        // Asignar patrones por defecto (en una implementación real,
        // esto vendría de análisis de datos históricos)
        trafficPatterns.put("default_centro", patronCentro);
        trafficPatterns.put("default_periferia", patronPeriferia);
    }

    /**
     * Genera clave única para un segmento de ruta
     */
    private String generateSegmentKey(Parada origen, Parada destino) {
        return origen.getIdParada() + "_" + destino.getIdParada();
    }

    /**
     * Obtiene factor general de tiempo basado en hora y día
     */
    private double getGeneralTimeFactor(int hora, int diaSemana) {
        double factor = 1.0;

        // Factor por hora del día
        if (hora >= 6 && hora <= 9) {
            factor *= 1.5; // Hora pico matutina
        } else if (hora >= 17 && hora <= 20) {
            factor *= 1.6; // Hora pico vespertina
        } else if (hora >= 22 || hora <= 5) {
            factor *= 0.7; // Horas nocturnas
        }

        // Factor por día de la semana (1=Lunes, 7=Domingo)
        if (diaSemana >= 6) { // Fin de semana
            factor *= 0.8;
        } else if (diaSemana == 1 || diaSemana == 5) { // Lunes y Viernes
            factor *= 1.1;
        }

        return factor;
    }

    /**
     * Aprende de datos de tráfico real para mejorar predicciones
     */
    public void learnFromRealTrafficData(String segmentoKey, int hora, int diaSemana,
                                         double tiempoReal, double tiempoEstimado) {
        try {
            double factorReal = tiempoReal / tiempoEstimado;

            TrafficPattern pattern = trafficPatterns.get(segmentoKey);
            if (pattern == null) {
                pattern = new TrafficPattern();
                trafficPatterns.put(segmentoKey, pattern);
            }

            // Actualizar patrón con nueva información
            pattern.updatePattern(hora, diaSemana, factorReal);

            Log.d(TAG, "Patrón de tráfico actualizado para segmento: " + segmentoKey);

        } catch (Exception e) {
            Log.e(TAG, "Error aprendiendo de datos de tráfico", e);
        }
    }

    /**
     * Clase interna para representar patrones de tráfico
     */
    private static class TrafficPattern {
        private List<Integer> horasPico = new ArrayList<>();
        private double factorHoraPico = 1.5;
        private double factorHoraNormal = 1.0;
        private double factorFinDeSemana = 0.8;
        private Map<String, Double> factoresEspecificos = new HashMap<>();

        public double getTrafficFactor(int hora, int diaSemana) {
            String key = hora + "_" + diaSemana;

            // Si tenemos un factor específico para esta hora/día, usarlo
            if (factoresEspecificos.containsKey(key)) {
                return factoresEspecificos.get(key);
            }

            // Usar patrones generales
            double factor = factorHoraNormal;

            if (horasPico.contains(hora)) {
                factor = factorHoraPico;
            }

            if (diaSemana >= 6) { // Fin de semana
                factor *= factorFinDeSemana;
            }

            return factor;
        }

        public void updatePattern(int hora, int diaSemana, double factorReal) {
            String key = hora + "_" + diaSemana;

            // Promedio ponderado con datos existentes
            Double factorExistente = factoresEspecificos.get(key);
            if (factorExistente != null) {
                double nuevoFactor = (factorExistente * 0.7) + (factorReal * 0.3);
                factoresEspecificos.put(key, nuevoFactor);
            } else {
                factoresEspecificos.put(key, factorReal);
            }
        }

        // Getters y setters
        public void setHorasPico(List<Integer> horasPico) { this.horasPico = horasPico; }
        public void setFactorHoraPico(double factorHoraPico) { this.factorHoraPico = factorHoraPico; }
        public void setFactorHoraNormal(double factorHoraNormal) { this.factorHoraNormal = factorHoraNormal; }
        public void setFactorFinDeSemana(double factorFinDeSemana) { this.factorFinDeSemana = factorFinDeSemana; }
    }


}
