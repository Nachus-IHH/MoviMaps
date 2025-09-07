package com.example.movimaps.osmmap.ai;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.movimaps.sql.entity.Parada;
import com.example.movimaps.sql.entity.Ruta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Modelo de Machine Learning simple para predicción de tiempos de viaje
 */
public class RouteMLModel {

    private static final String TAG = "RouteMLModel";
    private static final String PREFS_NAME = "route_ml_model";
    private static final String KEY_MODEL_DATA = "model_data";

    private Context context;
    private SharedPreferences prefs;
    private Map<String, RouteFeatures> routeFeatures;
    private LinearRegressionModel model;

    public RouteMLModel(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.routeFeatures = new HashMap<>();
        this.model = new LinearRegressionModel();
        loadModelData();
    }

    /**
     * Predice el tiempo de viaje usando el modelo ML
     */
    public double predictTravelTime(Ruta ruta, Parada origen, Parada destino,
                                    RouteOptimizationParams params) {
        try {
            // Extraer características de la ruta
            double[] features = extractFeatures(ruta, origen, destino, params);

            // Hacer predicción
            double tiempoPredicho = model.predict(features);

            Log.d(TAG, "Tiempo predicho por ML: " + tiempoPredicho + " minutos");
            return Math.max(5.0, tiempoPredicho); // Mínimo 5 minutos

        } catch (Exception e) {
            Log.e(TAG, "Error en predicción ML", e);
            // Fallback a cálculo básico
            return calculateBasicTravelTime(ruta, origen, destino);
        }
    }

    /**
     * Aprende de la selección de ruta del usuario para mejorar el modelo
     */
    public void learnFromRouteSelection(OptimizedRoute rutaSeleccionada,
                                        RouteOptimizationParams params) {
        try {
            if (rutaSeleccionada == null) return;

            String routeKey = generateRouteKey(rutaSeleccionada.getRutaOriginal());
            RouteFeatures features = routeFeatures.get(routeKey);

            if (features == null) {
                features = new RouteFeatures();
                routeFeatures.put(routeKey, features);
            }

            // Actualizar características basadas en la selección
            features.incrementSelectionCount();
            features.updateAverageScore(rutaSeleccionada.getPuntuacionTotal());
            features.updateUserPreferences(params);

            // Reentrenar modelo con nuevos datos
            retrainModel();

            // Guardar datos del modelo
            saveModelData();

            Log.d(TAG, "Modelo actualizado con nueva selección de ruta");

        } catch (Exception e) {
            Log.e(TAG, "Error aprendiendo de selección de ruta", e);
        }
    }

    /**
     * Extrae características numéricas de una ruta para el modelo ML
     */
    private double[] extractFeatures(Ruta ruta, Parada origen, Parada destino,
                                     RouteOptimizationParams params) {
        // Vector de características: [distancia, hora, día, tipo_transporte, num_paradas, preferencias_usuario]
        double[] features = new double[10];

        // Característica 0: Distancia estimada
        features[0] = calculateDistance(origen, destino);

        // Característica 1: Hora del día (normalizada 0-1)
        features[1] = params.getHoraViaje() / 24.0;

        // Característica 2: Día de la semana (normalizada 0-1)
        features[2] = params.getDiaSemana() / 7.0;

        // Característica 3: Tipo de transporte (codificado)
        features[3] = encodeTransportType(ruta.getTipoTransporte());

        // Característica 4: Número de paradas estimado
        features[4] = Math.log(Math.max(1, ruta.getNoRuta())); // Log para normalizar

        // Características 5-9: Preferencias del usuario (pesos)
        features[5] = params.getPesoTiempo();
        features[6] = params.getPesoCosto();
        features[7] = params.getPesoComodidad();
        features[8] = params.getPesoConfiabilidad();
        features[9] = params.getPesoDistancia();

        return features;
    }

    /**
     * Codifica el tipo de transporte como valor numérico
     */
    private double encodeTransportType(String tipoTransporte) {
        if (tipoTransporte == null) return 0.5;

        switch (tipoTransporte.toLowerCase()) {
            case "metro": return 1.0;
            case "bus": return 0.7;
            case "microbus": return 0.4;
            case "taxi": return 0.9;
            default: return 0.5;
        }
    }

    /**
     * Calcula distancia entre dos paradas
     */
    private double calculateDistance(Parada p1, Parada p2) {
        double lat1 = Math.toRadians(p1.getLat());
        double lon1 = Math.toRadians(p1.getLng());
        double lat2 = Math.toRadians(p2.getLat());
        double lon2 = Math.toRadians(p2.getLng());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat/2) * Math.sin(dlat/2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dlon/2) * Math.sin(dlon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return 6371 * c; // Radio de la Tierra en km
    }

    /**
     * Cálculo básico de tiempo de viaje como fallback
     */
    private double calculateBasicTravelTime(Ruta ruta, Parada origen, Parada destino) {
        double distancia = calculateDistance(origen, destino);
        double velocidadPromedio = 25.0; // km/h
        return (distancia / velocidadPromedio) * 60; // Convertir a minutos
    }

    /**
     * Genera clave única para una ruta
     */
    private String generateRouteKey(Ruta ruta) {
        return "ruta_" + ruta.getIdRuta();
    }

