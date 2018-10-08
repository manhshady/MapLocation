package com.example.user.drawpolygon;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.drawpolygon.data.model.Geo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> locationList;
    private PolygonOptions polygonOptions;
    private  ArrayList<Geo> geoArrayList;
    private ArrayList<LatLng> latLngArrayList;
    private LatLng currentPosition;
    private LatLng tempLatLng;

    @Override
    protected void onStart() {
        super.onStart();
//        locationList = readSharedLocationList();
//
//        polygonOptions = new PolygonOptions();
//        if (!locationList.isEmpty()){
//            for (LatLng latLng: locationList) {
//                polygonOptions.add(latLng);
//            }
//        }
//        Log.d("LOCATION LIST:" , polygonOptions.toString());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        TinyDB tinyDB = new TinyDB(getApplicationContext());
        ArrayList<Object> geoObjects = tinyDB.getListObject("GeoList", Geo.class);
        currentPosition = tinyDB.getObject("Current location", LatLng.class);
        latLngArrayList = new ArrayList<>();
        geoArrayList = new ArrayList<>();
        if(!geoObjects.isEmpty()) {

            for (Object object : geoObjects) {

                geoArrayList.add((Geo) object);

            }
            Log.i("GEO LIST", geoArrayList.toString());

        }
        
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (!geoArrayList.isEmpty()) {
            polygonOptions = new PolygonOptions();
            for (Geo g : geoArrayList) {
                tempLatLng = new LatLng(g.getLatitude(), g.getLongitude());
               latLngArrayList.add(tempLatLng);
                Log.i("Latlng", tempLatLng.toString());

            }
            polygonOptions.addAll(latLngArrayList);
            Polygon polygon = mMap.addPolygon(polygonOptions);

            polygon.setTag("alpha");
        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(currentPosition).title("Current position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 17.0f) );
    }

//    private List<LatLng> readSharedLocationList(){
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        Gson gson = new Gson();
//
//        String json = sharedPrefs.getString(MainActivity.class.getSimpleName(), "");
//        Type type = new TypeToken<List<LatLng>>() {}.getType();
//        List<LatLng> arrayList = gson.fromJson(json, type);
//        return arrayList;
//    }
}
