package ca.cmpt276.examharmony.Model.examSlot;
import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;

import jakarta.persistence.*;

import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "exam_Slot")
public class examSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CoursesSec CourseID;

    private LocalDate StartTime;
    private double duration;
    private int numOfRooms;
    private int assignedRooms;
    private int numInvigilator;
    
    // @ManyToOne
    // @JoinColumn(name = "users", referencedColumnName="uuid")
    // private UUID admin;
    // /*Admin ID */

    private String status;

    

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public CoursesSec getCourseID() {
        return this.CourseID;
    }

    public void setCourseID(CoursesSec CourseID) {
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

    // public void setAdmin(UUID admin) {
    //     this.admin = admin;
    // }

    // public UUID getAdmin(){
    //     return this.admin;
    // }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

}
