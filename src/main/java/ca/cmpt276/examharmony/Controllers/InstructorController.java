package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CourseRepository;
import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;
import ca.cmpt276.examharmony.Model.CustomUserDetails;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequest;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ca.cmpt276.examharmony.Model.Instructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class InstructorController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;
    
    @GetMapping("/instructor/home")
    public String InstructorInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            User instructor = userRepo.findByUsername(userDetails.getUsername());
            model.addAttribute("instructor", instructor);
            return "instructorTestPage";
        } else {
            return "redirect:/login";
        }
    }
    
    @PostMapping("/instructor/examslots")
    public String SubmitData(@RequestParam("code") String Code, @RequestParam("duration") String Duration, @RequestParam("p1") String P1, @RequestParam("p2") String P2, @RequestParam("p3") String P3, Model model) {
        Instructor instructor = new Instructor(Code,Duration,P1,P2,P3);
        model.addAttribute("instructor", instructor);
        return "InstructorInfoDisplay";
    }

    @GetMapping("/instructor/examslots/{courseName}")
    public String InstructorRequests(Model model, @PathVariable("courseName") String courseName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            User instructor = userRepo.findByUsername(userDetails.getUsername());
            Set<ExamRequest> exams = instructor.findRequestsByCourse(courseName);
            model.addAttribute("examRequests", exams);
            model.addAttribute("instructor", instructor);
            return "viewExamSlotRequests";
        } else {
            return "redirect:/login";
        }
    }
    
}

