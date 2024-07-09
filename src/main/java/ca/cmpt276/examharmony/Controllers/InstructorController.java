package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CourseRepository;
import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;
import ca.cmpt276.examharmony.Model.CustomUserDetails;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequest;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequestDTO;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequestRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ca.cmpt276.examharmony.Model.Instructor;

import java.util.List;
import java.util.Set;

@Controller
public class InstructorController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private ExamRequestRepository requestRepo;
    
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

    @PostMapping("/instructor/examslots/edit/{courseName}")
    public String submitData(@RequestBody List<ExamRequestDTO> examRequestDTOList, Model model, @PathVariable("courseName") String courseName) {
        if (examRequestDTOList.isEmpty()) {
            return "redirect:/login";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return "redirect:/login";
        }

        User instructor = userRepo.findByUsername(userDetails.getUsername());
        if (instructor == null) {
            return "redirect:/login";
        }

        List<ExamRequest> previousRequests = requestRepo.findExamRequestsByCourseName(courseName);

        // Update already-existing exam requests
        for (ExamRequest previousRequest : previousRequests) {
            //TODO: Handle case where instructor modifies request after it has been approved
            if (previousRequest.getStatus().equals("APPROVED")) {
                //Do something
            }

            if (!examRequestDTOList.isEmpty()) {
                ExamRequestDTO newRequest = examRequestDTOList.remove(0);
                previousRequest.setExamCode(newRequest.examCode);
                previousRequest.setExamDuration(newRequest.examDuration);
                previousRequest.setExamDate(newRequest.examDate);
                requestRepo.save(previousRequest);
            }
        }

        // Add new exam requests
        for (ExamRequestDTO newRequestDTO : examRequestDTOList) {
            ExamRequest newRequest = new ExamRequest();
            newRequest.setExamCode(newRequestDTO.examCode);
            newRequest.setExamDuration(newRequestDTO.examDuration);
            newRequest.setExamDate(newRequestDTO.examDate);
            newRequest.setCourseName(courseName);
            newRequest.setStatus("PENDING");
            requestRepo.save(newRequest);
            instructor.addExamRequest(newRequest);
        }
        System.out.println(instructor.getName() + " " + instructor.getEmailAddress());
        userRepo.save(instructor);

        model.addAttribute("examRequests", instructor.getExamSlotRequests());
        model.addAttribute("instructor", instructor);
        model.addAttribute("courseName", courseName);

        return "viewExamSlotRequests";
    }

    @GetMapping("/instructor/examslots/{courseName}")
    public String InstructorRequests(Model model, @PathVariable("courseName") String courseName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            User instructor = userRepo.findByUsername(userDetails.getUsername());
            model.addAttribute("examRequests", instructor.getExamSlotRequests());
            model.addAttribute("instructor", instructor);
            model.addAttribute("courseName", courseName);
            return "viewExamSlotRequests";
        } else {
            return "redirect:/login";
        }
    }
    
}

