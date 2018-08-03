package miadesign.hu.best.balaton.views;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import miadesign.hu.best.balaton.R;
import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.entities.Attraction;
import miadesign.hu.best.balaton.viewmodels.AttractionListViewModel;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private FusedLocationProviderClient clientPosition;
    private Activity activity;
    AttractionListViewModel viewModel;
    private static final int DEFAULT_ZOOM = 8;
    private static final int LOCATION_REQUEST_CODE = 42;
    private static final LatLng DEFAULT_POS = new LatLng(46.831891, 17.718159);

    public MapFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        activity = getActivity();
        // MapView
        mapView = root.findViewById(R.id.mapView);
        clientPosition = LocationServices.getFusedLocationProviderClient(activity);
        mapView.onCreate(null);
        mapView.getMapAsync(this);
        FragmentActivity activity = Objects.requireNonNull(getActivity());
        viewModel = ViewModelProviders.of(activity).get(AttractionListViewModel.class);
        return root;

    }

    private void putDataMarkersOnMap(Attraction[] attractions) {
        map.setInfoWindowAdapter(new MapInfo(activity));
        for (Attraction attraction : attractions) {
            LatLng here = new LatLng(attraction.getLatitude(), attraction.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(here)
                    .title(attraction.getName())
                    .snippet(attraction.getMainImage())
            ).setTag(attraction);
            map.setOnInfoWindowClickListener(marker -> {
                View infoWindow = View.inflate(activity, R.layout.map_info, null);
                ImageView preview = infoWindow.findViewById(R.id.image);
                Intent intent = new Intent(activity, AttractionActivity.class);
                intent.putExtra(App.INTENT_BUILDING, (Attraction) marker.getTag());
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, preview, "main_image");
                activity.startActivity(intent, options.toBundle());
            });
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_POS, DEFAULT_ZOOM));
    }

    //Map, Permission and Location
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        this.requestPermissionAndSetPosition();
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        viewModel.getList().observe(this, list -> {
            if (list != null) {
                this.putDataMarkersOnMap(list);
            }
        });
        map.setOnCameraMoveListener(() -> viewModel.setPosition(map.getCameraPosition().target));
    }

    private void requestPermissionAndSetPosition() {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.getDeviceLocation();
        } else {
            this.requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE
            );
        }

    }

    private void setDefaultPosition() throws SecurityException {

        map.setMyLocationEnabled(false);
        LatLng position = viewModel.getPosition() == null ? DEFAULT_POS : viewModel.getPosition();
        map.moveCamera(CameraUpdateFactory.newLatLng(position));
        map.getUiSettings().setMyLocationButtonEnabled(false);

    }

    private void getDeviceLocation() throws SecurityException {

        Task<Location> locationResult = clientPosition.getLastLocation();
        locationResult.addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                map.setMyLocationEnabled(true);
            } else {
                this.setDefaultPosition();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) throws SecurityException {

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.getDeviceLocation();
            } else {
                this.setDefaultPosition();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }
    //endregion

}
