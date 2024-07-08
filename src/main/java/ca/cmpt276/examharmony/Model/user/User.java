package ca.cmpt276.examharmony.Model.user;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;
import ca.cmpt276.examharmony.Model.Role;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequest;
import jakarta.persistence.*;

//User of the website
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String emailAddress;

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

    public static User createUser(String name, String password, String emailAddress){
        User newUser = new User();
        newUser.username = name;
        newUser.password = password;
        newUser.emailAddress= emailAddress;
        return newUser;
    }

    public int getID() {
        return ID;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return username;
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

}
