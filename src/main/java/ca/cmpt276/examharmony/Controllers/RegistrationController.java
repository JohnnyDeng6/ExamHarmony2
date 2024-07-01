package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.User;
import ca.cmpt276.examharmony.Model.emailSender.EmailService;
import ca.cmpt276.examharmony.Model.registration.UserRegistrationDto;
import ca.cmpt276.examharmony.Model.registration.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// When an admin creates an account, assume they know the person’s email
    // and create a temporary password, role(s), and username for them,
    // then they are sent the email with their account,
    // from then they can log in and change their password or username

    //create tmp pw,
    @Controller
    public class RegistrationController {

        @Autowired
        private UserService userService;

        @Autowired
        private EmailService emailService;

        @GetMapping("/admin/register")
        public String showRegistrationForm(Model model) {
            model.addAttribute("user", new UserRegistrationDto());
            return "/registrationPage";
        }

        @PostMapping("/admin/registration")
        public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, RedirectAttributes redirectAttributes, HttpSession session, Errors errors) {
            try {
                userService.registerNewUser(registrationDto);
                redirectAttributes.addFlashAttribute("alertMessage", "New user registered successfully. Return home");

                String toEmail = registrationDto.getEmail();
                String subject = "Registration Confirmation";
                String body = "Dear " + registrationDto.getName() + ",\n\nThank you for registering.\n\nBest regards,\nYour Company";
                emailService.sendSimpleEmail(toEmail, subject, body);

            } catch (UserAlreadyExistException uaeEx) {
                redirectAttributes.addFlashAttribute("alertMessage", "Account already exists, please choose another email or username");
                return "redirect:/admin/register";
            }
            return "redirect:/admin/home";
        }

        @PostMapping("/change-password")
        public String changePassword(@ModelAttribute("user") UserRegistrationDto registrationDto) {

            return "redirect:/home";
        }

        //TO DO:
        //SEND EMAIL TO registrationDto.getEmail()
        //prompt password change: passwordEncoder.encode(user.setPassword(NEW_PASSWORD);
    }

