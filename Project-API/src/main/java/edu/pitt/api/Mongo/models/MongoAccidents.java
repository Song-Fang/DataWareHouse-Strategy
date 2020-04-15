package edu.pitt.api.Mongo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("accidents")
public class MongoAccidents
{
    @Id
    public ObjectId _id;
    public String id;
    public String source;
    public String severity;
    public String startTime;
    public String endTime;
    public String lat;
    public String lng;
    public String distance;
    public String description;
    public String number;
    public String street;
    public String side;
    public String city;
    public String county;
    public String state;
    public String zipcode;
    public String country;
    public String temperature;
    public String humidity;
    public String visibility;
    public String wind_speed;
    public String weather_condition;

    //Constructor
    public MongoAccidents(){}
    public MongoAccidents(ObjectId _id, String id, String source, String severity, String startTime,
                          String endTime, String lat, String lng,
                          String distance, String description, String number, String street,
                          String side, String city, String county, String state, String zipcode,
                          String country, String temperature, String humidity,
                          String visibility, String wind_speed, String weather_condition)
    {
        this._id = _id;
        this.id = id;
        this.severity = severity;
        this.source = source;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
        this.distance = distance;
        this.number = number;
        this.street = street;
        this.side = side;
        this.city = city;
        this.county = county;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.temperature = temperature;
        this.humidity = humidity;
        this.visibility = visibility;
        this.wind_speed = wind_speed;
        this.weather_condition = weather_condition;
    }
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStart_time() {
        return startTime;
    }

    public void setStart_time(String startTime) {
        this.startTime = startTime;
    }

    public String getEnd_time() {
        return endTime;
    }

    public void setEnd_time(String endTime) {
        this.endTime = endTime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibity) {
        this.visibility = visibity;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWeather_condition() {
        return weather_condition;
    }

    public void setWeather_condition(String weathercondition) {
        this.weather_condition = weathercondition;
    }

}
