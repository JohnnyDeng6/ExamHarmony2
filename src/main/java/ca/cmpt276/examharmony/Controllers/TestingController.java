package ca.cmpt276.examharmony.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestingController {

    @GetMapping("/adminExamSlot")
    public String showAdminExamSlotPage() {
        return "adminExamSlot"; // This returns the template named "adminExamSlot.html"
    }

    @GetMapping("/invTest")
    public String showInvigilator() {
        return "invigilatorTestPage"; // This returns the template named "adminExamSlot.html"
    }
}