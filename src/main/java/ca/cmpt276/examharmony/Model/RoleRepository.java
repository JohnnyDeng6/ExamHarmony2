package ca.cmpt276.examharmony.Model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}


// package ca.cmpt276.examharmony.Model;

// import org.springframework.data.jpa.repository.JpaRepository;

// import ca.cmpt276.examharmony.Model.user.User;

// import java.util.List;

// public interface RoleRepository extends JpaRepository<User, Integer> {
//     static List<User> findByName(String roleName) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'findByName'");
//     }
// }

