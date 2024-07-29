package ca.cmpt276.examharmony.Model.examSlot;
import ca.cmpt276.examharmony.Model.CourseSectionInfo.CoursesSec;

import jakarta.persistence.*;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_Slot")
public class examSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CoursesSec CourseID;

    private LocalDateTime StartTime;
    private double duration;
    private int numOfRooms;
    private String assignedRooms;
    private int numInvigilator;
    
    // @ManyToOne
    // @JoinColumn(name = "users", referencedColumnName="uuid")
    // private UUID admin;
    // /*Admin ID */

    private String status;

    

    public int getId() {
        return this.id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public CoursesSec getCourseID() {
        return this.CourseID;
    }

    public void setCourseID(CoursesSec CourseID) {
        this.CourseID = CourseID;
    }

    public LocalDateTime getStartTime() {
        return this.StartTime;
    }

    public void setStartTime(LocalDateTime StartTime) {
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

    public String getAssignedRooms() {
        return this.assignedRooms;
    }

    public void setAssignedRooms(String assignedRooms) {
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
