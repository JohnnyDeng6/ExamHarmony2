package ca.cmpt276.examharmony.Model.examRequest;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "exam_request")
public class ExamRequest implements Comparable<ExamRequest>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //1st, 2nd, or 3rd preference
    @Column(name = "preference")
    private int preferenceStatus;
    private int examCode;
    private LocalDateTime examDate;
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
        if(examDuration <= 0){
            throw new RuntimeException("Duration cannot be negative");
        }
        this.examDuration = examDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.examDate = LocalDateTime.parse(examDate, formatter);
    }

    public int getPreferenceStatus() {
        return preferenceStatus;
    }

    public void setPreferenceStatus(int preferenceStatus) {
        this.preferenceStatus = preferenceStatus;
    }

    @Override
    public int compareTo(ExamRequest o) {
        return ~(this.preferenceStatus - o.preferenceStatus);
    }
}

