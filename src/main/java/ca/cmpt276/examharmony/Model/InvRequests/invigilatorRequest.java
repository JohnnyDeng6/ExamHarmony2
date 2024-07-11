package ca.cmpt276.examharmony.Model.InvRequests;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "invigilator_request")
public class invigilatorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private String username;
    private String email;
    private int inv_id;
    private int examCode;
    private LocalDate examDate;
    private String status;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getExamCode() {
        return examCode;
    }
    public void setExamCode(int examCode) {
        this.examCode = examCode;
    }
    public LocalDate getExamDate() {
        return examDate;
    }
    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getInv_id() {
        return inv_id;
    }
    public void setInv_id(int inv_id) {
        this.inv_id = inv_id;
    }
    
}
