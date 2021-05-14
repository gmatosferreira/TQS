package pt.tqsua.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UVIndex {

    private static final Logger log = LoggerFactory.getLogger(UVIndex.class);

    private int start;
    private int end;
    private Date date;
    private double index;
    private int location;
    @JsonProperty("indexClass")
    private String indexClass;

    public UVIndex() {
    }

    public UVIndex(int location, int start, int end, Date date, double index) {
        this.location = location;
        this.start = start;
        this.end = end;
        this.date = date;
        this.index = index;
    }

    @JsonProperty("location")
    public int getLocation() {
        return location;
    }

    @JsonProperty("globalIdLocal")
    public void setLocation(int location) {
        this.location = location;
    }

    @JsonProperty("location")
    public void setLocation2(int location) {
        this.setLocation(location);
    }

    @JsonProperty("intervaloHora")
    public void setInterval(String interval) {
        String[] timespan = interval.split("-");
        this.start = Integer.parseInt(timespan[0].replace("h", ""));
        this.end = Integer.parseInt(timespan[1].replace("h", ""));
    }

    @JsonProperty("start")
    public int getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(int start) {
        this.start = start;
    }

    @JsonProperty("end")
    public int getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(int end) {
        this.end = end;
    }

    @JsonProperty("date")
    public Date getDate() {
        return date;
    }

    @JsonProperty("data")
    public void setDate(Date date) {
        this.date = date;
    }

    @JsonProperty("date")
    public void setDate2(Date date) {
        this.setDate(date);
    }

    @JsonProperty("index")
    public double getIndex() {
        return index;
    }

    @JsonProperty("iUv")
    public void setIndex(double index) {
        this.index = index;
        // Define class
        if (index>=11) {
            this.indexClass = "Extremo";
        } else if (index>=8) {
            this.indexClass = "Muito Elevado";
        } else if (index>=6) {
            this.indexClass = "Elevado";
        } else if (index>=3) {
            this.indexClass = "Moderado";
        } else {
            this.indexClass = "Baixo";
        }
    }

    @JsonProperty("index")
    public void setIndex2(double index) {
        this.setIndex(index);
    }

    public String getIndexClass() {
        return indexClass;
    }

    public void setIndexClass(String indexClass) {
        this.indexClass = indexClass;
    }

    @Override
    public String toString() {
        return "UVIndex{" +
                "start=" + start +
                ", end=" + end +
                ", date=" + date +
                ", index=" + index +
                ", location=" + location +
                '}';
    }

    /**
     * Validates if day index matches
     * Ex. 0 is today, 1 tomorrow, ...
     * @param day
     * @return isDay true if index matches
     */
    public boolean isDay(int day) {
        log.debug("Difference between {} and {}:", this.date, new Date());
        /*
        long diffInMillies = Math.abs(this.date.getTime()-new Date().getTime());
        int diff = (int)TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (diff == day) {
            return true;
        }
        */
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        Calendar indexDate = Calendar.getInstance();
        indexDate.setTime(this.date);

        int diff = indexDate.get(Calendar.DAY_OF_MONTH)-now.get(Calendar.DAY_OF_MONTH);
        log.debug("Returned {} days", diff);
        if (diff == day) {
            return true;
        }
        return false;
    }


}
