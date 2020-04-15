package edu.pitt.api.neo4j.service;

import edu.pitt.api.neo4j.controller.Neo4jAccidentController;
import edu.pitt.api.neo4j.domain.Neo4jAccident;
import edu.pitt.api.neo4j.repository.Neo4jAccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class Neo4jAccidentService {
    @Autowired
    Neo4jAccidentRepository neo4jAccidentRepository;

    public List<Neo4jAccidentController.Count> countByState(){
        return neo4jAccidentRepository.accidentCountByState();
    }

    public List<Neo4jAccidentController.Count> countByCounty(String state){
        return neo4jAccidentRepository.countByCounty(state);
    }

    public List<Neo4jAccidentController.RoadLocation> getAccidentByRoad(String state, String city, String street){
        return neo4jAccidentRepository.getAccidentsByRoad(state,city,street);
    }

    public List<Neo4jAccidentController.CountWeatherCondition> countByvisibility(){
        return neo4jAccidentRepository.countByVisibility();
    }

    public List<Neo4jAccidentController.CountWeatherCondition> countByHumidity(){
        return neo4jAccidentRepository.countByHumidity();
    }

    public List<Neo4jAccidentController.CountWeatherCondition> countByWeatherCondition(){
        return neo4jAccidentRepository.countByWeatherCondition();
    }

}
