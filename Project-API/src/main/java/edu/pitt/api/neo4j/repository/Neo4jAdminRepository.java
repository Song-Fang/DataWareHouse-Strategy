package edu.pitt.api.neo4j.repository;

import edu.pitt.api.neo4j.domain.Neo4jUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Neo4jAdminRepository extends Neo4jRepository<Neo4jUser, Long> {

    Neo4jUser findOneByUsernameAndPassword(String username, String password);

}
