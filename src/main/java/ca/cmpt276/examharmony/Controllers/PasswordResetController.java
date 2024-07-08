package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("userId") UUID userId, Model model) {
        User user = userService.findByUUID(userId);

        if (user == null) {
            model.addAttribute("error", "Invalid user ID.");
            return "reset-password-error";
        }
        model.addAttribute("userId", userId);
        return "reset-password-form";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("userId") UUID userId,
                                      @RequestParam("password") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      Model model) {
        User user = userService.findByUUID(userId);
        if (user == null) {
            model.addAttribute("error", "Invalid user ID.");
            return "reset-password-error";
        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("userId", userId);
            return "reset-password-form";
        }
        if (!newPassword.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("error", "Password must be at least 8 characters long and include a number and a symbol.");
            model.addAttribute("userId", userId);
            return "reset-password-form";
        }
        userService.updatePassword(userId, newPassword);
        return "redirect:/login";
    }
}

