package edu.pitt.api.neo4j.repository;

import edu.pitt.api.neo4j.domain.Neo4jUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
    public interface Neo4jUserRepository extends Neo4jRepository<Neo4jUser, Long> {

        Neo4jUser findOneByUsername(String username);

        Optional<Neo4jUser> findOneByUsernameAndPassword(String username, String password);

        Neo4jUser findOneByUsernameAndCityAndStateAndEmailAndPhonenumber(String username, String city, String state, String email, String phonenumber);

        Neo4jUser findOneByEmail(String email);

        Long deleteByUsername(String username);
    }