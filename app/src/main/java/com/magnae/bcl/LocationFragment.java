package com.magnae.bcl;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private MapFragment mapFragment;


    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        NaverMapOptions options = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(37.503198, 126.775964), 11.5))
                .mapType(NaverMap.MapType.Basic);

        mapFragment = (MapFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment = MapFragment.newInstance(options);
        activity.getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();

        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        Marker markerAA = new Marker();
        markerAA.setPosition(new LatLng(37.490072, 126.744688));
        markerAA.setCaptionAlign(Align.Bottom);
        markerAA.setCaptionText("상동도서관");
        markerAA.setIconPerspectiveEnabled(true);
        markerAA.setMap(naverMap);

        Marker markerAB = new Marker();
        markerAB.setPosition(new LatLng(37.498934, 126.7981866));
        markerAB.setCaptionAlign(Align.Bottom);
        markerAB.setCaptionText("원미도서관");
        markerAB.setIconPerspectiveEnabled(true);
        markerAB.setMap(naverMap);

        Marker markerAC = new Marker();
        markerAC.setPosition(new LatLng(37.480248, 126.783911));
        markerAC.setCaptionAlign(Align.Bottom);
        markerAC.setCaptionText("심곡도서관");
        markerAC.setIconPerspectiveEnabled(true);
        markerAC.setMap(naverMap);

        Marker markerAD = new Marker();
        markerAD.setPosition(new LatLng(37.520542, 126.794618));
        markerAD.setCaptionAlign(Align.Bottom);
        markerAD.setCaptionText("북부도서관");
        markerAD.setIconPerspectiveEnabled(true);
        markerAD.setMap(naverMap);

        Marker markerAE = new Marker();
        markerAE.setPosition(new LatLng(37.508202, 126.773745));
        markerAE.setCaptionAlign(Align.Bottom);
        markerAE.setCaptionText("꿈도서관");
        markerAE.setIconPerspectiveEnabled(true);
        markerAE.setMap(naverMap);
    }

}
