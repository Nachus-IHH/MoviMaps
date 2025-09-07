package com.example.movimaps.osmmap.ai;
/**
 * Parámetros para la optimización de rutas con IA
 */
public class RouteOptimizationParams {

    // Pesos para diferentes factores (deben sumar 1.0)
    private double pesoTiempo = 0.3;
    private double pesoCosto = 0.2;
    private double pesoComodidad = 0.2;
    private double pesoConfiabilidad = 0.2;
    private double pesoDistancia = 0.1;

    // Contexto temporal
    private int horaViaje = 12; // Hora del día (0-23)
    private int diaSemana = 1;  // Día de la semana (1=Lunes, 7=Domingo)

    // Preferencias del usuario
    private boolean evitarTransbordos = false;
    private boolean preferirTransporteRapido = true;
    private boolean considerarAccesibilidad = false;
    private double presupuestoMaximo = Double.MAX_VALUE;
    private int tiempoMaximo = Integer.MAX_VALUE; // En minutos

    // Configuración de IA
    private boolean usarPrediccionTrafico = true;
    private boolean usarAprendizajeML = true;
    private double umbralConfianza = 0.7; // Umbral mínimo de confianza para predicciones

    // Constructor por defecto
    public RouteOptimizationParams() {}

    // Constructor con parámetros básicos
    public RouteOptimizationParams(int horaViaje, int diaSemana) {
        this.horaViaje = horaViaje;
        this.diaSemana = diaSemana;
    }

    // Constructor completo
    public RouteOptimizationParams(double pesoTiempo, double pesoCosto, double pesoComodidad,
                                   double pesoConfiabilidad, double pesoDistancia,
                                   int horaViaje, int diaSemana) {
        this.pesoTiempo = pesoTiempo;
        this.pesoCosto = pesoCosto;
        this.pesoComodidad = pesoComodidad;
        this.pesoConfiabilidad = pesoConfiabilidad;
        this.pesoDistancia = pesoDistancia;
        this.horaViaje = horaViaje;
        this.diaSemana = diaSemana;

        // Normalizar pesos para que sumen 1.0
        normalizarPesos();
    }

    /**
     * Normaliza los pesos para que sumen 1.0
     */
    public void normalizarPesos() {
        double suma = pesoTiempo + pesoCosto + pesoComodidad + pesoConfiabilidad + pesoDistancia;
        if (suma > 0) {
            pesoTiempo /= suma;
            pesoCosto /= suma;
            pesoComodidad /= suma;
            pesoConfiabilidad /= suma;
            pesoDistancia /= suma;
        }
    }

    /**
     * Crea parámetros optimizados para velocidad
     */
    public static RouteOptimizationParams paraVelocidad(int horaViaje, int diaSemana) {
        RouteOptimizationParams params = new RouteOptimizationParams();
        params.pesoTiempo = 0.6;
        params.pesoCosto = 0.1;
        params.pesoComodidad = 0.1;
        params.pesoConfiabilidad = 0.1;
        params.pesoDistancia = 0.1;
        params.horaViaje = horaViaje;
        params.diaSemana = diaSemana;
        params.preferirTransporteRapido = true;
        return params;
    }

    /**
     * Crea parámetros optimizados para economía
     */
    public static RouteOptimizationParams paraEconomia(int horaViaje, int diaSemana) {
        RouteOptimizationParams params = new RouteOptimizationParams();
        params.pesoTiempo = 0.2;
        params.pesoCosto = 0.5;
        params.pesoComodidad = 0.1;
        params.pesoConfiabilidad = 0.1;
        params.pesoDistancia = 0.1;
        params.horaViaje = horaViaje;
        params.diaSemana = diaSemana;
        return params;
    }

    /**
     * Crea parámetros optimizados para comodidad
     */
    public static RouteOptimizationParams paraComodidad(int horaViaje, int diaSemana) {
        RouteOptimizationParams params = new RouteOptimizationParams();
        params.pesoTiempo = 0.2;
        params.pesoCosto = 0.2;
        params.pesoComodidad = 0.4;
        params.pesoConfiabilidad = 0.1;
        params.pesoDistancia = 0.1;
        params.horaViaje = horaViaje;
        params.diaSemana = diaSemana;
        params.evitarTransbordos = true;
        return params;
    }

    /**
     * Crea parámetros balanceados
     */
    public static RouteOptimizationParams balanceado(int horaViaje, int diaSemana) {
        return new RouteOptimizationParams(0.3, 0.2, 0.2, 0.2, 0.1, horaViaje, diaSemana);
    }

    // Getters y Setters
    public double getPesoTiempo() { return pesoTiempo; }
    public void setPesoTiempo(double pesoTiempo) { this.pesoTiempo = pesoTiempo; }

    public double getPesoCosto() { return pesoCosto; }
    public void setPesoCosto(double pesoCosto) { this.pesoCosto = pesoCosto; }

    public double getPesoComodidad() { return pesoComodidad; }
    public void setPesoComodidad(double pesoComodidad) { this.pesoComodidad = pesoComodidad; }

    public double getPesoConfiabilidad() { return pesoConfiabilidad; }
    public void setPesoConfiabilidad(double pesoConfiabilidad) { this.pesoConfiabilidad = pesoConfiabilidad; }

    public double getPesoDistancia() { return pesoDistancia; }
    public void setPesoDistancia(double pesoDistancia) { this.pesoDistancia = pesoDistancia; }

    public int getHoraViaje() { return horaViaje; }
    public void setHoraViaje(int horaViaje) { this.horaViaje = horaViaje; }

    public int getDiaSemana() { return diaSemana; }
    public void setDiaSemana(int diaSemana) { this.diaSemana = diaSemana; }

    public boolean isEvitarTransbordos() { return evitarTransbordos; }
    public void setEvitarTransbordos(boolean evitarTransbordos) { this.evitarTransbordos = evitarTransbordos; }

    public boolean isPreferirTransporteRapido() { return preferirTransporteRapido; }
    public void setPreferirTransporteRapido(boolean preferirTransporteRapido) {
        this.preferirTransporteRapido = preferirTransporteRapido;
    }

    public boolean isConsiderarAccesibilidad() { return considerarAccesibilidad; }
    public void setConsiderarAccesibilidad(boolean considerarAccesibilidad) {
        this.considerarAccesibilidad = considerarAccesibilidad;
    }

    public double getPresupuestoMaximo() { return presupuestoMaximo; }
    public void setPresupuestoMaximo(double presupuestoMaximo) { this.presupuestoMaximo = presupuestoMaximo; }

    public int getTiempoMaximo() { return tiempoMaximo; }
    public void setTiempoMaximo(int tiempoMaximo) { this.tiempoMaximo = tiempoMaximo; }

    public boolean isUsarPrediccionTrafico() { return usarPrediccionTrafico; }
    public void setUsarPrediccionTrafico(boolean usarPrediccionTrafico) {
        this.usarPrediccionTrafico = usarPrediccionTrafico;
    }

    public boolean isUsarAprendizajeML() { return usarAprendizajeML; }
    public void setUsarAprendizajeML(boolean usarAprendizajeML) { this.usarAprendizajeML = usarAprendizajeML; }

    public double getUmbralConfianza() { return umbralConfianza; }
    public void setUmbralConfianza(double umbralConfianza) { this.umbralConfianza = umbralConfianza; }


}
