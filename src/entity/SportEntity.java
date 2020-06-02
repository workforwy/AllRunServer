package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SportEntity implements Serializable {
    private int id;
    private String username, sportType;
    private List<TraceEntity> listTraceEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public List<TraceEntity> getListTraceEntity() {
        return listTraceEntity;
    }

    public void setListTraceEntity(ArrayList<TraceEntity> listTraceEntity) {
        this.listTraceEntity = listTraceEntity;
    }


}
