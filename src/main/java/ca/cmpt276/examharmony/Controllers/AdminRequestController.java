package ca.cmpt276.examharmony.Controllers;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.cmpt276.examharmony.Model.InvRequests.InvigilatorRequestService;

@Controller
@RequestMapping("/admin")
public class AdminRequestController {
    private final InvigilatorRequestService invigilatorRequestService;

    @Autowired
    public AdminRequestController(InvigilatorRequestService invigilatorRequestService) {
        this.invigilatorRequestService = invigilatorRequestService;
    }

    @PostMapping("/sendRequest")
    public String sendRequest(
            @RequestParam String username,
            @RequestParam String email,
           // @RequestParam int inv_id,
            @RequestParam String examCode,
            @RequestParam String examDate,
            @RequestParam String status,  // Use String to parse into LocalDateTime
            RedirectAttributes redirectAttributes) {

        LocalDateTime parsedExamDate = LocalDateTime.parse(examDate);
        invigilatorRequestService.createRequest(username, email, examCode, parsedExamDate, status);

        redirectAttributes.addFlashAttribute("message", "Request sent successfully!");
        return "adminHome";
    }

    @GetMapping("/adminTestPage")
    public String adminTestPage(Model model) {
        // Logic to fetch and display requests for admins
        return "adminHome";
    }
}