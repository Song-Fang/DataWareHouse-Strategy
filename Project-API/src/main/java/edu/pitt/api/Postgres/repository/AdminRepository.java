package edu.pitt.api.Postgres.repository;

import edu.pitt.api.Postgres.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<User, Long> {
    User findOneByUsernameAndPassword(String username, String password);
}
