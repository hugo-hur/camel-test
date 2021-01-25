/*{
    "trainNumber":76090,
    "departureDate":"2021-01-24",
    "timestamp":"2021-01-24T12:07:13.000Z",
    "location":{
        "type":"Point",
        "coordinates":[
            23.855134,
            67.008691
            ]
    },
    "speed":51
} */
package xyz.model;
import xyz.model.LocationPOJO;

public class TrainPOJO {

    private int trainNumber;
    private String departureDate;
    private String timestamp;
    private LocationPOJO location;
    private int speed;

    // standard getter and setters
    public int getTrainNumber() {
        return this.trainNumber;
    }
    public void setTrainNumber(int number) {
        this.trainNumber = number;
    }
    public String getDepartureDate() {
        return this.departureDate;
    }
    public void setDepartureDate(String date) {
        this.departureDate = date;
    }
    public String getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(String date) {
        this.timestamp = date;
    }
    public LocationPOJO getLocation(){
        return this.location;
    }
    public void setLocation(LocationPOJO location){
        this.location = location;
    }
    public int getSpeed(){
        return this.speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
}