package ca.cmpt276.examharmony.Model.roles;

import jakarta.persistence.*;

//Table which stores the roles of users (admin, instructor, invigilator)
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Role createRole(String roleName){
        Role newRole = new Role();
        newRole.name = roleName;
        return newRole;
    }
}
