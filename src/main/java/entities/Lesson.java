package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lesson", schema = "db_education")
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "student_lesson",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    @Column(name = "is_student_present")
    private boolean isStudentPresent;

    @Column(name = "mark")
    private float mark;

    @Column(name = "date")
    private LocalDate date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public boolean isStudentPresent() {
        return isStudentPresent;
    }

    public void setStudentPresent(boolean studentPresent) {
        isStudentPresent = studentPresent;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "\n id=" + id +
                ",\n name='" + name + '\'' +
                ",\n students=" + students +
                ",\n isStudentPresent=" + isStudentPresent +
                ",\n mark=" + mark +
                ",\n date=" + date +
                "\n}";
    }
}