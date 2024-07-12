package ca.cmpt276.examharmony.Model;

public class DepartmentDTO {

    public String text;
    public String value;
    public String name;

    public static DepartmentDTO createDepartment(String text, String value, String name){
        DepartmentDTO newDepartment = new DepartmentDTO();
        newDepartment.text = text;
        newDepartment.value = value;
        newDepartment.name = name;
        return newDepartment;
    }
}
