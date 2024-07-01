package ca.cmpt276.examharmony.Model;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String name);
    User findByEmailAddress(String emailAddress);

}
