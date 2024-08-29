package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CourseRepository;
import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;
import ca.cmpt276.examharmony.Model.EditInterval.IntervalRepository;
import ca.cmpt276.examharmony.Model.roles.Role;
import ca.cmpt276.examharmony.Model.roles.RoleRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import ca.cmpt276.examharmony.Model.user.UserService;
import ca.cmpt276.examharmony.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @Autowired
    private IntervalRepository intervalRepository;
    private UserService userService;

    @GetMapping("/adminExamSlot")
    public String showAdminExamSlotPage() {
        return "admin/adminExamSlot"; // This returns the template named "adminExamSlot.html"
    }

    @GetMapping("/adminExamDisplay")
    public String showAdminExamDisplayPage(){
        return "admin/adminExamDisplay";
    }

    @GetMapping("/invTest")
    public String showInvigilator() {
        return "invigilator/invigilatorHome"; // This returns the template named "adminExamSlot.html"
    }

    @GetMapping("/test/add_users")
    public void addTestUsers() throws NoSuchAlgorithmException {

            if (userRepo.findByUsername("ADMIN") == null) {
                User user = User.createUser(
                        "ADMIN",
                        pwEncorder.encode("TEST"),
                        "a@gmail.com",
                        "ADMIN"
                );
                Set<Role> roleSet = new HashSet<>();
                roleSet.add(roleRepo.findByName("ADMIN"));
                user.setRoles(roleSet);

                SecureRandom secureRandom = new SecureRandom();
                UUID prtUUID = new UUID(secureRandom.nextLong(), secureRandom.nextLong());
                HashUtils hashUtils = new HashUtils();
                user.setPasswordResetToken(hashUtils.SHA256(prtUUID));

                userRepo.save(user);
            }

        if (userRepo.findByUsername("INSTRUCTOR") == null) {
            User user2 = User.createUser(
                    "INSTRUCTOR",
                    pwEncorder.encode("TEST"),
                    "i@gmail.com",
                    "INSTRUCTOR"
            );
            Set<Role> roleSet2 = new HashSet<>();
            roleSet2.add(roleRepo.findByName("INSTRUCTOR"));
            user2.setRoles(roleSet2);

            SecureRandom secureRandom2 = new SecureRandom();
            UUID prtUUID2 = new UUID(secureRandom2.nextLong(), secureRandom2.nextLong());
            HashUtils hashUtils2 = new HashUtils();
            user2.setPasswordResetToken(hashUtils2.SHA256(prtUUID2));
            userRepo.save(user2);

        }
        if (userRepo.findByUsername("INVIGILATOR") == null) {
            User user3 = User.createUser(
                    "INVIGILATOR",
                    pwEncorder.encode("TEST"),
                    "iv@gmail.com",
                    "INVIGILATOR"
            );
            Set<Role> roleSet3 = new HashSet<>();
            roleSet3.add(roleRepo.findByName("INVIGILATOR"));
            user3.setRoles(roleSet3);
            SecureRandom secureRandom3 = new SecureRandom();
            UUID prtUUID3 = new UUID(secureRandom3.nextLong(), secureRandom3.nextLong());
            HashUtils hashUtils3 = new HashUtils();
            user3.setPasswordResetToken(hashUtils3.SHA256(prtUUID3));
            userRepo.save(user3);

        }

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

    @GetMapping("/test/get/user")
    public void getTestInstructor(){
        User user = userRepo.findByEmailAddress("jzydeng@gmail.com");
        System.out.println(user.getEmailAddress());
        System.out.println(user.getUUID());
        System.out.println(user.getUsername());
    }

}