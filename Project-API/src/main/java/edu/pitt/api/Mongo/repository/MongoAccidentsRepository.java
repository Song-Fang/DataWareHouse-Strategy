package edu.pitt.api.Mongo.repository;

import edu.pitt.api.Mongo.models.MongoAccidents;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoAccidentsRepository extends MongoRepository<MongoAccidents, String>, CustomRepository
{
    // test for connection
    MongoAccidents findByid(String id);

    List<MongoAccidents> findAllBySource(String username);
    //search accidents by numbersByState

    List<MongoAccidents> getRecent100Reports();


    MongoAccidents findBy_id(String reportId);
}
