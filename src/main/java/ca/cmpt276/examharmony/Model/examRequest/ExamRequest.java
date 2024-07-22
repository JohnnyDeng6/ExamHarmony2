package ca.cmpt276.examharmony.Model.examRequest;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "exam_request")
public class ExamRequest implements Comparable<ExamRequest>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //1st, 2nd, or 3rd preference
    @Column(name = "preference")
    private int preferenceStatus;
    @Column(name = "exam_code")
    private int examCode;

    @Column(name = "exam_date")
    private LocalDateTime examDate;
    @Column(name = "exam_duration")
    private double examDuration;
    private String status;
    @Column(name = "course_name")
    private String courseName;

    public ExamRequest(int examCode, LocalDateTime examDate, double examDuration, String courseName, int preferenceStatus) {
        this.examCode = examCode;
        this.examDate = examDate;
        this.examDuration = examDuration;
        this.courseName = courseName;
        this.preferenceStatus = preferenceStatus;
    }

    public ExamRequest() {

    }

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
            throw new RuntimeException("Duration must be non-negative");
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
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.examDate = LocalDateTime.parse(examDate, formatter);
        } catch (DateTimeParseException err){
            throw new RuntimeException("Date could not be parsed, check format and make sure it contains valid characters");
        }
    }

    public int getID() {
        return ID;
    }

    public int getPreferenceStatus() {
        return preferenceStatus;
    }

    public void setPreferenceStatus(int preferenceStatus) {
        this.preferenceStatus = preferenceStatus;
    }

    @Override
    public int compareTo(ExamRequest o) {
        return (this.preferenceStatus - o.preferenceStatus);
    }
}

