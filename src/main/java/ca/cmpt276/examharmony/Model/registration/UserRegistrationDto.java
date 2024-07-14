package ca.cmpt276.examharmony.Model.registration;

import java.util.Set;
import java.util.UUID;

public class UserRegistrationDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private UUID id;
    private UUID resetPasswordToken;
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

    public void setUUID(UUID id) {
        this.id = id;
    }
    public UUID getUUID() {
        return id;
    }

    public void setResetPasswordToken(UUID resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
    public UUID getResetPasswordToken() {
        return resetPasswordToken;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
