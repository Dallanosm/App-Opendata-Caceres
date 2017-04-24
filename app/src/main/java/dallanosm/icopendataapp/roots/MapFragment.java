package dallanosm.icopendataapp.roots;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dallanosm.icopendataapp.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private List<Site> points;

    private boolean isUrl;

    private LatLng caceres = new LatLng(39.4694077, -6.3705542);

    private MapView mapView;

    public static MapFragment newInstance(List<Site> sites, boolean url) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putBoolean("url", url);
        args.putParcelableArrayList("sites", (ArrayList<? extends Parcelable>) sites);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        points = getArguments().getParcelableArrayList("sites");
        isUrl = getArguments().getBoolean("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        this.mapView = (MapView) view.findViewById(R.id.map_view);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);

        for (Site point : points) {
            MarkerOptions marker = new MarkerOptions().position(point.getLatLng()).title(point.getTitle())
                    .icon(BitmapDescriptorFactory.defaultMarker(point.getIcon()));

            if (point.getDescription() != null) {
                marker.snippet(point.getDescription());
            }
            googleMap.addMarker(marker);
        }

        if (isUrl) {
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(marker.getSnippet()));
                    startActivity(browserIntent);
                }
            });
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(caceres));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 3000, null);
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        MapsInitializer.initialize(getContext());

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
