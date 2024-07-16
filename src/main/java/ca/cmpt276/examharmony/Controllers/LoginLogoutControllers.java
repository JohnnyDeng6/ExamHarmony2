package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.roles.RoleRepository;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginLogoutControllers {

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/admin/home")
    public String adminTest(){
        return "adminTestPage";
    }


    @GetMapping("/test")    
    public String test(){
        return "testPage";
    }
}

