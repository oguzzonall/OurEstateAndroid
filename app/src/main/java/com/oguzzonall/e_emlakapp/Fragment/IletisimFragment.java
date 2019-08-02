package com.oguzzonall.e_emlakapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oguzzonall.e_emlakapp.R;

public class IletisimFragment extends Fragment implements OnMapReadyCallback {
    View view;
    MapView mapView;
    GoogleMap gm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_iletisim, container, false);
        mapView = view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gm = googleMap;
        LatLng latLng = new LatLng(40.7423863, 30.3282263);
        gm.addMarker(new MarkerOptions().position(latLng).title("E-Emlak"));
        gm.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gm.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}
