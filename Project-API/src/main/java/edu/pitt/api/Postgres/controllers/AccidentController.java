package edu.pitt.api.Postgres.controllers;

import edu.pitt.api.Postgres.config.AppKeys;
import edu.pitt.api.Postgres.repository.AccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AppKeys.Postgres_API_PATH + "/accident")
public class AccidentController {

    @Autowired
    AccidentRepository accidentRepository;

    @GetMapping(value = "/numbersByState")
    public List<CountImp> getNumbersByState() {
        
        return this.accidentRepository.countByState();


    }

    @GetMapping(value = "/numbersByCounty/{state}")
    public List<CountImp> getNumbersByCounty(@PathVariable String state) {
        return accidentRepository.countByCounty(state);
    }

    @GetMapping(value = "/accidentsByRoad/{state}/{city}/{road}")
    public List<RoadLocationImp> getAccidentsByRoad(@PathVariable String state, @PathVariable String city, @PathVariable String road) {
        if (accidentRepository.getAccidentsByRoad(state, city, "%" + road + "%").size() == 0) {
            throw new RuntimeException("No Record is founded !");
        } else {
            return accidentRepository.getAccidentsByRoad(state, city, "%" + road + "%");
        }
    }

    @GetMapping(value = "/numbersByVisibility")
    public List<CountVisibilityImp> getNumbersByVisibility() {
        return accidentRepository.countByVisibility();
    }

    @GetMapping(value = "/numbersByHumidity")
    public List<AccidentController.CountVisibilityImp> getNumbersByHumidity() {
        return accidentRepository.countByHumidity();
    }

    @GetMapping(value = "/numbersByWeatherCondition")
    public List<AccidentController.CountWeatherConditionImp> getNumbersByWeatherCondition() {
        return accidentRepository.countByWeatherCondition();
    }


    public interface CountImp {
        int getValue();

        void setValue(int value);

        String getId();

        void setId(String id);
    }

    public interface CountVisibilityImp {
        String getLabel();

        void setLabel(Double label);

        long getValue();

        void setValue(long value);

    }


    public interface CountWeatherConditionImp {
        String getLabel();

        void setLabel(String Label);

        long getValue();

        void setValue(long value);
    }

    public interface RoadLocationImp {
        float getLatitude();

        void setLatitude(String latitude);

        float getLongitude();

        void setLongitude(String longitude);
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

    @QueryResult
    public class Count implements CountImp {
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
    public class CountWeatherCondition implements CountWeatherConditionImp {
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

    @QueryResult
    public class RoadLocation implements RoadLocationImp {
        float latitude;
        float longitude;

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            if (latitude == null) return;
            this.latitude = Float.parseFloat(latitude);
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            if (longitude == null) return;
            this.longitude = Float.parseFloat(longitude);
        }
    }

}
