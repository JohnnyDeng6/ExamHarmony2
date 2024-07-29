package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.EditInterval.EditInterval;
import ca.cmpt276.examharmony.Model.EditInterval.IntervalRepository;
import ca.cmpt276.examharmony.Model.EditInterval.EditIntervalDTO;
import ca.cmpt276.examharmony.Model.examRequest.ExamSlotRequest;
import ca.cmpt276.examharmony.Model.examRequest.ExamSlotRequestRepository;
import ca.cmpt276.examharmony.Model.examSlot.examSlotRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;

import ca.cmpt276.examharmony.utils.CustomUserDetails;
import ca.cmpt276.examharmony.utils.InstructorExamSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

import ca.cmpt276.examharmony.Model.roles.RoleRepository;

@Controller
@RequestMapping("/admin")
public class Admin {

    @Autowired
    private examSlotRepository examRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ExamSlotRequestRepository examRequestRepository;

    @Autowired
    private IntervalRepository intervalRepository;

    @Autowired
    private  InstructorExamSlotRepository instructorExamRepo;

    @GetMapping("/viewRequests")
    public String viewRequests(Model model) {
        List<ExamSlotRequest> examSlotRequests = examRequestRepository.findAll();
        model.addAttribute("examSlotRequests", examSlotRequests);
        return "viewRequests";
    }

    @PostMapping("/approveRequest")
    public String approveRequest(@RequestParam("requestId") int requestId) {
        ExamSlotRequest request = examRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus("APPROVED");
            request.setPreferenceStatus(1);
            User owner = userRepository.findByUsername(request.getInstructorName());
            Iterator<ExamSlotRequest> iterator = owner.getExamSlotRequests().iterator();
            examRequestRepository.save(request);
            while (iterator.hasNext()){
                ExamSlotRequest currentRequest = iterator.next();
                if(currentRequest.getCourseName().equals(request.getCourseName()) && currentRequest.getID() != request.getID()){
                    instructorExamRepo.removeUserExamRequestAssociation(owner.getUUID(), currentRequest.getID());
                    examRequestRepository.delete(currentRequest);
                    iterator.remove();
                }
            }

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

    @PostMapping("/add/interval")
    public String setInterval(@RequestBody EditIntervalDTO intervalDTO, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails){
            EditInterval interval = intervalRepository.findById(1);
            try{

                interval.setTimes(intervalDTO.startDate, intervalDTO.endDate);
                User admin = userRepository.findByUsername(userDetails.getUsername());
                model.addAttribute("admin", admin);
                intervalRepository.save(interval);
                return "adminHome";

            } catch (RuntimeException err){
                throw new InstructorController.BadRequest(err.getMessage());
            }
        }

        return "redirect:/login";
    }
}



