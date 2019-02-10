package com.magnae.bcl;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private Toolbar myToolbar;
    private MapFragment mapFragment;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    CoordinatorLayout coordinatorLayout;
    private BottomSheetBehavior<View> persistentBottomSheet;
    private LocationButtonView locationButtonView;
    private Marker oldMarker = new Marker();


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

        myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(myToolbar);
        locationSource = new FusedLocationSource(activity, LOCATION_PERMISSION_REQUEST_CODE);

        NaverMapOptions options = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(37.503198, 126.775964), 11.5))
                .mapType(NaverMap.MapType.Basic);

        mapFragment = (MapFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment = MapFragment.newInstance(options);
        activity.getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();

        locationButtonView = view.findViewById(R.id.location_button);

        mapFragment.getMapAsync(this);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        final View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        persistentBottomSheet = BottomSheetBehavior.from(bottomSheet);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public OverlayImage getOverlayImage(String tag, Boolean activation) {

        final OverlayImage marker = OverlayImage.fromResource(R.drawable.marker_icon_sm);
        final OverlayImage marker_green = OverlayImage.fromResource(R.drawable.marker_icon_sm_green);
        final OverlayImage marker_blue = OverlayImage.fromResource(R.drawable.marker_icon_sm_blue);
        final OverlayImage marker_yellow = OverlayImage.fromResource(R.drawable.marker_icon_sm_yellow);

        final OverlayImage marker_active = OverlayImage.fromResource(R.drawable.marker_icon_lg);
        final OverlayImage marker_green_active = OverlayImage.fromResource(R.drawable.marker_icon_lg_green);
        final OverlayImage marker_blue_active = OverlayImage.fromResource(R.drawable.marker_icon_lg_blue);
        final OverlayImage marker_yellow_active = OverlayImage.fromResource(R.drawable.marker_icon_lg_yellow);

        final OverlayImage collapseMarker;
        final OverlayImage activeMarker;

        switch (tag) {
            case "AA":
            case "AE":
            case "AF":
            case "AI":
            case "AL":
                collapseMarker = marker_green;
                activeMarker = marker_green_active;
                break;
            case "AB":
            case "AC":
            case "AG":
                collapseMarker = marker_yellow;
                activeMarker = marker_yellow_active;
                break;
            case "AD":
            case "AH":
            case "AK":
            case "AJ":
                collapseMarker = marker_blue;
                activeMarker = marker_blue_active;
                break;
            default:
                collapseMarker = marker;
                activeMarker = marker_active;
        }

        if (activation) {
            return activeMarker;
        } else {
            return collapseMarker;
        }
    }


    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {

        naverMap.setLocationSource(locationSource);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(false);
        locationButtonView.setMap(naverMap);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Face);

        persistentBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    locationButtonView.setMap(null);
                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    locationButtonView.setMap(naverMap);
                }

                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    locationButtonView.setMap(naverMap);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        final List<Marker> markers = new ArrayList<>();

        final Overlay.OnClickListener listener = new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {

                String oldMarkerTag;
                if (oldMarker.getTag() != null) {
                    oldMarkerTag = oldMarker.getTag().toString();
                    oldMarker.setIcon(getOverlayImage(oldMarkerTag, false));
                    oldMarker.setWidth(60);
                    oldMarker.setHeight(68);
                }

                Marker marker = (Marker) overlay;
                String newMarkerTag = marker.getTag().toString();
                marker.setIcon(getOverlayImage(newMarkerTag, true));
                marker.setWidth(110);
                marker.setHeight(124);

                oldMarker = marker;
                return true;
            }
        };

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Marker markerAA = new Marker();
                markerAA.setPosition(new LatLng(37.490072, 126.744688));
                markerAA.setCaptionText("상동도서관");
                markerAA.setTag("AA");
                markerAA.setSubCaptionText("운영중");
                markerAA.setSubCaptionColor(Color.BLUE);
                markerAA.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAA);

                Marker markerAB = new Marker();
                markerAB.setPosition(new LatLng(37.498934, 126.7981866));
                markerAB.setCaptionText("원미도서관");
                markerAB.setTag("AB");
                markerAB.setSubCaptionText("운영중");
                markerAB.setSubCaptionColor(Color.BLUE);
                markerAB.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAB);

                Marker markerAC = new Marker();
                markerAC.setPosition(new LatLng(37.480248, 126.783911));
                markerAC.setCaptionText("심곡도서관");
                markerAC.setTag("AC");
                markerAC.setSubCaptionText("운영중");
                markerAC.setSubCaptionColor(Color.BLUE);
                markerAC.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAC);

                Marker markerAD = new Marker();
                markerAD.setPosition(new LatLng(37.520542, 126.794618));
                markerAD.setCaptionText("북부도서관");
                markerAD.setTag("AD");
                markerAD.setSubCaptionText("운영중");
                markerAD.setSubCaptionColor(Color.BLUE);
                markerAD.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAD);

                Marker markerAE = new Marker();
                markerAE.setPosition(new LatLng(37.508202, 126.773745));
                markerAE.setCaptionText("꿈빛도서관");
                markerAE.setTag("AE");
                markerAE.setSubCaptionText("운영중");
                markerAE.setSubCaptionColor(Color.BLUE);
                markerAE.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAE);

                Marker markerAF = new Marker();
                markerAF.setPosition(new LatLng(37.493931, 126.770068));
                markerAF.setCaptionText("책마루도서관");
                markerAF.setTag("AF");
                markerAF.setSubCaptionText("운영중");
                markerAF.setSubCaptionColor(Color.BLUE);
                markerAF.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAF);

                Marker markerAG = new Marker();
                markerAG.setPosition(new LatLng(37.467791, 126.796517));
                markerAG.setCaptionText("한울빛도서관");
                markerAG.setTag("AG");
                markerAG.setSubCaptionText("운영중");
                markerAG.setSubCaptionColor(Color.BLUE);
                markerAG.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAG);

                Marker markerAH = new Marker();
                markerAH.setPosition(new LatLng(37.515386, 126.805572));
                markerAH.setCaptionText("꿈여울도서관");
                markerAH.setTag("AH");
                markerAH.setSubCaptionText("운영중");
                markerAH.setSubCaptionColor(Color.BLUE);
                markerAH.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAH);

                Marker markerAI = new Marker();
                markerAI.setPosition(new LatLng(37.4824868, 126.7664629));
                markerAI.setCaptionText("송내도서관");
                markerAI.setTag("AI");
                markerAI.setSubCaptionText("운영중");
                markerAI.setSubCaptionColor(Color.BLUE);
                markerAI.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAI);

                Marker markerAK = new Marker();
                markerAK.setPosition(new LatLng(37.516485, 126.785366));
                markerAK.setCaptionText("도당도서관");
                markerAK.setTag("AK");
                markerAK.setSubCaptionText("운영중");
                markerAK.setSubCaptionColor(Color.BLUE);
                markerAK.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAK);

                Marker markerAJ = new Marker();
                markerAJ.setPosition(new LatLng(37.528180, 126.796453));
                markerAJ.setCaptionText("오정도서관");
                markerAJ.setTag("AJ");
                markerAJ.setSubCaptionText("운영중");
                markerAJ.setSubCaptionColor(Color.BLUE);
                markerAJ.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAJ);

                Marker markerAL = new Marker();
                markerAL.setPosition(new LatLng(37.49479, 126.754049));
                markerAL.setCaptionText("동화도서관");
                markerAL.setTag("AL");
                markerAL.setSubCaptionText("운영중");
                markerAL.setSubCaptionColor(Color.BLUE);
                markerAL.setSubCaptionHaloColor(Color.rgb(200, 255, 200));
                markers.add(markerAL);

                for (Marker marker : markers) {
                    marker.setCaptionAlign(Align.Bottom);
                    marker.setSubCaptionTextSize(10);
                    marker.setIconPerspectiveEnabled(true);
                    marker.setIcon(getOverlayImage(marker.getTag().toString(), false));
                    marker.setWidth(60);
                    marker.setHeight(68);
                    marker.setOnClickListener(listener);
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
