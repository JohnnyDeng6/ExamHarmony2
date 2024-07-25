package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.examRequest.ExamSlotRequest;
import ca.cmpt276.examharmony.Model.examRequest.ExamSlotRequestRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import ca.cmpt276.examharmony.Model.roles.RoleRepository;

@Controller
@RequestMapping("/admin")
public class Admin {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ExamSlotRequestRepository examRequestRepository;

    @GetMapping("/viewRequests")
    public String viewRequests(Model model) {
        List<ExamSlotRequest> examSlotRequests = examRequestRepository.findAll();
        model.addAttribute("examRequests", examSlotRequests);
        return "viewRequests";
    }

    @PostMapping("/approveRequest")
    public String approveRequest(@RequestParam("requestId") int requestId) {
        ExamSlotRequest request = examRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus("approved");

            User owner = userRepository.findByUsername(request.getInstructorName());
            owner.deleteUnApprovedRequests(request.getCourseName(), requestId);
            examRequestRepository.save(request);
            userRepository.save(owner);

        }

        return "redirect:/admin/viewRequests";
    }

    @GetMapping("/viewInstructors")
    public String viewInstructors(Model model) {
        List<User> instructors = userRepository.findByRoleName("INSTRUCTOR");
        model.addAttribute("instructors", instructors);
        return "viewInstructors";
    }

    @GetMapping("/viewInvigilators")
    public String viewInvigilators(Model model) {
        List<User> invigilators = userRepository.findByRoleName("INVIGILATOR");
        model.addAttribute("invigilators",invigilators);
        return "viewInvigilators";
    }
}



