package ca.cmpt276.examharmony.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvigilatorController {

    @GetMapping("invigilator/home")
    public String invigilatorHome(){
        return "invigilatorTestPage";
    }

}
