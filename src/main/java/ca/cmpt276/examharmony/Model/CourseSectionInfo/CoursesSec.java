package ca.cmpt276.examharmony.Model.CourseSectionInfo;

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

}
