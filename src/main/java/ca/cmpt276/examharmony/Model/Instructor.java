package ca.cmpt276.examharmony.Model;

public class Instructor {
    private String code;
    private String duration;
    private String p1;
    private String p2;
    private String p3;
    
    public Instructor() {
    }
    public Instructor(String code, String duration, String p1, String p2, String p3) {
        this.code = code;
        this.duration = duration;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getP1() {
        return p1;
    }
    public void setP1(String p1) {
        this.p1 = p1;
    }
    public String getP2() {
        return p2;
    }
    public void setP2(String p2) {
        this.p2 = p2;
    }
    public String getP3() {
        return p3;
    }
    public void setP3(String p3) {
        this.p3 = p3;
    }

}
