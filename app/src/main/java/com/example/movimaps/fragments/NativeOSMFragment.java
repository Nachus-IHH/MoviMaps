package com.example.movimaps.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import com.example.movimaps.R;
import com.example.movimaps.api.ApiClient;
import com.example.movimaps.api.ReverseGeocodeResponse;
import com.example.movimaps.SearchResponse;
import com.example.movimaps.database.AppDatabase;
import com.example.movimaps.database.Parada;
import com.example.movimaps.database.Ruta;
import com.example.movimaps.database.TransportDao;
import com.mmt.mapas.utils.HistoryManager;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NativeOSMFragment extends Fragment implements LocationListener {

    private MapView mapView;
    private IMapController mapController;
    private MyLocationNewOverlay myLocationOverlay;
    private LocationManager locationManager;
    private Marker selectedLocationMarker; // Marcador para la ubicación seleccionada por el usuario
    private List<Marker> currentMarkers = new ArrayList<>();
    private List<Polyline> currentPolylines = new ArrayList<>();

    private EditText etSearchAddress;
    private Button btnShowAddress, btnCurrentLocation, btnVisualizeRoute;

    private AppDatabase database;
    private TransportDao transportDao;
    private HistoryManager historyManager;

    private static final int LOCATION_PERMISSION_REQUEST = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Importante: Configurar osmdroid antes de inflar el layout del mapa
        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_native_osm, container, false);

        initViews(view);
        setupMap();
        setupListeners();

        database = AppDatabase.getInstance(requireContext());
        transportDao = database.transportDao();
        historyManager = new HistoryManager(requireContext());

        checkLocationPermission();

        return view;
    }

    private void initViews(View view) {
        mapView = view.findViewById(R.id.mapView);
        etSearchAddress = view.findViewById(R.id.etSearchAddress);
        btnShowAddress = view.findViewById(R.id.btnShowAddress);
        btnCurrentLocation = view.findViewById(R.id.btnCurrentLocation);
        btnVisualizeRoute = view.findViewById(R.id.btnVisualizeRoute);
    }

    private void setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapController = mapView.getController();
        mapController.setZoom(15.0);

        // Coordenadas iniciales (Ciudad de México)
        GeoPoint startPoint = new GeoPoint(19.4326, -99.1332);
        mapController.setCenter(startPoint);

        // Overlay de ubicación
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);
        myLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(myLocationOverlay);

        // Listener para clicks en el mapa (para agregar pin al tocar)
        mapView.getOverlays().add(new org.osmdroid.views.overlay.Overlay() {
            @Override
            public boolean onSingleTapConfirmed(android.view.MotionEvent e, MapView mapView) {
                GeoPoint geoPoint = (GeoPoint) mapView.getProjection().fromPixels((int)e.getX(), (int)e.getY());
                addPinOnMapTouch(geoPoint);
                return true;
            }
        });
    }

    private void setupListeners() {
        btnCurrentLocation.setOnClickListener(v -> getCurrentUserLocation());
        btnShowAddress.setOnClickListener(v -> displayAddressOnMap());
        btnVisualizeRoute.setOnClickListener(v -> visualizeSampleRoute());

        etSearchAddress.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                displayAddressOnMap();
                return true;
            }
            return false;
        });
    }

    /**
     * Método para agregar un pin en el mapa.
     * @param point Coordenadas GeoPoint del pin.
     * @param title Título del pin.
     * @param description Descripción del pin.
     * @param iconResource Recurso de drawable para el icono del pin (ej. R.drawable.ic_location_blue).
     */
    public void addPin(GeoPoint point, String title, String description, int iconResource) {
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle(title);
        marker.setSnippet(description);

        if (iconResource != 0) {
            marker.setIcon(ContextCompat.getDrawable(requireContext(), iconResource));
        }

        mapView.getOverlays().add(marker);
        currentMarkers.add(marker); // Guardar referencia para poder limpiarlos
        mapView.invalidate();
    }

    /**
     * Método para limpiar todos los pines y polilíneas del mapa.
     */
    private void clearMapOverlays() {
        for (Marker marker : currentMarkers) {
            mapView.getOverlays().remove(marker);
        }
        currentMarkers.clear();

        for (Polyline polyline : currentPolylines) {
            mapView.getOverlays().remove(polyline);
        }
        currentPolylines.clear();
        mapView.invalidate();
    }

    /**
     * Visualiza una ruta de ejemplo con sus paradas en el mapa.
     * Para una implementación real, se debería pasar un ID de ruta o un objeto Ruta.
     */
    private void visualizeSampleRoute() {
        clearMapOverlays(); // Limpiar mapa antes de dibujar nueva ruta

        // Ejemplo: Obtener una ruta de la base de datos (asumiendo que existe la Ruta con ID 1)
        // En una app real, esto vendría de una selección del usuario o un intent.
        Ruta sampleRuta = transportDao.getRutaById(1); // Asume que tienes una ruta con ID 1
        if (sampleRuta == null) {
            Toast.makeText(getContext(), "Ruta de ejemplo no encontrada en la base de datos.", Toast.LENGTH_LONG).show();
            // Crear una ruta de ejemplo si no existe en la BD
            sampleRuta = new Ruta("Ruta de Prueba", "bus_icon", 101, "#FF0000", 15);
            long rutaId = transportDao.insertRuta(sampleRuta);
            sampleRuta.setIdRuta((int) rutaId);

            // Agregar paradas de ejemplo para esta ruta
            Parada p1 = new Parada("Parada A", 19.4326, -99.1332, true, "stop_icon", 3, 2, 1);
            Parada p2 = new Parada("Parada B", 19.4285, -99.1277, true, "stop_icon", 4, 3, 2);
            Parada p3 = new Parada("Parada C", 19.4200, -99.1200, true, "stop_icon", 2, 2, 2);

            long p1Id = transportDao.insertParada(p1);
            long p2Id = transportDao.insertParada(p2);
            long p3Id = transportDao.insertParada(p3);

            transportDao.insertRutaHasParada(new com.example.osmmap.database.RutaHasParada(sampleRuta.getIdRuta(), (int)p1Id, 10, "normal", "00:05:00", 1, "00:00:00", 0.0));
            transportDao.insertRutaHasParada(new com.example.osmmap.database.RutaHasParada(sampleRuta.getIdRuta(), (int)p2Id, 15, "normal", "00:07:00", 2, "00:02:00", 0.5));
            transportDao.insertRutaHasParada(new com.example.osmmap.database.RutaHasParada(sampleRuta.getIdRuta(), (int)p3Id, 8, "normal", "00:04:00", 3, "00:03:00", 0.8));

            Toast.makeText(getContext(), "Ruta de ejemplo creada y visualizada.", Toast.LENGTH_LONG).show();
        }

        List<Parada> paradas = transportDao.getParadasByRuta(sampleRuta.getIdRuta());

        if (paradas != null && !paradas.isEmpty()) {
            List<GeoPoint> routePoints = new ArrayList<>();
            for (Parada parada : paradas) {
                GeoPoint stopPoint = new GeoPoint(parada.getLat(), parada.getLng());
                routePoints.add(stopPoint);
                // Agregar pin para cada parada
                addPin(stopPoint, parada.getNombreParada(), "Parada de la ruta " + sampleRuta.getNombreRuta(), R.drawable.ic_location_blue);
            }

            // Dibujar la polilínea de la ruta
            Polyline routePolyline = new Polyline();
            routePolyline.setPoints(routePoints);
            routePolyline.setColor(Color.parseColor(sampleRuta.getColor())); // Usar el color de la ruta
            routePolyline.setWidth(8.0f);
            mapView.getOverlays().add(routePolyline);
            currentPolylines.add(routePolyline);
            mapView.invalidate();

            // Centrar el mapa en la ruta
            mapView.zoomToBoundingBox(org.osmdroid.util.BoundingBox.fromGeoPoints(routePoints), true, 100);

            Toast.makeText(getContext(), "Visualizando ruta: " + sampleRuta.getNombreRuta + " con " + paradas.size() + " paradas.", Toast.LENGTH_LONG).show();
            historyManager.saveRoute(paradas.get(0).getNombreParada(), paradas.get(paradas.size()-1).getNombreParada(),
                    paradas.get(0).getLat(), paradas.get(0).getLng(),
                    paradas.get(paradas.size()-1).getLat(), paradas.get(paradas.size()-1).getLng(),
                    "N/A", "N/A", "driving"); // Guardar en historial
        } else {
            Toast.makeText(getContext(), "No se encontraron paradas para la ruta de ejemplo.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Obtiene la ubicación actual del usuario y la centra en el mapa.
     */
    private void getCurrentUserLocation() {
        if (myLocationOverlay != null && myLocationOverlay.isMyLocationEnabled()) {
            Location lastKnownLocation = myLocationOverlay.getLastFix();
            if (lastKnownLocation != null) {
                GeoPoint currentLocation = new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mapController.animateTo(currentLocation);
                mapController.setZoom(17.0);
                Toast.makeText(getContext(), "Ubicación actual: " + String.format("%.4f, %.4f", currentLocation.getLatitude(), currentLocation.getLongitude()), Toast.LENGTH_SHORT).show();

                // Opcional: Realizar geocodificación inversa para obtener el nombre del lugar
                reverseGeocodeCoordinates(currentLocation.getLatitude(), currentLocation.getLongitude(), "Tu ubicación actual", R.drawable.ic_location_green);
            } else {
                Toast.makeText(getContext(), "Esperando ubicación...", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Permiso de ubicación no concedido o GPS deshabilitado.", Toast.LENGTH_LONG).show();
            checkLocationPermission();
        }
    }

    /**
     * Agrega un pin en la ubicación tocada en el mapa y realiza geocodificación inversa.
     * Este pin se moverá si se toca otra parte del mapa.
     * @param geoPoint Las coordenadas tocadas en el mapa.
     */
    private void addPinOnMapTouch(GeoPoint geoPoint) {
        // Eliminar el marcador anterior si existe
        if (selectedLocationMarker != null) {
            mapView.getOverlays().remove(selectedLocationMarker);
            currentMarkers.remove(selectedLocationMarker);
        }

        selectedLocationMarker = new Marker(mapView);
        selectedLocationMarker.setPosition(geoPoint);
        selectedLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        selectedLocationMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_location_red));
        mapView.getOverlays().add(selectedLocationMarker);
        currentMarkers.add(selectedLocationMarker); // Añadir a la lista de marcadores actuales
        mapView.invalidate();

        // Realizar geocodificación inversa para obtener la dirección
        reverseGeocodeCoordinates(geoPoint.getLatitude(), geoPoint.getLongitude(), "Ubicación seleccionada", R.drawable.ic_location_red);
    }

    /**
     * Realiza geocodificación inversa (coordenadas a dirección) y agrega un pin.
     * @param lat Latitud.
     * @param lng Longitud.
     * @param defaultTitle Título por defecto si no se encuentra nombre de lugar.
     * @param iconResource Recurso de icono para el pin.
     */
    private void reverseGeocodeCoordinates(double lat, double lng, String defaultTitle, int iconResource) {
        ApiClient.getMapApiService().reverseGeocode(lat, lng, "json")
                .enqueue(new Callback<ReverseGeocodeResponse>() {
                    @Override
                    public void onResponse(Call<ReverseGeocodeResponse> call, Response<ReverseGeocodeResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String placeName = response.body().getDisplayName();
                            String shortName = placeName.length() > 50 ? placeName.substring(0, 50) + "..." : placeName;
                            if (selectedLocationMarker != null && selectedLocationMarker.getPosition().getLatitude() == lat && selectedLocationMarker.getPosition().getLongitude() == lng) {
                                selectedLocationMarker.setTitle(shortName);
                                selectedLocationMarker.setSnippet("Coordenadas: " + String.format("%.4f, %.4f", lat, lng));
                                mapView.invalidate();
                            } else {
                                addPin(new GeoPoint(lat, lng), shortName, "Coordenadas: " + String.format("%.4f, %.4f", lat, lng), iconResource);
                            }
                            Toast.makeText(getContext(), "Lugar: " + shortName, Toast.LENGTH_SHORT).show();
                            historyManager.saveLocation(shortName, lat, lng, "selected");
                        } else {
                            if (selectedLocationMarker != null && selectedLocationMarker.getPosition().getLatitude() == lat && selectedLocationMarker.getPosition().getLongitude() == lng) {
                                selectedLocationMarker.setTitle(defaultTitle);
                                selectedLocationMarker.setSnippet("Coordenadas: " + String.format("%.4f, %.4f", lat, lng));
                                mapView.invalidate();
                            } else {
                                addPin(new GeoPoint(lat, lng), defaultTitle, "Coordenadas: " + String.format("%.4f, %.4f", lat, lng), iconResource);
                            }
                            Toast.makeText(getContext(), "No se encontró nombre para las coordenadas.", Toast.LENGTH_SHORT).show();
                            historyManager.saveLocation(defaultTitle + " (" + String.format("%.4f, %.4f", lat, lng) + ")", lat, lng, "selected");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReverseGeocodeResponse> call, Throwable t) {
                        if (selectedLocationMarker != null && selectedLocationMarker.getPosition().getLatitude() == lat && selectedLocationMarker.getPosition().getLongitude() == lng) {
                            selectedLocationMarker.setTitle(defaultTitle);
                            selectedLocationMarker.setSnippet("Coordenadas: " + String.format("%.4f, %.4f", lat, lng));
                            mapView.invalidate();
                        } else {
                            addPin(new GeoPoint(lat, lng), defaultTitle, "Coordenadas: " + String.format("%.4f, %.4f", lat, lng), iconResource);
                        }
                        Toast.makeText(getContext(), "Error al obtener información del lugar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        historyManager.saveLocation(defaultTitle + " (" + String.format("%.4f, %.4f", lat, lng) + ")", lat, lng, "selected");
                    }
                });
    }

    /**
     * Muestra una dirección ingresada en el mapa y opcionalmente la ubicación actual del usuario.
     */
    private void displayAddressOnMap() {
        String address = etSearchAddress.getText().toString().trim();
        if (address.isEmpty()) {
            etSearchAddress.setError("Ingrese una dirección");
            etSearchAddress.requestFocus();
            return;
        }

        clearMapOverlays(); // Limpiar marcadores anteriores

        // Realizar geocodificación (dirección a coordenadas)
        ApiClient.getMapApiService().searchLocation(address, "json", 1)
                .enqueue(new Callback<List<SearchResponse>>() {
                    @Override
                    public void onResponse(Call<List<SearchResponse>> call, Response<List<SearchResponse>> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                            SearchResponse result = response.body().get(0);
                            double lat = Double.parseDouble(result.getLatitude());
                            double lng = Double.parseDouble(result.getLongitude());
                            GeoPoint location = new GeoPoint(lat, lng);

                            mapController.animateTo(location);
                            mapController.setZoom(16.0);
                            addPin(location, result.getDisplayName(), "Dirección buscada", R.drawable.ic_location_red);
                            Toast.makeText(getContext(), "Dirección encontrada: " + result.getDisplayName(), Toast.LENGTH_LONG).show();
                            historyManager.saveSearch(address, result.getDisplayName(), lat, lng, "address");

                            // Opcional: Mostrar ubicación actual del usuario
                            if (myLocationOverlay != null && myLocationOverlay.isMyLocationEnabled()) {
                                Location lastKnownLocation = myLocationOverlay.getLastFix();
                                if (lastKnownLocation != null) {
                                    addPin(new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()),
                                            "Tu ubicación", "Ubicación actual del dispositivo", R.drawable.ic_location_green);
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Dirección no encontrada.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchResponse>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error al buscar dirección: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        } else {
            enableLocationServices();
        }
    }

    private void enableLocationServices() {
        if (myLocationOverlay != null) {
            myLocationOverlay.enableFollowLocation();
            myLocationOverlay.enableMyLocation();
        }
        // Iniciar LocationManager para recibir actualizaciones de ubicación
        locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
            } catch (SecurityException e) {
                Toast.makeText(getContext(), "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocationServices();
            } else {
                Toast.makeText(getContext(), "Permiso de ubicación necesario para usar el mapa.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (myLocationOverlay != null) {
            myLocationOverlay.enableMyLocation();
            myLocationOverlay.enableFollowLocation();
        }
        if (locationManager != null) {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
            } catch (SecurityException e) {
                // Handle exception if permission is revoked after onResume
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if (myLocationOverlay != null) {
            myLocationOverlay.disableMyLocation();
            myLocationOverlay.disableFollowLocation();
        }
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Opcional: Actualizar el mapa automáticamente con la nueva ubicación
        // GeoPoint newLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
        // mapController.animateTo(newLocation);
        // Toast.makeText(getContext(), "Ubicación actualizada: " + String.format("%.4f, %.4f", location.getLatitude(), location.getLongitude()), Toast.LENGTH_SHORT).show();
    }
}

