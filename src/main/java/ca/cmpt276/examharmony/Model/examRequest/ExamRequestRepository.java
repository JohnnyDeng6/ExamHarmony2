package ca.cmpt276.examharmony.Model.examRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRequestRepository extends JpaRepository<ExamRequest, Integer> {

    List<ExamRequest> findExamRequestsByCourseName(String courseName);
    ExamRequest findExamRequestByExamCode(int ExamCode);
    List<ExamRequest> findExamRequestsByStatus(String status);
    List<ExamRequest> findExamRequestsByStatusAndCourseName(String status, String courseName);
}
