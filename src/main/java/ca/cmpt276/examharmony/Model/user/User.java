package ca.cmpt276.examharmony.Model.user;
import java.time.LocalDateTime;
import java.util.*;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequest;
import jakarta.persistence.*;

import ca.cmpt276.examharmony.Model.roles.Role;
import jakarta.transaction.Transactional;

//User of the website
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", columnDefinition = "UUID")
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
    private String passwordResetToken;
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

    public String getHighestRole() {
        for (Role role : roles) {
            return role.getName().toLowerCase();
        }
        return "";
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(
            name = "instructor_exam_requests",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "exam_requestID"))
    private List<ExamRequest> examSlotRequests = new ArrayList<>();

    public List<ExamRequest> getExamSlotRequests() {
        return examSlotRequests;
    }

    public void setExamSlotRequests(List<ExamRequest> examSlotRequests) {
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
//        this.passwordResetToken = UUID.randomUUID();
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

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isPasswordResetTokenValid() {
        return passwordResetToken != null && !passwordResetToken.equals("DNE") && passwordResetTokenExpiry != null && passwordResetTokenExpiry.isAfter(LocalDateTime.now());
    }

    public List<ExamRequest> findRequestsByCourse(String courseName){
        List<ExamRequest> examRequests = new ArrayList<>();
        Iterator<ExamRequest> examRequestIterator = this.examSlotRequests.iterator();
        while (examRequestIterator.hasNext()){
            ExamRequest exam = examRequestIterator.next();
            if(exam.getCourseName().equals(courseName)){
                examRequests.add(exam);
            }
        }
        return examRequests;
    }

    public void addNewExamRequest(ExamRequest request){

        examSlotRequests.add(request);
    }

    public void updateExamRequest(ExamRequest request, String newDate){
        for(ExamRequest examRequest: this.examSlotRequests){
            if(examRequest.getPreferenceStatus() == request.getPreferenceStatus() && examRequest.getCourseName().equals(request.getCourseName())){
                examRequest.setExamCode(request.getExamCode());
                examRequest.setExamDate(newDate);
                examRequest.setExamDuration(request.getExamDuration());
                examRequest.setStatus(request.getStatus());
                return;
            }
        }
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

    @Transactional
    public void deleteExamRequest(ExamRequest examRequest) {
        this.examSlotRequests.remove(examRequest);
    }

}
