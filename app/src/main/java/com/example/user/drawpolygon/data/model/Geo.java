package com.example.user.drawpolygon.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geo {
    @SerializedName("GeoFenceID")
    @Expose
    private Integer geoFenceID;
    @SerializedName("PointIndex")
    @Expose
    private Integer pointIndex;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;

    public Geo() {
    }

    public Geo(Integer pointIndex, Double latitude, Double longitude) {
        this.pointIndex = pointIndex;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Geo(Integer geoFenceID, Integer pointIndex, Double latitude, Double longitude) {
        this.geoFenceID = geoFenceID;
        this.pointIndex = pointIndex;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getGeoFenceID() {
        return geoFenceID;
    }

    public void setGeoFenceID(Integer geoFenceID) {
        this.geoFenceID = geoFenceID;
    }

    public Integer getPointIndex() {
        return pointIndex;
    }

    public void setPointIndex(Integer pointIndex) {
        this.pointIndex = pointIndex;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
