package com.example.user.drawpolygon.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomGeofence {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("geo")
    @Expose
    private List<Geo> geo = null;

    public CustomGeofence() {
    }

    public CustomGeofence(Integer id, String name, List<Geo> geo) {
        this.id = id;
        this.name = name;
        this.geo = geo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Geo> getGeo() {
        return geo;
    }

    public void setGeo(List<Geo> geo) {
        this.geo = geo;
    }

    @Override
    public String toString() {
        return "CustomGeofence{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", geo=" + geo +
                '}';
    }
}
