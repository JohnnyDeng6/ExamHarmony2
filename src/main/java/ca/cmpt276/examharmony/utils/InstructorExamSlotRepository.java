package ca.cmpt276.examharmony.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InstructorExamSlotRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void removeUserExamRequestAssociation(UUID userId, int examRequestId) {
        Query query = entityManager.createNativeQuery("DELETE FROM instructor_exam_requests WHERE userid = :userId AND exam_requestid = :examRequestId");
        query.setParameter("userId", userId);
        query.setParameter("examRequestId", examRequestId);
        query.executeUpdate();
    }
}
