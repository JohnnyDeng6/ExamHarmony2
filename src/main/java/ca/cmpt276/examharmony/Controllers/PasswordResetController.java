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
    public String showSetPasswordForm(@RequestParam("passwordResetToken") UUID passwordResetToken, Model model) {
        User user = userService.findByPasswordResetToken(passwordResetToken);

        if (user == null || !user.isPasswordResetTokenValid()) {
            model.addAttribute("error", "This link does not exist");
            return "reset-password-error";
        }
        model.addAttribute("passwordResetToken", passwordResetToken);
        return "reset-password-form";
    }

//    public String sendPasswordResetLink() {
//    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("passwordResetToken") UUID passwordResetToken,
                                      @RequestParam("password") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      Model model) {
        User user = userService.findByPasswordResetToken(passwordResetToken);
        if (user == null || !user.isPasswordResetTokenValid()) {
            model.addAttribute("error", "This link does not exist.");
            return "reset-password-error";
        }
        model.addAttribute("passwordResetToken", passwordResetToken);
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "reset-password-form";
        }
        if (!newPassword.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z\\d@$!%*?&]{8,}$")) {
            model.addAttribute("error", "Password must be at least 8 characters long and include a number and a symbol.");
            return "reset-password-form";
        }
        userService.updatePassword(user.getUUID(), newPassword);
        userService.invalidatePasswordResetToken(user.getUUID());
        return "redirect:/login";
    }
}

