package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.examRequest.ExamRequest;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequestRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Admin {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamRequestRepository examRequestRepository;

    @GetMapping("/viewRequests")
    public String viewRequests(Model model) {
        List<ExamRequest> examRequests = examRequestRepository.findAll();
        model.addAttribute("examRequests", examRequests);
        return "viewRequests"; 
    }

    @PostMapping("/approveRequest")
    public String approveRequest(@RequestParam("requestId") int requestId) {
        ExamRequest request = examRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus("approved");
            examRequestRepository.save(request);
        }
        return "redirect:/viewRequests";
    }

    @GetMapping("/viewInstructors")
    public String viewInstructors(Model model) {
        List<User> instructors = userRepository.findByRoleName("instructor");
        model.addAttribute("instructors", instructors);
        return "viewInstructors";
    }

    @GetMapping("/viewInvigilators")
    public String viewInvigilators(Model model) {
        return "viewInvigilators"; // This should match the name of your HTML file in the templates folder
    }
}
