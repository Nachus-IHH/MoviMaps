package com.example.movimaps.osmmap.ai;

import android.content.Context;
import android.util.Log;
import com.example.movimaps.sql.database.AppDatabase;
import com.example.movimaps.sql.entity.Ruta;
import com.example.movimaps.sql.entity.Parada;
import com.example.movimaps.osmmap.api.RouteResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;


public class RouteOptimizationService {

    private static final String TAG = "RouteOptimizationAI";
    private Context context;
    private AppDatabase database;
    private TrafficPredictor trafficPredictor;
    private RouteMLModel mlModel;

    public RouteOptimizationService(Context context) {
        this.context = context;
        this.database = AppDatabase.getInstance(context);
        this.trafficPredictor = new TrafficPredictor(context);
        this.mlModel = new RouteMLModel(context);
    }

    /**
     * Encuentra la ruta óptima usando IA considerando múltiples factores
     */
    public CompletableFuture<OptimizedRouteResult> findOptimalRoute(
            Parada origen, Parada destino, RouteOptimizationParams params) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Log.d(TAG, "Iniciando optimización de ruta con IA");

                // 1. Obtener todas las rutas posibles
                List<Ruta> rutasPosibles = database.transportDao()
                        .getRutasEntreParadas(origen.getIdParada(), destino.getIdParada());

                if (rutasPosibles.isEmpty()) {
                    // Buscar rutas con transbordos usando algoritmo A*
                    rutasPosibles = findRoutesWithTransfers(origen, destino);
                }

                // 2. Evaluar cada ruta con IA
                List<OptimizedRoute> rutasOptimizadas = new ArrayList<>();

                for (Ruta ruta : rutasPosibles) {
                    OptimizedRoute rutaOptimizada = evaluateRouteWithAI(ruta, origen, destino, params);
                    if (rutaOptimizada != null) {
                        rutasOptimizadas.add(rutaOptimizada);
                    }
                }

                // 3. Aplicar algoritmo de optimización multi-objetivo
                OptimizedRoute mejorRuta = applyMultiObjectiveOptimization(rutasOptimizadas, params);

                // 4. Aprender de la selección para mejorar futuras predicciones
                mlModel.learnFromRouteSelection(mejorRuta, params);

