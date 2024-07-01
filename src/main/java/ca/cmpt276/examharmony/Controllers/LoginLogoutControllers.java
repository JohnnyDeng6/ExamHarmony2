package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.Role;
import ca.cmpt276.examharmony.Model.RoleRepository;
import ca.cmpt276.examharmony.Model.User;
import ca.cmpt276.examharmony.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LoginLogoutControllers {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/admin/home")
    public String adminTest(){


        return "adminTestPage";
    }

    @GetMapping("/instructor/home")
    public String instructorTest(){
        /*
        User newUser = new User();
        newUser.setEmailAddress("jca543@sfu.ca");
        newUser.setName("Bob");
        newUser.setPassword(encoder.encode("1111"));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByName("ADMIN"));
        newUser.setRoles(roles);

        userRepo.save(newUser);
        */

        return "instructorTestPage";
    }

    //For testing purposes
    @GetMapping("/test")
    public String test(){
        return "testPage";
    }


}

