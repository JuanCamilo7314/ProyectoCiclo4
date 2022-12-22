package com.g104g5.appciclo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class Maps extends AppCompatActivity {
    private MapView map;
    private MapController mapController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        mapController = (MapController) map.getController();
        GeoPoint colombia = new GeoPoint(4.570868,-74.297333);
        mapController.setCenter(colombia);
        mapController.setZoom(8);
        map.setMultiTouchControls(true);

        Intent intentIn = getIntent();
        ArrayList<String> latitudes = intentIn.getStringArrayListExtra("latitudes");
        ArrayList<String> longitudes = intentIn.getStringArrayListExtra("longitudes");

        for (int i = 0; i<latitudes.size();i++){
            GeoPoint geoPoint = new GeoPoint(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i)));
            Marker marker = new Marker(map);
            marker.setPosition(geoPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(marker);
        }

    }
}