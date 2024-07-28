package ca.cmpt276.examharmony.Controllers;

import ca.cmpt276.examharmony.Model.DTOs.DepartmentDTO;
import ca.cmpt276.examharmony.Model.examRequest.ExamSlotRequest;
import ca.cmpt276.examharmony.Model.examRequest.ExamSlotRequestRepository;
import ca.cmpt276.examharmony.Model.user.User;
import ca.cmpt276.examharmony.Model.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(InstructorController.class)
class InstructorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstructorController instructorController;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private ExamSlotRequestRepository requestRepo;

    @Test
    @WithMockUser(username = "John", roles = {"INSTRUCTOR"})
    void submitData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("instructorTestPage"));
    }

    @Test
    void submitUnauthenticatedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/home"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "Alexios", roles = {"ADMIN"})
    void submitIncorrectUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/home"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
    void instructorRequests() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User mockInstructor = new User();
        mockInstructor.setName("Alex");
        mockInstructor.setEmailAddress("Test@example.ca");
        mockInstructor.setPassword("123456");

        List<ExamSlotRequest> mockExamSlotRequests = Arrays.asList(
                new ExamSlotRequest(LocalDateTime.parse("2024-04-12 12:48", formatter), 3, "CMPT354",1),
                new ExamSlotRequest(LocalDateTime.parse("2024-04-16 10:30", formatter), 4, "CMPT354", 2)
        );

        when(userRepo.findByUsername("Alex")).thenReturn(mockInstructor);
        when(requestRepo.findExamRequestsByCourseName("CMPT354")).thenReturn(mockExamSlotRequests);

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/examslots/CMPT354"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("viewExamSlotRequests"));
    }

    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
    void testInvalidRequests() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User mockInstructor = new User();
        mockInstructor.setName("Alex");
        mockInstructor.setEmailAddress("Test@example.ca");
        mockInstructor.setPassword("123456");

        List<ExamSlotRequest> badList = Arrays.asList(
                new ExamSlotRequest(LocalDateTime.parse("2024-04-12 12:48", formatter), 3, "CMPT373", 5),
                new ExamSlotRequest(LocalDateTime.parse("2024-04-16 01:00", formatter), -5, "CMPT373", 2)
        );

        when(userRepo.findByUsername("instructor")).thenReturn(mockInstructor);
        when(requestRepo.findExamRequestsByCourseName("CMPT373")).thenReturn(badList);
        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/examslots/CMPT373"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
    void deleteRequest() throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User mockInstructor = new User();
        mockInstructor.setName("Alex");
        mockInstructor.setEmailAddress("Test@example.ca");
        mockInstructor.setPassword("123456");
        ExamSlotRequest testRequest = new ExamSlotRequest(LocalDateTime.parse("2024-04-12 12:48", formatter), 3, "CMPT354", 1);
        List<ExamSlotRequest> mockExamSlotRequests = Arrays.asList(
                testRequest,
                new ExamSlotRequest(LocalDateTime.parse("2024-04-16 10:00", formatter), 4, "CMPT354", 3)
        );

    }

    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
    void getDepartments() throws JsonProcessingException, Exception{
        List<DepartmentDTO> testList = new ArrayList<>();
        testList.add(DepartmentDTO.createDepartment("CMPT", "cmpt", "Computing Science"));
        testList.add(DepartmentDTO.createDepartment("ENSC", "ensc", "Engineering Science"));
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(testList);
        mockMvc.perform(MockMvcRequestBuilders.post("/instructor/get/departments")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("department-selection"));
    }

    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
    void viewDepartments() throws Exception {

        List<DepartmentDTO> testList = new ArrayList<>();
        testList.add(DepartmentDTO.createDepartment("CMPT", "cmpt", "Computing Science"));
        testList.add(DepartmentDTO.createDepartment("ENSC", "ensc", "Engineering Science"));

        Field departmentsField = InstructorController.class.getDeclaredField("departments");
        departmentsField.setAccessible(true);
        departmentsField.set(instructorController, testList);
        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/view/departments/spring"))
                .andExpect(MockMvcResultMatchers.view().name("department-selection"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
    void viewNoDepartments() throws Exception {

        List<DepartmentDTO> testList = new ArrayList<>();

        Field departmentsField = InstructorController.class.getDeclaredField("departments");
        departmentsField.setAccessible(true);
        departmentsField.set(instructorController, testList);

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/view/departments/spring"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("department-selection"));
    }


    @Test
    @WithMockUser(username = "Alex", roles = {"INSTRUCTOR"})
    void addCourse() {
        User mockInstructor = new User();
        mockInstructor.setName("Alex");
        mockInstructor.setEmailAddress("Test@example.ca");
        mockInstructor.setPassword("123456");


    }
}