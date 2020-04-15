package edu.pitt.api.Postgres.repository;
import edu.pitt.api.Postgres.controllers.AccidentController;
import edu.pitt.api.Postgres.models.Accidents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AccidentRepository extends JpaRepository<Accidents, Long> {
    List<Accidents> findAllBySource(String username);

    Accidents findOneById(Long reportId);

    @Query(value = "select * from Accidents a order by a.starttime desc limit 100", nativeQuery = true)
    List<Accidents> findFirst100OrderByStartTimeDesc();

    @Query(value = "select a.state as id, count(a) as value from Accidents a group by a.state")
    List<AccidentController.CountImp> countByState();

    @Query("select a.county as location, count(a) as number from Accidents a where a.state=:state group by a.county")
    List<AccidentController.CountImp> countByCounty(@Param("state") String state);

    @Query("select a.latitude as latitude, a.longitude as longitude from Accidents a where a.state =:state and a.city=:city and a.street like :street")
    List<AccidentController.RoadLocationImp> getAccidentsByRoad(@Param("state") String state, @Param("city") String city, @Param("street") String street);

    @Query("select a.visibility as label , count(a) as value from Accidents a where a.visibility != 0 group by a.visibility")
    List<AccidentController.CountVisibilityImp> countByVisibility();


    @Query("select a.humidity as label, count(a) as value from Accidents a group by a.humidity")
    List<AccidentController.CountVisibilityImp> countByHumidity();


    @Query("select a.weathercondition as label, count(a) as value from Accidents a group by a.weathercondition")
    List<AccidentController.CountWeatherConditionImp> countByWeatherCondition();
}
