package edu.pitt.api.Postgres.models;

import javax.persistence.*;

@Entity
@Table(name = "accidents")
public class Accidents {

    @Id
    @GeneratedValue()
    @SequenceGenerator(
            name = "accidents_generator",
            sequenceName = "accidents_sequence",
            initialValue = 1
    )
    long id;

    @Column
    String source;

    @Column
    String latitude;

    @Column
    String longitude;

    @Column
    String description;

    @Column
    String street;

    @Column
    String city;

    @Column
    String county;

    @Column
    String state;

    @Column
    String zipcode;

    @Column
    String starttime;

    @Column(nullable = false)
    int humidity = 0;

    @Column(nullable = false)
    double visibility = 0.0;

    @Column
    String weathercondition;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {

        this.visibility = visibility;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWeathercondition() {
        return weathercondition;
    }

    public void setWeathercondition(String weathercondition) {
        this.weathercondition = weathercondition;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStartTime() {
        return starttime;
    }

    public void setStartTime(String starttime) {
        this.starttime = starttime;
    }
}
