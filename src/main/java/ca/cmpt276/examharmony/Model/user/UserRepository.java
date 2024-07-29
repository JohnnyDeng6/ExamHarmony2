package ca.cmpt276.examharmony.Model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User findByUsername(String username);
    @Query(value = "SELECT * FROM users WHERE email_address = :emailAddress", nativeQuery = true)
    User findByEmailAddress(String emailAddress);
    User findByPasswordResetToken(String passwordResetToken);

    // Custom query to find users by role name
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRoleName(@Param("roleName") String roleName);
    

}
