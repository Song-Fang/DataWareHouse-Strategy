package edu.pitt.api.Mongo.controllers;

import edu.pitt.api.Mongo.config.AppKeys;
import edu.pitt.api.Mongo.models.MongoAccidents;
import edu.pitt.api.Mongo.repository.Custom;
import edu.pitt.api.Mongo.repository.MongoAccidentsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(AppKeys.Mongo_API_PATH+"/accident")
public class MongoAccidentsController
{
    @Autowired
    private MongoAccidentsRepository mongoAccidentsRepository;
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public List<MongoAccidents> getAllAccidents() {
//        return mongoAccidentsRepository.findAll();
//    }

//    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
//    public MongoAccidents getAccidentsById(@PathVariable("id") String id)
//    {
//        return mongoAccidentsRepository.findByid(id);
//    }

    @RequestMapping(value = "/numbersByState", method = RequestMethod.GET)
    public List<Custom.Map> getNumbersByState()
    {
        List<Custom.Map> newlist  = new ArrayList<>();
        List<Custom.CountState> list = mongoAccidentsRepository.getNumbersByState();
        list.forEach(l -> {
            Custom.Map map = new Custom.Map();
            if (l.getState() != null) {
                map.setId(l.getState());
                map.setValue(l.getValue());
                newlist.add(map);
            }
        });
        return newlist;
    }

    @RequestMapping(value = "/numbersByCounty/{state}", method = RequestMethod.GET)
    public List<Custom.CountState> getNumbersByCounty(@PathVariable("state") String state)
    {
        return mongoAccidentsRepository.getNumbersByCounty(state);
    }

    @RequestMapping(value="/accidentsByRoad/{state}/{city}/{street}", method = RequestMethod.GET)
    public List<Custom.RoadLocation> getAccidentsByRoad(@PathVariable String state, @PathVariable String city, @PathVariable String street)
    {
        return mongoAccidentsRepository.getAccidentsByRoad(state, city, street);
    }

    @RequestMapping(value="/numbersByVisibility", method = RequestMethod.GET)
    public List<Custom.CountVis>getNumbersByVisibility()
    {
        return mongoAccidentsRepository.getNumbersByVisibility();
    }

    @RequestMapping(value="/numbersByHumidity", method = RequestMethod.GET)
    public List<Custom.CountVis>getNumbersByHumidity()
    {
        return mongoAccidentsRepository.getNumbersByHumidity();
    }

    @RequestMapping(value="/numbersByWeatherCondition", method = RequestMethod.GET)
    public List<Custom.CountVis>getNumbersByWeatherCondition()
    {
        return mongoAccidentsRepository.getNumbersByWeatherCondition();
    }



}


