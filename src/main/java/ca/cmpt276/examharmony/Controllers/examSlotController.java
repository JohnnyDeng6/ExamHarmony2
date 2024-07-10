
package ca.cmpt276.examharmony.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ca.cmpt276.examharmony.Model.examSlot.examSlotRepository;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

import ca.cmpt276.examharmony.Model.examSlot.examSlot;
import ca.cmpt276.examharmony.Model.user.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class examSlotController {
    
    @Autowired
    private examSlotRepository examRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/examSlot/showAll")
    public String getAllExamSlots(Model model){
        List<examSlot> examSlot = examRepo.findAll();
        model.addAttribute("examSlots", examSlot);
        return "/adminExamSlot";
    }
    
    @PostMapping("/examSlot/add")
    public String addExamSlot(@RequestParam Map<String, String> newExamSlot, HttpServletResponse response) {
        LocalDate StartTime = LocalDate.parse(newExamSlot.get("StartTime"));
        double duration = Double.parseDouble(newExamSlot.get("duration"));
        int numOfRooms = Integer.parseInt(newExamSlot.get("numOfRooms"));
        int assignedRooms = Integer.parseInt(newExamSlot.get("assignedRooms"));
        int numInvigilator = Integer.parseInt(newExamSlot.get("numInvigilator"));
    
        /*Admin ID */
        User admin = userRepo.findById(UUID.fromString(newExamSlot.get("adminID"))).orElseThrow(()-> new IllegalArgumentException("Invalid admin ID"));

        String status = newExamSlot.get("status");
        
        examSlot exam = new examSlot();

        exam.setStartTime(StartTime);
        exam.setDuration(duration);
        exam.setNumOfRooms(numOfRooms);
        exam.setNumInvigilator(numInvigilator);
        exam.setAssignedRooms(assignedRooms);
        exam.setAdmin(admin);
        exam.setStatus(status);
        
        examRepo.save(exam);
        response.setStatus(HttpServletResponse.SC_CREATED);
        
        return "redirect:/adminExamSlot";
    }
    
    @PostMapping("/examSlot/delete")
    public String deleteExamSlot(@RequestParam("id") int id){

        if(examRepo.existsById(id)){
            System.out.println("delete successful");
            examRepo.deleteById(id);
        }
        return "redirect:/adminExamSlot";
    }



    @PostMapping("examSlot/update")
    public String updateExamSlot(@ModelAttribute examSlot exam){
        examRepo.save(exam);
        return "redirect:/adminExamSlot";
    }

}