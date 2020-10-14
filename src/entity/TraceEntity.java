package entity;

import java.io.Serializable;

public class TraceEntity implements Serializable {
    private int id, sportId;
    private double sportTime, latitude, longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public double getSportTime() {
        return sportTime;
    }

    public void setSportTime(double sportTime) {
        this.sportTime = sportTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
