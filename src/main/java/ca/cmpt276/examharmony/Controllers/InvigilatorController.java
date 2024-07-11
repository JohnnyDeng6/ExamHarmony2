package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.InvRequests.invigilatorRequest;
import ca.cmpt276.examharmony.Model.InvRequests.InvigilatorRequestService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/invigilator")
public class InvigilatorController {

    private final InvigilatorRequestService invigilatorRequestService;

    @Autowired
    public InvigilatorController(InvigilatorRequestService invigilatorRequestService) {
        this.invigilatorRequestService = invigilatorRequestService;
    }

    @GetMapping("/home")
    public String invigilatorHome(Model model) {
        return "invigilatorTestPage";
    }

    @GetMapping("/requests/{username}")
    @ResponseBody
    public List<invigilatorRequest> getRequests(@PathVariable String username) {
        return invigilatorRequestService.getRequests(username);
    }

    @PostMapping("/requests/{requestId}/status")
    @ResponseBody
    public invigilatorRequest updateRequestStatus(@PathVariable int requestId, @RequestParam String status) {
        return invigilatorRequestService.updateStatus(requestId, status);
    }
}