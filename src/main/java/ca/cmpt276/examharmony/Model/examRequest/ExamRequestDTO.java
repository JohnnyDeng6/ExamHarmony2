package ca.cmpt276.examharmony.Model.examRequest;

public class ExamRequestDTO implements Comparable<ExamRequestDTO>{

    public int preferenceStatus;
    public int examCode;
    public String examDate;
    public double examDuration;
    public String courseName;

    @Override
    public int compareTo(ExamRequestDTO o) {
        return ~(this.preferenceStatus - o.preferenceStatus);
    }
}

