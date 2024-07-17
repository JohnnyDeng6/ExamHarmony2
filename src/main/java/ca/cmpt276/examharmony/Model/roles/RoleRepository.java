package ca.cmpt276.examharmony.Model.roles;

import ca.cmpt276.examharmony.Model.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
