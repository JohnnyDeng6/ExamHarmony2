package ca.cmpt276.examharmony.Model.examRequest;

public class ExamSlotRequestDTO implements Comparable<ExamSlotRequestDTO>{

    public int preferenceStatus;
    public int examCode;
    public String examDate;
    public double examDuration;
    public String courseName;

    public String instructorName;

    @Override
    public int compareTo(ExamSlotRequestDTO o) {
        return (this.preferenceStatus - o.preferenceStatus);
    }
}

