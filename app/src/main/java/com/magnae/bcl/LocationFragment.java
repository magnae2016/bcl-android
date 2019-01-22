package com.magnae.bcl;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
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
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private MapFragment mapFragment;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;


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
        locationSource = new FusedLocationSource(activity, LOCATION_PERMISSION_REQUEST_CODE);

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {

        naverMap.setLocationSource(locationSource);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Face);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        final List<Marker> markers = new ArrayList<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Marker markerAA = new Marker();
                markerAA.setPosition(new LatLng(37.490072, 126.744688));
                markerAA.setCaptionText("상동도서관");
                markerAA.setSubCaptionText("운영중");
                markerAA.setSubCaptionColor(Color.BLUE);
                markerAA.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAA.setIcon(MarkerIcons.GREEN);
                markers.add(markerAA);

                Marker markerAB = new Marker();
                markerAB.setPosition(new LatLng(37.498934, 126.7981866));
                markerAB.setCaptionText("원미도서관");
                markerAB.setSubCaptionText("운영중");
                markerAB.setSubCaptionColor(Color.BLUE);
                markerAB.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAB.setIcon(MarkerIcons.YELLOW);
                markers.add(markerAB);

                Marker markerAC = new Marker();
                markerAC.setPosition(new LatLng(37.480248, 126.783911));
                markerAC.setCaptionText("심곡도서관");
                markerAC.setSubCaptionText("운영중");
                markerAC.setSubCaptionColor(Color.BLUE);
                markerAC.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAC.setIcon(MarkerIcons.YELLOW);
                markers.add(markerAC);

                Marker markerAD = new Marker();
                markerAD.setPosition(new LatLng(37.520542, 126.794618));
                markerAD.setCaptionText("북부도서관");
                markerAD.setSubCaptionText("운영중");
                markerAD.setSubCaptionColor(Color.BLUE);
                markerAD.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAD.setIcon(MarkerIcons.LIGHTBLUE);
                markers.add(markerAD);

                Marker markerAE = new Marker();
                markerAE.setPosition(new LatLng(37.508202, 126.773745));
                markerAE.setCaptionText("꿈빛도서관");
                markerAE.setSubCaptionText("운영중");
                markerAE.setSubCaptionColor(Color.BLUE);
                markerAE.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAE.setIcon(MarkerIcons.GREEN);
                markers.add(markerAE);

                Marker markerAF = new Marker();
                markerAF.setPosition(new LatLng(37.493931, 126.770068));
                markerAF.setCaptionText("책마루도서관");
                markerAF.setSubCaptionText("운영중");
                markerAF.setSubCaptionColor(Color.BLUE);
                markerAF.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAF.setIcon(MarkerIcons.GREEN);
                markers.add(markerAF);

                Marker markerAG = new Marker();
                markerAG.setPosition(new LatLng(37.467791, 126.796517));
                markerAG.setCaptionText("한울빛도서관");
                markerAG.setSubCaptionText("운영중");
                markerAG.setSubCaptionColor(Color.BLUE);
                markerAG.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAG.setIcon(MarkerIcons.YELLOW);
                markers.add(markerAG);

                Marker markerAH = new Marker();
                markerAH.setPosition(new LatLng(37.515386, 126.805572));
                markerAH.setCaptionText("꿈여울도서관");
                markerAH.setSubCaptionText("운영중");
                markerAH.setSubCaptionColor(Color.BLUE);
                markerAH.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAH.setIcon(MarkerIcons.LIGHTBLUE);
                markers.add(markerAH);

                Marker markerAI = new Marker();
                markerAI.setPosition(new LatLng(37.4824868, 126.7664629));
                markerAI.setCaptionText("송내도서관");
                markerAI.setSubCaptionText("운영중");
                markerAI.setSubCaptionColor(Color.BLUE);
                markerAI.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAI.setIcon(MarkerIcons.GREEN);
                markers.add(markerAI);

                Marker markerAK = new Marker();
                markerAK.setPosition(new LatLng(37.516485, 126.785366));
                markerAK.setCaptionText("도당도서관");
                markerAK.setSubCaptionText("운영중");
                markerAK.setSubCaptionColor(Color.BLUE);
                markerAK.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAK.setIcon(MarkerIcons.LIGHTBLUE);
                markers.add(markerAK);

                Marker markerAJ = new Marker();
                markerAJ.setPosition(new LatLng(37.528180, 126.796453));
                markerAJ.setCaptionText("오정도서관");
                markerAJ.setSubCaptionText("운영중");
                markerAJ.setSubCaptionColor(Color.BLUE);
                markerAJ.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAJ.setIcon(MarkerIcons.LIGHTBLUE);
                markers.add(markerAJ);

                Marker markerAL = new Marker();
                markerAL.setPosition(new LatLng(37.49479, 126.754049));
                markerAL.setCaptionText("동화도서관");
                markerAL.setSubCaptionText("운영중");
                markerAL.setSubCaptionColor(Color.BLUE);
                markerAL.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markerAL.setIcon(MarkerIcons.GREEN);
                markers.add(markerAL);

                for (Marker marker : markers) {
                    marker.setCaptionAlign(Align.Bottom);
                    marker.setSubCaptionTextSize(10);
                    marker.setIconPerspectiveEnabled(true);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (Marker marker : markers) {
                            marker.setMap(naverMap);
                        }
                    }
                });
            }
        });

        executor.shutdown();
    }
}
