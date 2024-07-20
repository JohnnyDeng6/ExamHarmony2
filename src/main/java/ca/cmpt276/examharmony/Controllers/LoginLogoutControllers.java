package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.roles.RoleRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import ca.cmpt276.examharmony.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginLogoutControllers {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/admin/home")
    public String adminTest(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails){
            User admin = userRepo.findByUsername(userDetails.getUsername());
            model.addAttribute("admin", admin);
            return "adminTestPage";
        }
        return "redirect:/login";

    }


    @GetMapping("/test")    
    public String test(){
        return "testPage";
    }
}

