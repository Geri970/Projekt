package hu.csongor.demo.dto;

public class RegisterRequest {

    private String nev;
    private String jelszo;

    private boolean teacher;
    private String teacherCode;

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }
}