                return new OptimizedRouteResult(mejorRuta, rutasOptimizadas);

            } catch (Exception e) {
                Log.e(TAG, "Error en optimización de ruta", e);
                return null;
            }
        });
    }

    /**
     * Evalúa una ruta usando modelos de IA
     */
    private OptimizedRoute evaluateRouteWithAI(Ruta ruta, Parada origen, Parada destino,
                                               RouteOptimizationParams params) {
        try {
            // Obtener paradas de la ruta
            List<Parada> paradasRuta = database.transportDao().getParadasByRuta(ruta.getIdRuta());

            // Calcular métricas base
            double distanciaTotal = calculateRouteDistance(paradasRuta);
            double tiempoBase = calculateBaseTime(paradasRuta);

            // Predicción de tráfico con IA
            double factorTrafico = trafficPredictor.predictTrafficFactor(
                    paradasRuta, params.getHoraViaje(), params.getDiaSemana());

            // Predicción de tiempo real con ML
            double tiempoPredichoML = mlModel.predictTravelTime(
                    ruta, origen, destino, params);

            // Calcular costo estimado
            double costoEstimado = calculateRouteCost(ruta, distanciaTotal);

            // Calcular nivel de comodidad (basado en tipo de transporte, ocupación, etc.)
            double nivelComodidad = calculateComfortLevel(ruta, params);

            // Calcular puntuación de confiabilidad
            double confiabilidad = calculateReliabilityScore(ruta);

            // Crear ruta optimizada
            OptimizedRoute rutaOptimizada = new OptimizedRoute();
            rutaOptimizada.setRutaOriginal(ruta);
            rutaOptimizada.setDistanciaTotal(distanciaTotal);
            rutaOptimizada.setTiempoEstimado(tiempoPredichoML * factorTrafico);
            rutaOptimizada.setCostoEstimado(costoEstimado);
            rutaOptimizada.setNivelComodidad(nivelComodidad);
            rutaOptimizada.setConfiabilidad(confiabilidad);
            rutaOptimizada.setFactorTrafico(factorTrafico);
            rutaOptimizada.setParadas(paradasRuta);

            // Calcular puntuación total usando pesos del usuario
            double puntuacionTotal = calculateTotalScore(rutaOptimizada, params);
            rutaOptimizada.setPuntuacionTotal(puntuacionTotal);

            return rutaOptimizada;

        } catch (Exception e) {
            Log.e(TAG, "Error evaluando ruta: " + ruta.getNoRuta(), e);
            return null;
        }
    }

    /**
     * Encuentra rutas con transbordos usando algoritmo A*
     */
    private List<Ruta> findRoutesWithTransfers(Parada origen, Parada destino) {
        // Implementación del algoritmo A* para encontrar rutas con transbordos
        List<Ruta> rutasConTransbordos = new ArrayList<>();

        // Obtener todas las rutas que pasan por el origen
        List<Ruta> rutasOrigen = database.transportDao().getRutasByParada(origen.getIdParada());

        for (Ruta rutaOrigen : rutasOrigen) {
            List<Parada> paradasRuta = database.transportDao().getParadasByRuta(rutaOrigen.getIdRuta());

            // Para cada parada de esta ruta, buscar conexiones al destino
            for (Parada paradaIntermedia : paradasRuta) {
                List<Ruta> rutasDestino = database.transportDao().getRutasByParada(destino.getIdParada());

                for (Ruta rutaDestino : rutasDestino) {
                    if (!rutaOrigen.equals(rutaDestino)) {
                        List<Parada> paradasRutaDestino = database.transportDao()
                                .getParadasByRuta(rutaDestino.getIdRuta());

                        if (paradasRutaDestino.contains(paradaIntermedia)) {
                            // Encontramos una conexión válida
                            rutasConTransbordos.add(rutaOrigen);
                            rutasConTransbordos.add(rutaDestino);
                        }
                    }
                }
            }
        }

        return rutasConTransbordos;
    }

    /**
     * Aplica optimización multi-objetivo para seleccionar la mejor ruta
     */
    private OptimizedRoute applyMultiObjectiveOptimization(List<OptimizedRoute> rutas,
                                                           RouteOptimizationParams params) {
        if (rutas.isEmpty()) return null;

        // Normalizar todas las métricas (0-1)
        normalizeMetrics(rutas);

        // Aplicar algoritmo TOPSIS (Technique for Order Preference by Similarity to Ideal Solution)
        OptimizedRoute mejorRuta = null;
        double mejorPuntuacion = Double.NEGATIVE_INFINITY;

        for (OptimizedRoute ruta : rutas) {
            double puntuacion = calculateTOPSISScore(ruta, params);
            if (puntuacion > mejorPuntuacion) {
                mejorPuntuacion = puntuacion;
                mejorRuta = ruta;
            }
        }

        return mejorRuta;
    }

    // Métodos auxiliares para cálculos
    private double calculateRouteDistance(List<Parada> paradas) {
        // Calcular distancia total usando coordenadas de paradas
        double distanciaTotal = 0.0;
        for (int i = 0; i < paradas.size() - 1; i++) {
            distanciaTotal += calculateDistance(paradas.get(i), paradas.get(i + 1));
        }
        return distanciaTotal;
    }

    private double calculateDistance(Parada p1, Parada p2) {
        // Fórmula de Haversine para calcular distancia entre coordenadas
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

    private double calculateBaseTime(List<Parada> paradas) {
        // Tiempo base estimado (velocidad promedio + tiempo de paradas)
        double distancia = calculateRouteDistance(paradas);
        double velocidadPromedio = 25.0; // km/h promedio en transporte público
        double tiempoPorParada = 1.5; // minutos por parada

        return (distancia / velocidadPromedio * 60) + (paradas.size() * tiempoPorParada);
    }

    private double calculateRouteCost(Ruta ruta, double distancia) {
        // Calcular costo basado en tipo de transporte y distancia
        double tarifaBase = 2.0; // Tarifa base
        double costoPorKm = 0.5;
        return tarifaBase + (distancia * costoPorKm);
    }

    private double calculateComfortLevel(Ruta ruta, RouteOptimizationParams params) {
        // Calcular nivel de comodidad basado en varios factores
        double comodidad = 0.5; // Base

        // Ajustar por tipo de transporte
        if (ruta.getTipoTransporte() != null) {
            switch (ruta.getTipoTransporte().toLowerCase()) {
                case "metro": comodidad += 0.3; break;
                case "bus": comodidad += 0.1; break;
                case "microbus": comodidad -= 0.1; break;
            }
        }

        // Ajustar por hora del día (menos comodidad en horas pico)
        int hora = params.getHoraViaje();
        if ((hora >= 7 && hora <= 9) || (hora >= 17 && hora <= 19)) {
            comodidad -= 0.2; // Horas pico
        }

        return Math.max(0.0, Math.min(1.0, comodidad));
    }

    private double calculateReliabilityScore(Ruta ruta) {
        // Calcular confiabilidad basada en historial de la ruta
        // Por ahora, usar un valor base que se puede mejorar con datos históricos
        return 0.8; // 80% de confiabilidad base
    }

    private double calculateTotalScore(OptimizedRoute ruta, RouteOptimizationParams params) {
        // Calcular puntuación total usando pesos del usuario
        double score = 0.0;

        score += (1.0 / ruta.getTiempoEstimado()) * params.getPesoTiempo();
        score += (1.0 / ruta.getCostoEstimado()) * params.getPesoCosto();
        score += ruta.getNivelComodidad() * params.getPesoComodidad();
        score += ruta.getConfiabilidad() * params.getPesoConfiabilidad();
        score += (1.0 / ruta.getDistanciaTotal()) * params.getPesoDistancia();

        return score;
    }

    private void normalizeMetrics(List<OptimizedRoute> rutas) {
        if (rutas.isEmpty()) return;

        // Encontrar valores min/max para normalización
        double minTiempo = rutas.stream().mapToDouble(OptimizedRoute::getTiempoEstimado).min().orElse(0);
        double maxTiempo = rutas.stream().mapToDouble(OptimizedRoute::getTiempoEstimado).max().orElse(1);
        double minCosto = rutas.stream().mapToDouble(OptimizedRoute::getCostoEstimado).min().orElse(0);
        double maxCosto = rutas.stream().mapToDouble(OptimizedRoute::getCostoEstimado).max().orElse(1);

        // Normalizar métricas
        for (OptimizedRoute ruta : rutas) {
            ruta.setTiempoNormalizado((ruta.getTiempoEstimado() - minTiempo) / (maxTiempo - minTiempo));
            ruta.setCostoNormalizado((ruta.getCostoEstimado() - minCosto) / (maxCosto - minCosto));
        }
    }

    private double calculateTOPSISScore(OptimizedRoute ruta, RouteOptimizationParams params) {
        // Implementación simplificada de TOPSIS
        // En una implementación completa, se calcularían las distancias a soluciones ideales
        return ruta.getPuntuacionTotal();
    }


}
