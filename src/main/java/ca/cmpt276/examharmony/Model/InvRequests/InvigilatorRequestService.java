package ca.cmpt276.examharmony.Model.InvRequests;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvigilatorRequestService {
    @Autowired
    private InvigilatorRequestRepository invigilatorRequestRepository;

    public List<invigilatorRequest> getRequests(String username) {
        List<invigilatorRequest> requests = invigilatorRequestRepository.findByUsername(username);
        System.out.println("Fetched requests for username " + username + ": " + requests.size());
        return requests;
    }

    public invigilatorRequest updateStatus(int id, String status) {
        invigilatorRequest request = invigilatorRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        request.setStatus(status);
        return invigilatorRequestRepository.save(request);
    }

    public invigilatorRequest createRequest(String username, String email, String examCode,
                                            LocalDateTime examDate) {
        invigilatorRequest request = new invigilatorRequest(username, email,  examCode, examDate);
        return invigilatorRequestRepository.save(request);
    }
}
