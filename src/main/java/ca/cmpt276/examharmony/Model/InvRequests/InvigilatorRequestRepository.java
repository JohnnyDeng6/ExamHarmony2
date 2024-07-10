package ca.cmpt276.examharmony.Model.InvRequests;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvigilatorRequestRepository extends JpaRepository<invigilatorRequest, Integer> {
    List<invigilatorRequest> findByUsername(String username);
    List<invigilatorRequest> findByEmail(String email);
}
