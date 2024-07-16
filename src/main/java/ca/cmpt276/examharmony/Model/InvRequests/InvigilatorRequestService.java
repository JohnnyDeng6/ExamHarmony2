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
        System.out.println("Fetched requests for username " + username + ": " + requests.size());
        return requests;
    }

    public InvigilatorRequest updateStatus(String username, String examCode, String status) {
        InvigilatorRequest request = invigilatorRequestRepository.findByUsernameAndExamCode(username, examCode)
                .orElseThrow(() -> new IllegalArgumentException("Request not found here"));
        request.setStatus(status);
        return invigilatorRequestRepository.save(request);
    }


    public InvigilatorRequest createRequest(String username, String email, String examCode,
    LocalDateTime examDate, String Status) {
        InvigilatorRequest request = new InvigilatorRequest(username, email, examCode, examDate, Status);
        return invigilatorRequestRepository.save(request);
    }
}
