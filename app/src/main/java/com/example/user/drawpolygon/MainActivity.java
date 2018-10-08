package com.example.user.drawpolygon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.drawpolygon.data.model.CustomGeofence;
import com.example.user.drawpolygon.data.model.Geo;
import com.example.user.drawpolygon.data.model.UserLocation;
import com.example.user.drawpolygon.data.remote.APIService;
import com.example.user.drawpolygon.data.remote.ApiUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity  {

    private final static String TAG = MainActivity.class.getSimpleName();
    private static final String[] TABLE_HEADERS = { "ID", "Latitude", "Longitude"};
    private static  String[][] DATA_TO_SHOW = {{"1", "2", "3"},
            {"4", "5", "6"}};
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Button btnGetLocation;
    private Button btnTrackGPS;
    private Button btnClear;
    private Button btnSave;
    private Button btnAddlocation;
    private EditText etGeoName;
    private TextView tvLocation;
    private  de.codecrafters.tableview.TableView tableViewLocation;

    private ArrayList<LatLng> locationList;
    private ArrayList<Geo> geoList;
    private int locationID = 0;
    private LatLng tempLatLng;
    private Geo tempGeo;
    private CustomGeofence tempGeofence;
    private UserLocation tempUserLocation;
    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAPIService = ApiUtils.getAPIService();

        locationList = new ArrayList<>();
        geoList = new ArrayList<>();
        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnSave = findViewById(R.id.btnSave);
        btnTrackGPS = findViewById(R.id.btnTrackGPS);
        btnClear = findViewById(R.id.btnClear);
        btnAddlocation = findViewById(R.id.btnAddLocation);
        etGeoName = findViewById(R.id.etGeoName);

        tableViewLocation = findViewById(R.id.tableView);
        tableViewLocation.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));


        tvLocation = findViewById(R.id.tvLocation);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
                if(!Permissions.Check_FINE_LOCATION(MainActivity.this) ){
                    Permissions.Request_FINE_LOCATION(MainActivity.this, 22);
                } else  {

                    mFusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            tempLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                            String locationPoint =  " Lat: " + location.getLatitude() + " Lon: " + location.getLongitude() + "\n";
                            tvLocation.setText("Location: " + locationPoint);
                        }
                    });
                }
            }
        });

        btnAddlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tempLatLng != null) {
                    locationID++;
                    tempGeo = new Geo(locationID, tempLatLng.latitude, tempLatLng.longitude);
//                geo.setPointIndex(locationID);
//                geo.setLatitude(tempLatLng.latitude);
//                geo.setLongitude(tempLatLng.longitude);
                    geoList.add(tempGeo);


                    GeoTableDataAdapter geoTableDataAdapter = new GeoTableDataAdapter(getApplicationContext(), geoList);
                    tableViewLocation.setDataAdapter(geoTableDataAdapter);

                } else{
                    //Handle exception here i.e: Not getting the current location first
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationID = 0;
                geoList.clear();
                GeoTableDataAdapter geoTableDataAdapter = new GeoTableDataAdapter(getApplicationContext(), geoList);
                tableViewLocation.setDataAdapter(geoTableDataAdapter);
            }
        });

        btnTrackGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempLatLng != null) {
                    //Get current date time
                    DateFormat dateFormat =  SimpleDateFormat.getDateTimeInstance();
                    String datetime = dateFormat.format(Calendar.getInstance().getTime());

                    tempUserLocation = new UserLocation("123", tempLatLng.latitude, tempLatLng.longitude, datetime, 3);

                    sendUserLocation(tempUserLocation);
                    shareToMap(tempLatLng, geoList);

//                shareLocationListToMap(locationList);
//                Log.d(TAG, locationList.toString());
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
                } else{
                    //Handle exception here i.e: Not getting the current location first
                }
            }
        });

        //Send the geofence
        //TODO: implement this button _X
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geoName = etGeoName.getText().toString();
                if(!geoList.isEmpty() && !geoName.matches("") ){
                    tempGeofence = new CustomGeofence(-1, geoName, geoList);
                    sendCustomGeofence(tempGeofence);
                }else{
                    //Handle exception here
                   if(geoName.matches("")){
                       Toast.makeText(getApplicationContext(), "Enter the Geofence's name ", Toast.LENGTH_LONG).show();
                   } else if (geoList.isEmpty()){
                       Toast.makeText(getApplicationContext(), "Geofence has no geo point to submit", Toast.LENGTH_LONG).show();
                   }


                }
            }
        });
    }



    private void shareToMap(LatLng currentLatLng, ArrayList<Geo> geoList){
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        tinyDB.putObject("Current location", tempLatLng);
        ArrayList<Object> objectArrayList = new ArrayList<>();
        for (Geo g: geoList){
            objectArrayList.add((Object) g);
        }
        tinyDB.putListObject("GeoList",objectArrayList);
    }

//    private void shareLocationListToMap(ArrayList<LatLng> latLngArrayList){
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        Gson gson = new Gson();
//
//        String json = gson.toJson(latLngArrayList);
//
//        editor.putString(TAG, json);
//        editor.commit();
//    }

    //Save the Geofence data to the database
    private void sendCustomGeofence(CustomGeofence customGeofence){
        mAPIService.saveCustomGeofence(customGeofence).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "Geofence submitted to API. " + response.body().toString());
                    Toast.makeText(getApplicationContext(), "Geofence submitted", Toast.LENGTH_LONG).show();
                } else{
                    int statusCode = response.code();
                    Log.e(TAG, "Response code: " + statusCode);
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Unable to submit geofence to API.");
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO: response code 500, response should be 1 if success => fixed _X
    //Send user/device's current location to the server
    private void sendUserLocation(UserLocation userLocation){
        Log.i(TAG, userLocation.toString() );
       mAPIService.saveUserLocation(userLocation).enqueue(new Callback<ResponseBody>() {

           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if(response.isSuccessful()){
                   Log.i(TAG, "User location submitted to API. " + response.body().toString());
               } else{
                   int statusCode = response.code();
                   Log.e(TAG, "Response code: " + statusCode);
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               Log.e(TAG, "Unable to submit user location to API.");
           }
       });
    }

    private void setupLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
