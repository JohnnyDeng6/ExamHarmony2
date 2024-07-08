package ca.cmpt276.examharmony.Model.examRequest;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "exam_request")
public class ExamRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private int examCode;
    private LocalDate examDate;
    private double examDuration;
    private String status;
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public int getExamCode() {
        return examCode;
    }

    public void setExamCode(int examCode) {
        this.examCode = examCode;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(double examDuration) {
        this.examDuration = examDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }
}
