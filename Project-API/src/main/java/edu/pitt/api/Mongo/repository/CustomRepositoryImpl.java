package edu.pitt.api.Mongo.repository;
import edu.pitt.api.Mongo.models.MongoAccidents;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
public class CustomRepositoryImpl implements CustomRepository
{
//    private final MongoOperations operations;
    private final MongoTemplate mongoTemplate;

    @Autowired
//    public CustomRepositoryImpl(MongoOperations operations) {this.operations = operations; }
    public CustomRepositoryImpl(MongoTemplate mongoTemplate){this.mongoTemplate = mongoTemplate;}

    @Override
    public List<Custom.CountState> getNumbersByState()
    {
        GroupOperation groupOperation = groupByState();
        ProjectionOperation projectionOperation = projectGroupByState();
        return mongoTemplate.aggregate(Aggregation.newAggregation(
                groupOperation,
                projectionOperation), MongoAccidents.class, Custom.CountState.class).getMappedResults();
    }

    private GroupOperation groupByState(){
        return group("state")
                .addToSet("state").as("state")
                .count().as("value");
    }

    private ProjectionOperation projectGroupByState()
    {
        return project("state","value")
                .and("state").previousOperation();
    }

    @Override
    public List<Custom.CountState> getNumbersByCounty(String state){
        Aggregation agg = newAggregation(
                match(Criteria.where("state").is(state)),
                group("county").count().as("value"),
                project("value").and("county").previousOperation());
        return mongoTemplate.aggregate(agg, MongoAccidents.class, Custom.CountState.class).getMappedResults();
    }

    @Override
    public List<Custom.RoadLocation> getAccidentsByRoad(String state, String city, String street)
    {
        Query locateByRoad = new Query();
        locateByRoad.addCriteria(Criteria.where("state").is(state));
        locateByRoad.addCriteria(Criteria.where("city").is(city));
        locateByRoad.addCriteria(Criteria.where("street").regex(street));
        List<MongoAccidents> acc = mongoTemplate.find(locateByRoad, MongoAccidents.class);
        List<Custom.RoadLocation> res = new ArrayList<>();

        acc.forEach(a -> {
            Custom.RoadLocation c = new Custom.RoadLocation();
            c.latitude = Float.parseFloat(a.lat);
            c.longitude = Float.parseFloat(a.lng);
            res.add(c);
        });
        return res;
    }

    @Override
    public List<Custom.CountVis>getNumbersByVisibility(){
        Aggregation agg = newAggregation(
                group("visibility")
                        .addToSet("visibility").as("label")
                        .count().as("value"),
                project("label","value").and("visibility").previousOperation());
        return mongoTemplate.aggregate(agg, MongoAccidents.class, Custom.CountVis.class).getMappedResults();
    }

    @Override
    public List<Custom.CountVis>getNumbersByHumidity(){
        Aggregation agg = newAggregation(
                group("humidity")
                        .addToSet("humidity").as("label")
                        .count().as("value"),
                project("label","value").and("humidity").previousOperation());
        return mongoTemplate.aggregate(agg, MongoAccidents.class, Custom.CountVis.class).getMappedResults();
    }

    @Override
    public List<Custom.CountVis>getNumbersByWeatherCondition(){
        Aggregation agg = newAggregation(
                group("weather_condition")
                        .addToSet("weather_condition").as("label")
                        .count().as("value"),
                project("label", "value").and("weather_condition").previousOperation());
        return mongoTemplate.aggregate(agg, MongoAccidents.class, Custom.CountVis.class).getMappedResults();
    }

    @Override
    public List<MongoAccidents> getRecent100Reports(){
        Query loadTop100 = new Query();
        loadTop100.limit(100);
        loadTop100.with(Sort.by(Sort.Direction.DESC, "startTime"));
        return mongoTemplate.find(loadTop100, MongoAccidents.class);
    }


}
