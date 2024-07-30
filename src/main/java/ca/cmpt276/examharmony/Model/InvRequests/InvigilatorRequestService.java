package ca.cmpt276.examharmony.Model.InvRequests;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvigilatorRequestService {

    @Autowired
    private InvigilatorRequestRepository invigilatorRequestRepository;

    public List<InvigilatorRequest> getRequests(String username) {
        List<InvigilatorRequest> requests = invigilatorRequestRepository.findByUsername(username);
        return requests;
    }

    public InvigilatorRequest updateStatus(int id, String status) {
        InvigilatorRequest request = invigilatorRequestRepository.findById(id);
        request.setStatus(status);
        return invigilatorRequestRepository.save(request);
    }


    public void createRequest(String username, String email, String examCode,
    LocalDateTime examDate) {
        InvigilatorRequest request = new InvigilatorRequest(username, email, examCode, examDate, "pending");
        invigilatorRequestRepository.save(request);
    }
}
