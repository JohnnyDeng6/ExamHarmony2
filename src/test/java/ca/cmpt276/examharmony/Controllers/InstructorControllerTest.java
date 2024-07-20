package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.examRequest.ExamRequest;
import ca.cmpt276.examharmony.Model.examRequest.ExamRequestRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(InstructorController.class)
class InstructorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstructorController instructorController;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    ExamRequestRepository requestRepo;

    @Test
    @WithMockUser(username = "John", roles = {"INSTRUCTOR"})
    void submitData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("instructorTestPage"));
    }

    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
    void instructorRequests() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User mockInstructor = new User();
        mockInstructor.setName("Alex");
        mockInstructor.setEmailAddress("Test@example.ca");
        mockInstructor.setPassword("123456");

        List<ExamRequest> mockExamRequests = Arrays.asList(
                new ExamRequest(1, LocalDateTime.parse("2024-04-12 12:48", formatter), 3, "CMPT354"),
                new ExamRequest(1,  LocalDateTime.parse("2024-04-16 01:00"), 4, "CMPT354")
        );

        when(userRepo.findByUsername("instructor")).thenReturn(mockInstructor);
        when(requestRepo.findExamRequestsByCourseName("Course 1")).thenReturn(mockExamRequests);
        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/examslots/CMPT354"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("viewExamSlotRequests"));
    }

    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
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