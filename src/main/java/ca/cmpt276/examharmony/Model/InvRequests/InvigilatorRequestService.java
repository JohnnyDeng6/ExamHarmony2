package ca.cmpt276.examharmony.Model.InvRequests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvigilatorRequestService {
    @Autowired
    private InvigilatorRequestRepository invigilatorRequestRepository;

    public List<invigilatorRequest> getRequests(String Username){
        return invigilatorRequestRepository.findByUsername(Username);
    }
    public invigilatorRequest updateStatus(int rid, String status){
    invigilatorRequest req = invigilatorRequestRepository.findById(rid).orElseThrow();
    req.setStatus(status);
    return invigilatorRequestRepository.save(req);
    }
    }
