package com.example.user.drawpolygon.data.remote;

import com.example.user.drawpolygon.data.model.CustomGeofence;
import com.example.user.drawpolygon.data.model.UserLocation;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {
    @GET("/geo/all")
    Call<List<CustomGeofence>> getCustomGeofences();

    @GET("/geo/detail")
    Call<List<CustomGeofence>> getCustomGeofence(@Query("id") int id);

    @POST("/geo/insert")
    @Headers( {"Content-Type: application/json ",
            "Accept: application/json"})
    Call<ResponseBody> saveCustomGeofence(@Body CustomGeofence customGeofence);

    @POST("/track/insert")
    @Headers( {"Content-Type: application/json ",
            "Accept: application/json"})
    Call<ResponseBody> saveUserLocation(@Body UserLocation userLocation);

}
