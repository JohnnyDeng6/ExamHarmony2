package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.InvRequests.InvigilatorRequest;
import ca.cmpt276.examharmony.Model.InvRequests.InvigilatorRequestService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/requests/{requestId}/status")
    @ResponseBody
    public InvigilatorRequest updateRequestStatus(@PathVariable int requestId, @RequestParam String status) {
        return invigilatorRequestService.updateStatus(requestId, status);
    }
}
