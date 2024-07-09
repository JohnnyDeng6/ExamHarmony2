package ca.cmpt276.examharmony.Model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
    User findByEmailAddress(String emailAddress);
}
