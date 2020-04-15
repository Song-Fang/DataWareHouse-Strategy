package edu.pitt.api.neo4j.repository;

import edu.pitt.api.neo4j.controller.Neo4jAccidentController;
import edu.pitt.api.neo4j.domain.Neo4jAccident;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface Neo4jAccidentRepository extends Neo4jRepository<Neo4jAccident,Long> {

    List<Neo4jAccident> findAllBySource(String username);

    Neo4jAccident findOneById(Long reportId);

//    p.state as state, p.city as city, p.street as street, p.zipcode as zipcode, p.latitude as latitude," +
//            "p.longitude as longitude, p.visibility as visibility, p.humidity as humidity, p.starttime as starttime order by p.starttime


    @Query(value = "match (p) return p limit 100")
    List<Neo4jAccident> findFirst100OrderByStartTimeDesc();

    @Query("match (p) return p.state AS id, count(*) AS value")
    List<Neo4jAccidentController.Count> accidentCountByState();

    @Query("match (p) where p.state=$state return p.county AS location, count(*) AS number")
    List<Neo4jAccidentController.Count> countByCounty(@Param("state") String state);

    @Query("match (p) where p.city=$city and p.state=$state and p.street =$street return p.latitude AS latitude, p.longitude AS longitude")
    List<Neo4jAccidentController.RoadLocation> getAccidentsByRoad(@Param("state") String state, @Param("city") String city, @Param("street") String street);

    @Query("match(p) return p.visibility AS label,count(*) AS value")
    List<Neo4jAccidentController.CountWeatherCondition> countByVisibility();


    @Query("match(p) return p.humidity AS label, count(*) AS value")
    List<Neo4jAccidentController.CountWeatherCondition> countByHumidity();


    @Query("match(p) return p.weatherCondition AS label, count(*) AS value")
    List<Neo4jAccidentController.CountWeatherCondition> countByWeatherCondition();
}
