package ca.cmpt276.examharmony.Model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
    User findByEmailAddress(String emailAddress);
    User findByPasswordResetToken(UUID passwordResetToken);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    static
    List<User> findByRoleName(@Param("roleName") String roleName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByRoleName'");
    }
    
}
