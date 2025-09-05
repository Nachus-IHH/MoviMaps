package com.example.movimaps.osmmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.movimaps.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class NativeOsmFragment extends Fragment implements LocationListener {

    private MapView mapView;
    private MyLocationNewOverlay myLocationOverlay;
    private IMapController mapController;
    private static final int LOCATION_PERMISSION_REQUEST = 1;

    // esto deberia ser aparte, no aqui
    private Button btnShowAddress, btnCurrentLocation, btnVisualizeRoute;
    private EditText etSearchAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 1. Inflar el layout del fragmento y obtener la vista principal
        View rootView = inflater.inflate(R.layout.fragment_native_osm, container, false);

        // 2. Configurar osmdroid antes de crear la vista del mapa
        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());

        // Inicializa vistas
        initViews(rootView);
        // 4. Configurar el mapa
        setupMap();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 5. Verificar permisos de ubicación después de que la vista se haya creado
        checkLocationPermission();
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

    /**
     * Inicializa las vistas
     * @param view
     */
    private void initViews(View view) {
        mapView = view.findViewById(R.id.mapView);
        etSearchAddress = view.findViewById(R.id.etSearchAddress);
        btnShowAddress = view.findViewById(R.id.btnShowAddress);
        btnCurrentLocation = view.findViewById(R.id.btnCurrentLocation);
        btnVisualizeRoute = view.findViewById(R.id.btnVisualizeRoute);
    }

    /**
     * Configura el mapa
     */
    private void setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapController = mapView.getController();
        mapController.setZoom(9.5);
    }

    /**
     * Comprueba si tienes los permisos de ubicacion activados
     */
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

    /**
     * Habilita los permisos de ubicacion
     */
    private void enableLocationServices() {
        if (myLocationOverlay != null) {
            myLocationOverlay.enableFollowLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocationServices();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            GeoPoint newLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
            mapView.getController().animateTo(newLocation);
        }
    }

    // METODOS PARA PERSONALIZAR EL FRAGMENTO

    // Metodo para centrar el mapa en una ubicación específica
    public void centerMapOnLocation(GeoPoint point) {
        if (mapController != null) {
            mapController.animateTo(point);
            mapView.invalidate();
        }
    }

    public Marker addMarker(GeoPoint point, String title, String description, int iconId) {
        // Crear el marcador
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        // Configurar texto
        if (title != null) { marker.setTitle(title); }
        if (description != null) { marker.setSnippet(description); }

        // Configurar icono personalizado
        if (iconId != 0) {
            try {
                marker.setIcon(getResources().getDrawable(iconId));
            }
            catch (Resources.NotFoundException e) {
                marker.setIcon(getResources().getDrawable(R.drawable.icon_location_on));
            }
        }

        // Añadir el marcador al mapa
        mapView.getOverlays().add(marker);
        mapView.invalidate(); // Refrescar el mapa

        return marker;
    }

    /* Listener para clicks en el mapa (para agregar pin al tocar)
    public void addPinOnClic() {
        mapView.getOverlays().add(new org.osmdroid.views.overlay.Overlay() {
            @Override
            public boolean onSingleTapConfirmed(android.view.MotionEvent e, MapView mapView) {
                GeoPoint geoPoint = (GeoPoint) mapView.getProjection().fromPixels((int)e.getX(), (int)e.getY());
                //addPinOnMapTouch(geoPoint);
                addMarker(geoPoint, "Ubicación", "", 0);
                return true;
            }
        });
    }
    */

    public GeoPoint getMyLocation() {
        return new GeoPoint(getMyLocation().getLatitude(), getMyLocation().getLongitude());
    }
    public void showMyLocation(){
        // Overlay de ubicación
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);
        myLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(myLocationOverlay);
    }


    // Metodo para limpiar todos los overlays del mapa
    public void clearMap() {
        if (mapView != null) {
            mapView.getOverlays().clear();
            mapView.invalidate();
        }
    }

}