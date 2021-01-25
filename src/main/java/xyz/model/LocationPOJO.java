package xyz.model;
/*
"location":{
        "type":"Point",
        "coordinates":[
            23.855134,
            67.008691
            ]
    }
 */
import java.util.List;

public class LocationPOJO {
    private String type;
    //private Double[] coordinates;
    private List<Double> coordinates;

    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return this.coordinates;
    }
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}