package ca.cmpt276.examharmony.Model.examRequest;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

@Service
public class ExamSlotRequestService {

    @Autowired
    private ExamSlotRequestRepository examSlotRequestRepository;

    public List<ExamSlotRequest> getRequests(String instructorName) {
        return examSlotRequestRepository.findByInstructorName(instructorName);
    }

    public ExamSlotRequest updateStatus(int id, String status) {
        ExamSlotRequest request = examSlotRequestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setStatus(status);
            return examSlotRequestRepository.save(request);
        }
        return null;
    }

    public void createRequest(String instructorName, String courseName, String examCode, LocalDateTime examDate, double examDuration) {
        ExamSlotRequest request = new ExamSlotRequest();
        request.setInstructorName(instructorName);
        request.setCourseName(courseName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        request.setExamDate(examDate.format(formatter));
        request.setExamDuration(examDuration);
        request.setStatus("pending");
        examSlotRequestRepository.save(request);
    }
}
