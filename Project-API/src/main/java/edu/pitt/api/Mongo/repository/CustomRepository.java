package edu.pitt.api.Mongo.repository;
import edu.pitt.api.Mongo.models.MongoAccidents;

import java.util.List;
public interface CustomRepository
{
    List<Custom.CountState> getNumbersByState();
    List<Custom.CountState>getNumbersByCounty(String state);
    List<Custom.RoadLocation> getAccidentsByRoad(String state, String city, String street);
    List<Custom.CountVis>getNumbersByVisibility();
    List<Custom.CountVis>getNumbersByHumidity();
    List<Custom.CountVis>getNumbersByWeatherCondition();
    List<MongoAccidents>getRecent100Reports();


}
