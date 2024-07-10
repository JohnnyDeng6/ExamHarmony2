package ca.cmpt276.examharmony.Model.examSlot;
import ca.cmpt276.examharmony.Model.user.User;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "exam_Slot")
public class examSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @OneToOne(mappedBy = "course_section", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private int CourseID;

    private LocalDate StartTime;
    private double duration;
    private int numOfRooms;
    private int assignedRooms;
    private int numInvigilator;
    
    @ManyToOne
    @JoinColumn(name = "adminID", referencedColumnName="adminID")
    private User admin;
    /*Admin ID */

    private String status;

    

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCourseID() {
        return this.CourseID;
    }

    public void setCourseID(int CourseID) {
        this.CourseID = CourseID;
    }

    public LocalDate getStartTime() {
        return this.StartTime;
    }

    public void setStartTime(LocalDate StartTime) {
        this.StartTime = StartTime;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getNumOfRooms() {
        return this.numOfRooms;
    }

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public int getAssignedRooms() {
        return this.assignedRooms;
    }

    public void setAssignedRooms(int assignedRooms) {
        this.assignedRooms = assignedRooms;
    }
    
    public int getNumInvigilator() {
        return this.numInvigilator;
    }

    public void setNumInvigilator(int numInvigilator) {
        this.numInvigilator = numInvigilator;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public User getAdmin(){
        return this.admin;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

}
