package edu.pitt.api.Mongo.repository;

import edu.pitt.api.Mongo.models.MongoUsers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoUsersRepository extends MongoRepository<MongoUsers,String > {

    @Query(value="{ '_id' : ?0 }", fields="{ 'name' : 1, 'email' : 1}")
    List<MongoUsers> findByThePersonsid(String id);


    @Query(value = "{'username':?0,'city':?1,'state':?2,'email':?3,'phonenumber':?4}")
    MongoUsers checkInfo(String username, String city, String state, String email, String phone);


    List<MongoUsers> findOneByUsername(String username);

    List<MongoUsers> findUsersByUsername(String username);

    MongoUsers findUsersByUsernameIs(String username);

    MongoUsers findUsersByEmailIs(String email);

    MongoUsers findUsersByUsernameIsAndPasswordIs(String username, String pwd);

    boolean existsUsersByUsername(String username);


}
