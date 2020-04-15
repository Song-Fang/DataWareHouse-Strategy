package edu.pitt.api.neo4j.controller;
import edu.pitt.api.neo4j.Config.AppKeys;
import edu.pitt.api.neo4j.service.Neo4jAccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(AppKeys.NEO4J_API_PATH + "/accident")
@CrossOrigin
public class Neo4jAccidentController {
    @Autowired
    Neo4jAccidentService neo4jAccidentService;

    @GetMapping("/numbersByState")
    public List<Count> getNumbersByState(){
        return neo4jAccidentService.countByState();
    }



    @GetMapping(value = "/numbersByCounty/{state}")
    public List<Count> getNumbersByCounty(@PathVariable String state){
        return neo4jAccidentService.countByCounty(state);
    }

    @GetMapping(value = "/accidentsByRoad/{state}/{city}/{road}")
    public List<RoadLocation> getAccidentsByRoad(@PathVariable String state, @PathVariable String city, @PathVariable String road) {

        return neo4jAccidentService.getAccidentByRoad(state,city,road);
    }

    @GetMapping(value = "/numbersByVisibility")
    public List<CountWeatherCondition> getNumbersByVisibility(){
        return neo4jAccidentService.countByvisibility();
    }

    @GetMapping(value = "/numbersByHumidity")
    public List<CountWeatherCondition> getNumbersByHumidity(){
        return neo4jAccidentService.countByHumidity();
    }

    @GetMapping(value = "/numbersByWeatherCondition")
    public List<CountWeatherCondition> getNumbersByWeatherCondition(){
        return neo4jAccidentService.countByWeatherCondition();
    }



    @QueryResult
    public static class Count {
        String id;
        int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @QueryResult
    public class RoadLocation implements RoadLocationImp {
        float latitude;
        float longitude;

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }
    }

    public interface RoadLocationImp {
        float getLatitude();

        void setLatitude(float latitude);

        float getLongitude();

        void setLongitude(float longitude);
    }

    public interface CountVisibilityImp {
        String getLabel();

        void setLabel(Double label);

        long getValue();

        void setValue(long value);
    }

    @QueryResult
    public static class CountVisibility implements CountVisibilityImp {
        String label;
        long value;

        public String getLabel() {
            return label;
        }

        public void setLabel(Double label) {
            this.label = String.valueOf(label);
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
//
//    @QueryResult
//    public  class CountHumidity {
//        Double humidity;
//        int number;
//
//        public Double getHumidity() {
//            return humidity;
//        }
//
//        public void setHumidity(Double humidity) {
//            this.humidity = humidity;
//        }
//
//        public int getNumber() {
//            return number;
//        }
//
//        public void setNumber(int number) {
//            this.number = number;
//        }
//    }

    @QueryResult
    public class CountWeatherCondition {
        String label;
        long value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }





}
