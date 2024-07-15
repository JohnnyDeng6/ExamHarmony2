package ca.cmpt276.examharmony.Model.InvRequests;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvigilatorRequestRepository extends JpaRepository<InvigilatorRequest, Integer> {
    List<InvigilatorRequest> findByUsername(String username);
    List<InvigilatorRequest> findByEmail(String email);
}
