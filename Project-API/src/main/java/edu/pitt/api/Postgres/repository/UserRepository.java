package edu.pitt.api.Postgres.repository;

import edu.pitt.api.Postgres.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByUsername(String username);

    Optional<User> findOneByUsernameAndPassword(String username, String password);

    User findOneByUsernameAndCityAndStateAndEmailAndPhonenumber(String username, String city, String state, String email, String phonenumber);

    User findOneByEmail(String email);

    void deleteByUsername(String username);
}
