package ca.cmpt276.examharmony.Model;


import jakarta.persistence.*;

@Entity
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isInstructor;
    private boolean isInvigilator;
    private String emailAddress;

    public User createUser(String name, String password, boolean isAdmin, boolean isInstructor, boolean isInvigilator, String emailAddress){
        User newUser = new User();
        newUser.username = name;
        newUser.password = password;
        newUser.isAdmin = isAdmin;
        newUser.isInstructor = isInstructor;
        newUser.isInvigilator = isInvigilator;
        newUser.emailAddress= emailAddress;
        return newUser;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.username = name;
    }

    public void setInvigilator(boolean invigilator) {
        isInvigilator = invigilator;
    }

    public void setInstructor(boolean instructor) {
        isInstructor = instructor;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return username;
    }

    public boolean isInvigilator() {
        return isInvigilator;
    }

    public boolean isInstructor() {
        return isInstructor;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
