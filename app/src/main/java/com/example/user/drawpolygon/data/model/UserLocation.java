package com.example.user.drawpolygon.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLocation {
    @SerializedName("WorkID")
    @Expose
    private String workID;

    @SerializedName("Latitude")
    @Expose
    private Double latitude;

    @SerializedName("Longitude")
    @Expose
    private Double longitude;

    @SerializedName("PositionTimeStamp")
    @Expose
    private String positionTimeStamp;

    @SerializedName("EventID")
    @Expose
    private Integer eventID;

    public UserLocation() {
    }

    public UserLocation(String workID, Double latitude, Double longitude, String positionTimeStamp, Integer eventID) {
        this.workID = workID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.positionTimeStamp = positionTimeStamp;
        this.eventID = eventID;
    }

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
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

    public String getPositionTimeStamp() {
        return positionTimeStamp;
    }

    public void setPositionTimeStamp(String positionTimeStamp) {
        this.positionTimeStamp = positionTimeStamp;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "workID='" + workID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", positionTimeStamp='" + positionTimeStamp + '\'' +
                ", eventID=" + eventID +
                '}';
    }
}
