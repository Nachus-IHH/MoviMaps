package com.example.movimaps.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.movimaps.R;
import com.example.movimaps.osmmap.api.ApiClient;
import com.example.movimaps.osmmap.api.GeocodingResponse;
import com.example.movimaps.sql.database.AppDatabase;
import com.example.movimaps.sql.database.Parada;
import com.example.movimaps.sql.dao.TransportDao;
import com.example.movimaps.sql.entity.Ruta;
import com.example.movimaps.utils.HistoryManager;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento unificado de OSMDroid para MoviMaps
 * Incluye: BD (Room), Retrofit (geocodificación), historial, utilitarios de mapa.
 */
public class NativeOSMFragment extends Fragment {

    private MapView mapView;
    private MapController mapController;
    private MyLocationNewOverlay myLocationOverlay;
    private Context context;

    private AppDatabase database;
    private TransportDao transportDao;
    private HistoryManager historyManager;

    // Gestión de permisos
    private ActivityResultLauncher<String> requestPermissionLauncher;

    // ================== CICLO DE VIDA ==================
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_native_osm, container, false);

        context = requireContext();
        Configuration.getInstance().load(context, context.getSharedPreferences("osm_prefs", Context.MODE_PRIVATE));

        // Inicializar BD e historial
        database = AppDatabase.getInstance(context);
        transportDao = database.transportDao();
        historyManager = new HistoryManager(context);

        // Configuración de permisos
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                enableUserLocation();
            } else {
                Log.e("OSM", "Permiso de ubicación denegado");
            }
        });

        // Configuración del mapa
        mapView = view.findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(15.0);

        // Overlay de ubicación
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), mapView);
        myLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(myLocationOverlay);

        // Eventos táctiles para agregar pines
        addPinOnMapTouch();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    // ================== MÉTODOS DE UBICACIÓN ==================
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }
        myLocationOverlay.enableMyLocation();
    }

    @SuppressLint("MissingPermission")
    public void getCurrentUserLocation() {
        if (myLocationOverlay.getMyLocation() != null) {
            GeoPoint myLocation = myLocationOverlay.getMyLocation();
            centerMapOnLocation(myLocation.getLatitude(), myLocation.getLongitude());
        } else {
            Log.e("OSM", "Ubicación aún no disponible");
        }
    }

    // ================== MÉTODOS DE MAPA (UTILITARIOS) ==================
    public void centerMapOnLocation(double latitude, double longitude) {
        GeoPoint point = new GeoPoint(latitude, longitude);
        mapController.setCenter(point);
        mapController.animateTo(point);
    }

    public void addMarker(double latitude, double longitude, String title, String description) {
        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(latitude, longitude));
        marker.setTitle(title);
        marker.setSubDescription(description);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
        mapView.invalidate();
    }

    public void showMyLocation() {
        if (myLocationOverlay != null && myLocationOverlay.getMyLocation() != null) {
            mapController.animateTo(myLocationOverlay.getMyLocation());
        }
    }

    public void clearMap() {
        mapView.getOverlays().clear();
        mapView.invalidate();
    }

    public void clearMapOverlays() {
        List<?> overlays = new ArrayList<>(mapView.getOverlays());
        for (Object overlay : overlays) {
            if (overlay instanceof Marker || overlay instanceof ItemizedIconOverlay) {
                mapView.getOverlays().remove(overlay);
            }
        }
        mapView.invalidate();
    }

    // ================== FUNCIONES AVANZADAS ==================
    private void addPinOnMapTouch() {
        mapView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                IGeoPoint geoPoint = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
                Marker marker = new Marker(mapView);
                marker.setPosition(new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
                marker.setTitle("Marcador");
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                mapView.getOverlays().add(marker);
                mapView.invalidate();
            }
            return false;
        });
    }

    public void displayAddressOnMap(String query) {
        ApiClient.getMapApiService().search(query, "json").enqueue(new Callback<List<GeocodingResponse>>() {
            @Override
            public void onResponse(Call<List<GeocodingResponse>> call, Response<List<GeocodingResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    GeocodingResponse result = response.body().get(0);
                    double lat = Double.parseDouble(result.getLatitude());
                    double lon = Double.parseDouble(result.getLongitude());
                    // Asegúrate de usar el nombre correcto en GeocodingResponse
                    addMarker(lat, lon, "Dirección", result.getDisplayName());
                    centerMapOnLocation(lat, lon);

                    // Guardar en historial
                    historyManager.saveSearch(query, result.getDisplayName(), lat, lon, "geocoding");
                    // Nota: Tu HistoryManager usa 'lat' y 'lon' como double, lo cual es correcto.
                }
            }

            @Override
            public void onFailure(Call<List<GeocodingResponse>> call, Throwable t) {
                Log.e("OSM", "Error de geocodificación: " + t.getMessage());
            }
        });
    }

    public void visualizeSampleRoute() {
        new Thread(() -> {
            Ruta ruta = transportDao.getRutaById(1);
            if (ruta != null) {
                List<Parada> paradas = transportDao.getParadasByRuta(ruta.getIdRuta());
                requireActivity().runOnUiThread(() -> {
                    clearMapOverlays();
                    ArrayList<OverlayItem> overlayItems = new ArrayList<>();
                    for (Parada parada : paradas) {
                        GeoPoint point = new GeoPoint(parada.getLat(), parada.getLng());
                        overlayItems.add(new OverlayItem(parada.getIdParada()+"", "Parada en " + parada.getNombreParada(), point));
                    }
                    ItemizedOverlayWithFocus<OverlayItem> overlay = new ItemizedOverlayWithFocus<>(overlayItems, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                        @Override
                        public boolean onItemSingleTapUp(int index, OverlayItem item) {
                            return true;
                        }

                        @Override
                        public boolean onItemLongPress(int index, OverlayItem item) {
                            return false;
                        }
                    }, context);
                    overlay.setFocusItemsOnTap(true);
                    mapView.getOverlays().add(overlay);
                    mapView.invalidate();
                });
            }
        }).start();
    }

    public void centerMapOnLocation(GeoPoint myLocation) {
    }
}
