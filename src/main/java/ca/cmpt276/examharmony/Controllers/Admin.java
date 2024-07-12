package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class Admin {

    @GetMapping("/viewRequests")
    public String viewRequests(Model model) {
        // Add any necessary attributes to the model
        return "viewRequests"; // This should match the name of your HTML file in the templates folder
    }

    // @GetMapping("/viewInstructors")
    // public String viewInstructors(Model model) {
    //     List<User> instructors = UserRepository.findByRoleName("Instructor");
    //     model.addAttribute("instructors", instructors);
    //     return "viewInstructors";
    // }
    @GetMapping("/viewInstructors")
    public String viewInstructors(Model model) {
        List<User> instructors = UserRepository.findByRoleName("Instructor");
        model.addAttribute("instructors", instructors);
        return "viewInstructors";
    }

    @GetMapping("/viewInvigilators")
    public String viewInvigilators(Model model) {
        // Add any necessary attributes to the model
        // Example: model.addAttribute("invigilators", invigilatorService.getAllInvigilators());
        return "viewInvigilators"; // This should match the name of your HTML file in the templates folder
    }
}
