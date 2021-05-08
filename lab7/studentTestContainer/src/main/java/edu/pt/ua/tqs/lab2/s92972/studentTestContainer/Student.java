package edu.pt.ua.tqs.lab2.s92972.studentTestContainer;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long nmec;
    @Column(name="name")
    private String name;
    @Column(name="course")
    private String course;

    public Student(Long nmec, String name, String course) {
        this.nmec = nmec;
        this.name = name;
        this.course = course;
    }

    public Student() {
    }

    public Long getNmec() {
        return nmec;
    }

    public void setNmec(Long nmec) {
        this.nmec = nmec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nmec=" + nmec +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return nmec == student.nmec && Objects.equals(name, student.name) && Objects.equals(course, student.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nmec, name, course);
    }
}
