package ca.cmpt276.examharmony.Controllers;

import java.security.Principal;
import ca.cmpt276.examharmony.Model.InvRequests.InvigilatorRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import ca.cmpt276.examharmony.Model.InvRequests.InvigilatorRequest;

@Controller
@RequestMapping("/invigilator")
public class InvigilatorController {
    private final InvigilatorRequestService invigilatorRequestService;

    @Autowired
    public InvigilatorController(InvigilatorRequestService invigilatorRequestService) {
        this.invigilatorRequestService = invigilatorRequestService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model, Principal principal) {
        String username = principal.getName();
        List<InvigilatorRequest> requests = invigilatorRequestService.getRequests(username);
        model.addAttribute("requests", requests);
        return "invigilatorTestPage";
    }


    @GetMapping("/requests/{username}")
    @ResponseBody
    public List<InvigilatorRequest> getRequests(@PathVariable String username) {
        return invigilatorRequestService.getRequests(username);
    }

    @PostMapping("/requests/status")
    @ResponseBody
    public ResponseEntity<?> updateRequestStatus(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String examCode = body.get("examCode");
            String status = body.get("status");
            InvigilatorRequest updatedRequest = invigilatorRequestService.updateStatus(username, examCode, status);
            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating request status: " + e.getMessage());
        }
    }
}
    