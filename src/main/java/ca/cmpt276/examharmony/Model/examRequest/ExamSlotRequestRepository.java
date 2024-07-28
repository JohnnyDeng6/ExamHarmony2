package ca.cmpt276.examharmony.Model.examRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamSlotRequestRepository extends JpaRepository<ExamSlotRequest, Integer> {

    List<ExamSlotRequest> findExamRequestsByCourseName(String courseName);
//    ExamSlotRequest findExamRequestByExamCode(int ExamCode);
    List<ExamSlotRequest> findExamRequestsByStatus(String status);
    List<ExamSlotRequest> findExamRequestsByStatusAndCourseName(String status, String courseName);
}
