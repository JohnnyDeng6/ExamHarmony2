package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CourseRepository;

import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;
import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSecDTO;
import ca.cmpt276.examharmony.utils.CustomUserDetails;
import ca.cmpt276.examharmony.Model.DTOs.DepartmentDTO;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequest;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequestDTO;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequestRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import ca.cmpt276.examharmony.utils.InstructorExamSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.Collections.sort;

@Controller
public class InstructorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class BadRequest extends RuntimeException{
        public BadRequest(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class NotFoundException extends RuntimeException{
        public NotFoundException(String message) {
            super(message);
        }
    }

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private ExamRequestRepository requestRepo;

    @Autowired
    private InstructorExamSlotRepository instructorExamSlotRepo;

    private List<DepartmentDTO> departments = Collections.synchronizedList(new ArrayList<>());;

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
    @GetMapping("/instructor/examslots/{courseName}")
    public String InstructorRequests(Model model, @PathVariable("courseName") String courseName) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
        User instructor = userRepo.findByUsername(userDetails.getUsername());
        List<ExamRequest> examRequests = requestRepo.findExamRequestsByCourseName(courseName);
        model.addAttribute("examRequests", examRequests);
        model.addAttribute("instructor", instructor);
        model.addAttribute("courseName", courseName);
        return "viewExamSlotRequests";
    } else {
        return "redirect:/login";
    }
}


    @PostMapping("/instructor/examslots/edit/{courseName}")
    public String submitData(@RequestBody List<ExamRequestDTO> examRequestDTOList, Model model, @PathVariable("courseName") String courseName) {
        if (examRequestDTOList.isEmpty()) {
            return "redirect:/instructor/examslots/" + courseName;
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

        sort(examRequestDTOList);
        sort(previousRequests);
        Iterator<ExamRequest> examRequestIterator = previousRequests.iterator();
        int preferenceStatus = 1;
        // Update already-existing exam requests
       while(examRequestIterator.hasNext()) {
           ExamRequest previousRequest = examRequestIterator.next();
            //Find a new request which has the same preference and update the old request
            Iterator<ExamRequestDTO> iterator = examRequestDTOList.iterator();

            while (iterator.hasNext()){
                ExamRequestDTO newRequest = iterator.next();
                if(newRequest.preferenceStatus == previousRequest.getPreferenceStatus()){
                    try{
                        previousRequest.setExamCode(newRequest.examCode);
                        previousRequest.setExamDuration(newRequest.examDuration);
                        previousRequest.setExamDate(newRequest.examDate);
                        instructor.updateExamRequest(previousRequest, newRequest.examDate);
                        requestRepo.save(previousRequest);
                        iterator.remove();
                    } catch (RuntimeException invalidParameter){
                        throw new BadRequest(invalidParameter.getMessage());
                    }
                }
            }
            preferenceStatus++;
        }
        // Add new exam requests
        for (ExamRequestDTO newRequestDTO : examRequestDTOList) {
            try {
                ExamRequest newRequest = new ExamRequest();
                newRequest.setExamCode(newRequestDTO.examCode);
                newRequest.setExamDuration(newRequestDTO.examDuration);
                newRequest.setExamDate(newRequestDTO.examDate);
                newRequest.setCourseName(courseName);
                newRequest.setStatus("PENDING");
                newRequest.setPreferenceStatus(preferenceStatus);
                requestRepo.save(newRequest);
                instructor.addNewExamRequest(newRequest);
                preferenceStatus++;
            } catch (RuntimeException invalidParameter) {
                throw new BadRequest(invalidParameter.getMessage());
            }
        }

        for(ExamRequest t: instructor.findRequestsByCourse(courseName)){
            System.out.println(t.getExamDuration());
            System.out.println(t.getExamDate());
            System.out.println(t.getPreferenceStatus());
            System.out.println(t.getExamCode());

        }
        userRepo.save(instructor);

        model.addAttribute("examRequests", instructor.findRequestsByCourse(courseName));
        model.addAttribute("instructor", instructor);
        model.addAttribute("courseName", courseName);
        return "viewExamSlotRequests";

    }

    // @GetMapping("/instructor/examslots/{courseName}")
    // public String InstructorRequests(Model model, @PathVariable("courseName") String courseName) {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
    //         User instructor = userRepo.findByUsername(userDetails.getUsername());
    //         System.out.println(instructor.findRequestsByCourse(courseName));
    //         model.addAttribute("examRequests", instructor.findRequestsByCourse(courseName));
    //         model.addAttribute("instructor", instructor);
    //         model.addAttribute("courseName", courseName);
    //         return "viewExamSlotRequests";
    //     } else {
    //         return "redirect:/login";
    //     }
    // }

    @DeleteMapping("/instructor/examslots/delete/{courseName}/{preference}")
    public String deleteRequest(Model model, @PathVariable("courseName") String courseName
            , @PathVariable("preference") int preference){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails){
            User instructor = userRepo.findByUsername(userDetails.getUsername());
            List<ExamRequest> examRequestList = instructor.findRequestsByCourse(courseName);
            Iterator<ExamRequest> iterator = examRequestList.iterator();
            while (iterator.hasNext()) {
                ExamRequest request = iterator.next();
                if (request.getPreferenceStatus() == preference) {
                    instructorExamSlotRepo.removeUserExamRequestAssociation(instructor.getUuid(), request.getID());
                    instructor.deleteExamRequest(request);
                    requestRepo.delete(request);
                    iterator.remove();
                    while(iterator.hasNext()){
                        ExamRequest nextRequest = iterator.next();
                        nextRequest.setPreferenceStatus(nextRequest.getPreferenceStatus()-1);
                        requestRepo.save(nextRequest);
                    }
                    model.addAttribute("examRequests", instructor.findRequestsByCourse(courseName));
                    model.addAttribute("instructor", instructor);
                    model.addAttribute("courseName", courseName);
                    return "viewExamSlotRequests";
                }
            }
            throw new NotFoundException("Requested exam slot request does not exist");
        } else {
            return "redirect:/login";
        }
    }


    @PostMapping("/instructor/get/departments")
    public String getDepartments(Model model, @RequestBody List<DepartmentDTO> departments) {
        this.departments.clear();
        this.departments.addAll(departments);
        model.addAttribute("departmentInfo", this.departments);
        return "department-selection";
    }

    @GetMapping("/instructor/view/departments/{semester}")
    public String viewDepartments(Model model, @PathVariable("semester") String semester){
        model.addAttribute("departmentInfo", this.departments);
        model.addAttribute("semester", semester);
        return "department-selection";
    }

    @PostMapping("/instructor/view/add/course")
    public String addCourse(Model model, @RequestBody CoursesSecDTO newCourse){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails){
            User currentUser = userRepo.findByUsername(userDetails.getUsername());
            CoursesSec course = CoursesSec.CreateNewCourse(newCourse.department, newCourse.courseName);
            courseRepo.save(course);
            currentUser.addCourse(course);
            userRepo.save(currentUser);
            model.addAttribute("instructor", currentUser);
            return "instructorTestPage";
        } else {
            return "redirect:/login";
        }
    }
}

