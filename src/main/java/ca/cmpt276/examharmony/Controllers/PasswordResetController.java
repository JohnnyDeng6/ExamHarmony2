package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.CustomUserDetails;
import ca.cmpt276.examharmony.Model.emailSender.EmailService;
import ca.cmpt276.examharmony.Model.registration.UserRegistrationDto;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import ca.cmpt276.examharmony.Model.user.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;


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

    @PostMapping("/{prefix}/sendPrt")
    @ResponseBody
    public String sendPasswordResetLink(RedirectAttributes redirectAttributes, @PathVariable("prefix") String prefix) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getCurrentUser();

            UUID token = UUID.randomUUID();
            user.setPasswordResetToken(token);
            user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
            userRepository.save(user);

            String toEmail = userDetails.getEmail();
            String subject = "Password Reset Confirmation";
            String body = buildPasswordResetEmailBody(userDetails);
            emailService.sendHtmlEmail(toEmail, subject, body);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }


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

    private String buildPasswordResetEmailBody(CustomUserDetails userDetails) {
        String link = "https://examharmony.onrender.com/reset-password?passwordResetToken=" + userDetails.getPasswordResetToken();
        return "<p>Dear " + userDetails.getName() + ",</p>"
                + "<p>You have requested a password reset.</p>"
                + "<p>To get started, please set your password by clicking the link below:</p>"
                + "<p><a href=\"" + link + "\">Set Your Password</a></p>"
                + "<p>This link will expire in 24 hours</p>"
                + "<p>If you did not request for a password reset, please ignore this email.</p>"
                + "<p>Best regards,</p>"
                + "<p>The ExamHarmony Team</p>"
                + "<p><em>Note: This is an automated message, please do not reply.</em></p>";
    }
}

