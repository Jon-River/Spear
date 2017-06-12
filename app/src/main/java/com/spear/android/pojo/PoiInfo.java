package com.spear.android.pojo;

/**
 * Created by Pablo on 11/6/17.
 */

public class PoiInfo {

    String latitude, longitude, description;
    long timestamp;

    public PoiInfo() {
    }

    public PoiInfo(String latitude, String longitude, String description, long timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.timestamp = timestamp;
    }



    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
