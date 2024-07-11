package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CourseRepository;
import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;
import ca.cmpt276.examharmony.Model.Role;
import ca.cmpt276.examharmony.Model.RoleRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class TestingController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder pwEncorder;

    @GetMapping("/adminExamSlot")
    public String showAdminExamSlotPage() {
        return "adminExamSlot"; // This returns the template named "adminExamSlot.html"
    }

    @GetMapping("/invTest")
    public String showInvigilator() {
        return "invigilatorTestPage"; // This returns the template named "adminExamSlot.html"
    }

    @GetMapping("/test/add_test_course")
    public void addTestCourse(){

        CoursesSec newCourse = new CoursesSec();
        newCourse.setCourseName("CMPT354");
        newCourse.setDepartment("CMPT");
        newCourse.setExamSlotStatus(false);
        courseRepo.save(newCourse);

    }

    // @GetMapping("/test")
    // public String getTestPage(){
    //     return "testPage";
    // }

    @GetMapping("/test/add_user_with_course")
    public void addTestInstructor(){
        CoursesSec course = courseRepo.findByCourseName("CMPT354");
        String password = pwEncorder.encode("Helios6789!");
        User newUser = User.createUser("Jackson", password , "JacksonPollack@lazersharks.com", "Jackson");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepo.findByName("INSTRUCTOR"));
        newUser.addCourse(course);
        newUser.setRoles(roleSet);
        userRepo.save(newUser);
    }
}