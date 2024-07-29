package ca.cmpt276.examharmony.Controllers;

import java.time.LocalDateTime;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserService;
import ca.cmpt276.examharmony.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.cmpt276.examharmony.Model.InvRequests.InvigilatorRequestService;

@Controller
@RequestMapping("/admin")
public class AdminRequestController {

    @Autowired
    private UserService userService;

    private final InvigilatorRequestService invigilatorRequestService;

    @Autowired
    public AdminRequestController(InvigilatorRequestService invigilatorRequestService) {
        this.invigilatorRequestService = invigilatorRequestService;
    }


    @PostMapping("/sendRequest")
    public String sendRequest(@RequestHeader(value = "Referer", required = false) String referer,
            @RequestParam String username,
            @RequestParam String email,
           // @RequestParam int inv_id,
            @RequestParam String examCode,
            @RequestParam String examDate,
            @RequestParam String status,  // Use String to parse into LocalDateTime
            RedirectAttributes redirectAttributes,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getCurrentUser();
        User user = userService.findByUsername(username);
        if (user != null && user.getEmailAddress().equals(email)) {
            LocalDateTime parsedExamDate = LocalDateTime.parse(examDate);
            invigilatorRequestService.createRequest(username, email, examCode, parsedExamDate, status);
            redirectAttributes.addFlashAttribute("alertMessage", "Request sent successfully!");
            if(referer == null){
                model.addAttribute("admin", currentUser);
                return "redirect:/adminHome";
            } else {
                return "redirect:" + referer;
            }

        } else {
            redirectAttributes.addFlashAttribute("alertMessage", "These credentials do not exist");
            return "redirect:/login";
        }
    }

    @GetMapping("/adminTestPage")
    public String adminTestPage(Model model) {
        // Logic to fetch and display requests for admins
        return "adminHome";
    }
}