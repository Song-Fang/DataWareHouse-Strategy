package edu.pitt.api.Mongo.repository;

import edu.pitt.api.Mongo.models.MongoUsers;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAdminRepository extends MongoRepository<MongoUsers, String>
{


}
