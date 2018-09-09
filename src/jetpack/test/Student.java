package jetpack.test;

import jetpack.FormatType;
import jetpack.annotation.JsonKey;
import jetpack.annotation.JsonObject;

@JsonObject(formatBy = FormatType.OBJECT)
public class Student {

    @JsonKey
    private String name;

    @JsonKey
    private String school;

    @JsonKey
    private String major;

    private int age;

    private Student[] students;

    public Student(String name, String school, String major, int age, Student ... students) {
        this.name = name;
        this.school = school;
        this.major = major;
        this.age = age;
        this.students = students;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }
}
