package pt.tqsua.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

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

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("globalIdLocal")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("id")
    public void setId2(Integer id) {
        this.setId(id);
    }

    @JsonProperty("idArea")
    public String getIdArea() {
        return idArea;
    }

    @JsonProperty("idAreaAviso")
    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    @JsonProperty("idArea")
    public void setIdArea2(String idArea) {
        this.setIdArea(idArea);
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("local")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public void setName2(String name) {
        this.setName(name);
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

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", idArea='" + idArea + '\'' +
                ", name='" + name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
