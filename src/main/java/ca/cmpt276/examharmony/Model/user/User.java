package ca.cmpt276.examharmony.Model.user;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;
import ca.cmpt276.examharmony.Model.Role;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequest;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

//User of the website
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", columnDefinition = "CHAR(36)")
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String emailAddress;
    @Column(name = "prtExpiry", unique = true)
    private LocalDateTime passwordResetTokenExpiry;
    @Column(nullable = false)
    private String password;
    @Column(name = "prt")
    private UUID passwordResetToken;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "roleID"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "instructor_courses",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "courseID"))
    private Set<CoursesSec> instructorCourses = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "instructor_exam_requests",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "exam_requestID"))
    private Set<ExamRequest> examSlotRequests = new HashSet<>();

    public Set<ExamRequest> getExamSlotRequests() {
        return examSlotRequests;
    }

    public void setExamSlotRequests(Set<ExamRequest> examSlotRequests) {
        this.examSlotRequests = examSlotRequests;
    }

    public Set<CoursesSec> getInstructorCourses() {
        return instructorCourses;
    }

    public void addCourse(CoursesSec course){
        instructorCourses.add(course);
    }

    public void setInstructorCourses(Set<CoursesSec> instructorCourses) {
        this.instructorCourses = instructorCourses;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.passwordResetToken = UUID.randomUUID();
    }
    public User() {}

    public static User createUser(String username, String password, String emailAddress, String name){
        User newUser = new User();
        newUser.username = username;
        newUser.password = password;
        newUser.emailAddress = emailAddress;
        newUser.name = name;
        return newUser;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setPasswordResetToken(UUID passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
    public UUID getPasswordResetToken() {
        return passwordResetToken;
    }

    public boolean isPasswordResetTokenValid() {
        return passwordResetToken != null && !passwordResetToken.equals(UUID.fromString("00000000-0000-0000-0000-000000000000")) && passwordResetTokenExpiry != null && passwordResetTokenExpiry.isAfter(LocalDateTime.now());
    }

    public Set<ExamRequest> findRequestsByCourse(String courseName){
        Set<ExamRequest> examRequests = new HashSet<>();
        Iterator<ExamRequest> examRequestIterator = this.examSlotRequests.iterator();
        while (examRequestIterator.hasNext()){
            ExamRequest exam = examRequestIterator.next();
            if(exam.getCourseName().equals(courseName)){
                examRequests.add(exam);
            }
        }
        return examRequests;
    }

    public void addExamRequest(ExamRequest request){
        System.out.println("Adding request");
        examSlotRequests.add(request);
    }

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = this.roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }

    public LocalDateTime getPasswordResetTokenExpiry() {
        return passwordResetTokenExpiry;
    }
    public void setPasswordResetTokenExpiry(LocalDateTime passwordResetTokenExpiry) {
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
    }
}
