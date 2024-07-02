package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.User;
import ca.cmpt276.examharmony.Model.registration.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("userId") int userId, Model model) {
        User user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "Invalid user ID.");
            return "reset-password-error";
        }
        model.addAttribute("userId", userId);
        return "reset-password-form";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("userId") int userId,
                                      @RequestParam("password") String newPassword, Model model) {
        User user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "Invalid user ID.");
            return "reset-password-error";
        }
        userService.updatePassword(userId, newPassword);
        return "redirect:/login";
    }
}

