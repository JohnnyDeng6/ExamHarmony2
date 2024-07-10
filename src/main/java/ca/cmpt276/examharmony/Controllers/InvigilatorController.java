package ca.cmpt276.examharmony.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvigilatorController {

    @GetMapping("/invigilator/home")
    public String invigilatorHome(){
        return "InvigilatorTestPage";
    }


    // @GetMapping("/invigilator/give-availibilty")
    // public String invigilator_Avail(){
    //     return "InvigilatorCalendar";
    // }

    // @GetMapping("/invigilator/requests")
    // public String invigilator_requests(){
    //     return "InvigilatorRequests";
    // }
}