    /**
     * Reentrena el modelo con los datos acumulados
     */
    private void retrainModel() {
        try {
            List<double[]> trainingFeatures = new ArrayList<>();
            List<Double> trainingTargets = new ArrayList<>();

            // Preparar datos de entrenamiento
            for (Map.Entry<String, RouteFeatures> entry : routeFeatures.entrySet()) {
                RouteFeatures features = entry.getValue();
                if (features.getSelectionCount() > 0) {
                    // Usar características históricas como entrada
                    double[] featureVector = features.toFeatureVector();
                    double target = features.getAverageScore();

                    trainingFeatures.add(featureVector);
                    trainingTargets.add(target);
                }
            }

            // Entrenar modelo si tenemos suficientes datos
            if (trainingFeatures.size() >= 5) {
                model.train(trainingFeatures, trainingTargets);
                Log.d(TAG, "Modelo reentrenado con " + trainingFeatures.size() + " muestras");
            }

        } catch (Exception e) {
            Log.e(TAG, "Error reentrenando modelo", e);
        }
    }

    /**
     * Guarda los datos del modelo en SharedPreferences
     */
    private void saveModelData() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(routeFeatures);
            prefs.edit().putString(KEY_MODEL_DATA, json).apply();
        } catch (Exception e) {
            Log.e(TAG, "Error guardando datos del modelo", e);
        }
    }

    /**
     * Carga los datos del modelo desde SharedPreferences
     */
    private void loadModelData() {
        try {
            String json = prefs.getString(KEY_MODEL_DATA, "{}");
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, RouteFeatures>>(){}.getType();
            Map<String, RouteFeatures> loaded = gson.fromJson(json, type);

            if (loaded != null) {
                routeFeatures = loaded;
                Log.d(TAG, "Datos del modelo cargados: " + routeFeatures.size() + " rutas");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error cargando datos del modelo", e);
            routeFeatures = new HashMap<>();
        }
    }

    /**
     * Clase para almacenar características de rutas
     */
    private static class RouteFeatures {
        private int selectionCount = 0;
        private double averageScore = 0.0;
        private double avgTimeWeight = 0.3;
        private double avgCostWeight = 0.2;
        private double avgComfortWeight = 0.2;
        private double avgReliabilityWeight = 0.2;
        private double avgDistanceWeight = 0.1;

        public void incrementSelectionCount() {
            selectionCount++;
        }

        public void updateAverageScore(double newScore) {
            averageScore = (averageScore * (selectionCount - 1) + newScore) / selectionCount;
        }

        public void updateUserPreferences(RouteOptimizationParams params) {
            double factor = 1.0 / selectionCount;
            avgTimeWeight = avgTimeWeight * (1 - factor) + params.getPesoTiempo() * factor;
            avgCostWeight = avgCostWeight * (1 - factor) + params.getPesoCosto() * factor;
            avgComfortWeight = avgComfortWeight * (1 - factor) + params.getPesoComodidad() * factor;
            avgReliabilityWeight = avgReliabilityWeight * (1 - factor) + params.getPesoConfiabilidad() * factor;
            avgDistanceWeight = avgDistanceWeight * (1 - factor) + params.getPesoDistancia() * factor;
        }

        public double[] toFeatureVector() {
            return new double[]{
                    selectionCount, averageScore, avgTimeWeight, avgCostWeight,
                    avgComfortWeight, avgReliabilityWeight, avgDistanceWeight
            };
        }

        // Getters
        public int getSelectionCount() { return selectionCount; }
        public double getAverageScore() { return averageScore; }
    }

    /**
     * Modelo de regresión lineal simple
     */
    private static class LinearRegressionModel {
        private double[] weights;
        private double bias = 0.0;
        private boolean trained = false;

        public void train(List<double[]> features, List<Double> targets) {
            if (features.isEmpty()) return;

            int numFeatures = features.get(0).length;
            weights = new double[numFeatures];

            // Inicializar pesos aleatoriamente
            Random random = new Random();
            for (int i = 0; i < numFeatures; i++) {
                weights[i] = random.nextGaussian() * 0.1;
            }

            // Entrenamiento simple con gradiente descendente
            double learningRate = 0.01;
            int epochs = 100;

            for (int epoch = 0; epoch < epochs; epoch++) {
                for (int i = 0; i < features.size(); i++) {
                    double[] x = features.get(i);
                    double y = targets.get(i);

                    double prediction = predict(x);
                    double error = y - prediction;

                    // Actualizar pesos
                    for (int j = 0; j < weights.length; j++) {
                        weights[j] += learningRate * error * x[j];
                    }
                    bias += learningRate * error;
                }
            }

            trained = true;
        }

        public double predict(double[] features) {
            if (!trained || weights == null) {
                // Predicción por defecto basada en características básicas
                return Math.max(10.0, features[0] * 2.0 + features[1] * 30.0); // distancia * 2 + hora factor
            }

            double prediction = bias;
            for (int i = 0; i < Math.min(features.length, weights.length); i++) {
                prediction += weights[i] * features[i];
            }

            return Math.max(5.0, prediction); // Mínimo 5 minutos
        }
    }


}
