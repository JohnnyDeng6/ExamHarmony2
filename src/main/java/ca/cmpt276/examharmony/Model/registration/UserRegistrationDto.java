package ca.cmpt276.examharmony.Model.registration;

import ca.cmpt276.examharmony.Model.Role;

import java.util.Set;

public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private int id;
    private Set<String> role;


    public UserRegistrationDto(String name, String email, String password, Set<String> role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserRegistrationDto() {
    }

    // Getters and Setters

    public void setID(int id) {
        this.id = id;
    }
    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return role;
    }

    public void setRoles(Set<String> role) {
        this.role = role;
    }
}
