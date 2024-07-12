package ca.cmpt276.examharmony.Model.CourseSectionInfo;
import ca.cmpt276.examharmony.Model.examSlot.examSlot;

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

    @OneToOne(mappedBy ="CourseID")
    private examSlot examSlot;

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

    public examSlot getExamSlot(){
        return examSlot;
    }

    public void setExamSlot(examSlot e){
        this.examSlot = e;
    }

    public static CoursesSec CreateNewCourse(String department, String courseName){
        CoursesSec newCourse = new CoursesSec();
        newCourse.courseName = courseName;
        newCourse.department = department;
        newCourse.examSlotStatus = false;
        return newCourse;
    }

}
