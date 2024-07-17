package ca.cmpt276.examharmony.Model.CourseSectionInfo;
import ca.cmpt276.examharmony.Model.examSlot.examSlot;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "course_section")
public class CoursesSec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseName;

    private String department;

    private boolean examSlotStatus;

    @OneToMany(mappedBy ="CourseID")
    private List<examSlot> examSlot;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean getExamSlotStatus() {
        return examSlotStatus;
    }

    public void setExamSlotStatus(boolean examSlotStatus) {
        this.examSlotStatus = examSlotStatus;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

   
    public static CoursesSec CreateNewCourse(String department, String courseName){
        CoursesSec newCourse = new CoursesSec();
        newCourse.courseName = courseName;
        newCourse.department = department;
        newCourse.examSlotStatus = false;
        return newCourse;
    }

}
