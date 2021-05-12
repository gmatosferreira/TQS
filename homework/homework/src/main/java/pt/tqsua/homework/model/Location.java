package pt.tqsua.homework.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Location {

    @Id
    private Integer id;
    private String idArea;
    private String name;
    private String latitude;
    private String longitude;

    public Location() {}

    public Location(Integer id, String idArea, String name, String latitude, String longitude) {
        this.id = id;
        this.idArea = idArea;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
