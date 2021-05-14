package pt.tqsua.homework.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.tqsua.homework.model.enums.AwarenessLevel;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Warning {

    private String name;
    private String text;
    private LocalDateTime start;
    private LocalDateTime end;
    private AwarenessLevel level;
    private String location;

    public Warning() {}

    public Warning(String name, String text, LocalDateTime start, LocalDateTime end, AwarenessLevel level, String location) {
        this.name = name;
        this.text = text;
        this.start = start;
        this.end = end;
        this.level = level;
        this.location = location;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("awarenessTypeName")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public void setName2(String name) {
        this.setName(name);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("level")
    public String getLevel() {
        return level.toString();
    }

    @JsonProperty("awarenessLevelID")
    public void setLevel(AwarenessLevel level) {
        this.level = level;
    }

    @JsonProperty("level")
    public void setLevel2(AwarenessLevel level) {
        this.setLevel(level);
    }

    @JsonProperty("start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getStart() {
        return start;
    }

    @JsonProperty("startTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @JsonProperty("start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public void setStart2(LocalDateTime start) {
        this.setStart(start);
    }

    @JsonProperty("end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getEnd() {
        return end;
    }

    @JsonProperty("endTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @JsonProperty("end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public void setEnd2(LocalDateTime end) {
        this.setEnd(end);
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("idAreaAviso")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("location")
    public void setLocation2(String location) {
        this.setLocation(location);
    }

    @Override
    public String toString() {
        return "Warning{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", level=" + level +
                ", location=" + location +
                '}';
    }
}
