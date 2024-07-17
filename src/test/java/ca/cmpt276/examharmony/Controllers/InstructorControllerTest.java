package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class InstructorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstructorController instructorController;

    @Autowired
    private UserRepository userRepo;


    @Test
    void submitData() {
    }

    @Test
    void instructorRequests() {
    }

    @Test
    void deleteRequest() {
    }

    @Test
    void getDepartments() {
    }

    @Test
    void viewDepartments() {
    }

    @Test
    void addCourse() {
    }
}