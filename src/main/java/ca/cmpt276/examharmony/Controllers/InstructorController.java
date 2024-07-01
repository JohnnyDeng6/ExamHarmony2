package ca.cmpt276.examharmony.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.cmpt276.examharmony.Model.Instructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InstructorController {
    
    @GetMapping("/instructor/home")
    public String InstructorInfo() {
        return "instructorTestPage";
    }
    
    @PostMapping("/instructor/home")
    public String SubmitData(@RequestParam("code") String Code, @RequestParam("duration") String Duration, @RequestParam("p1") String P1, @RequestParam("p2") String P2, @RequestParam("p3") String P3, Model model) {
        Instructor instructor = new Instructor(Code,Duration,P1,P2,P3);
        model.addAttribute("instructor", instructor);
        return "InstructorInfoDisplay";
    }
    
}
