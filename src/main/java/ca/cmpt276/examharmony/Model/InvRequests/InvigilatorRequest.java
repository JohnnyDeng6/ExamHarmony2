package ca.cmpt276.examharmony.Model.InvRequests;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "invigilator_request")
public class InvigilatorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private int inv_id;
    private String examCode;
    private LocalDateTime examDate;
    private String status;

    public InvigilatorRequest() {
    }
    public InvigilatorRequest(String username, String email, String examCode,LocalDateTime examDate, String status) {
        this.username = username;
        this.email = email;
        this.examCode = examCode;
        this.examDate = examDate;
        this.status = "pending";
  
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getExamCode() {
        return examCode;
    }
    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }
   
    public LocalDateTime getExamDate() {
        return examDate;
    }
    public void setExamDate(LocalDateTime examDate) {
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
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
