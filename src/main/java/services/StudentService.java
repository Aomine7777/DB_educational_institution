package services;

import entities.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.StudentRepository;

import java.util.Optional;
import java.util.Scanner;

public class StudentService extends StudentRepository {

    public StudentService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    StudentRepository studentRepository = new StudentRepository(factory);
    Scanner scanner = new Scanner(System.in);

    public void createStudent() {
        System.out.println("Створення студента.");
        System.out.println("Введіть ім'я нового студента:");
        String newStudentName = scanner.nextLine();

        System.out.println("Введіть призвище нового студента:");
        String inputUserName = scanner.nextLine();

        Optional<Student> existingStudentOptional = studentRepository.getByUserName(inputUserName);

        if (existingStudentOptional.isPresent()) {
            System.out.println("Студент з таким призвищем вже існує");
        } else {
            Student newStudent = new Student();
            newStudent.setName(newStudentName);
            newStudent.setUserName(inputUserName);

            Student savedStudent = studentRepository.save(newStudent);

            if (savedStudent != null) {
                System.out.println("Новий студент успішно створений!");
                System.out.println("Id нового студента: " + savedStudent.getId());
            } else {
                System.out.println("Помилка при створенні нового студента");
            }
        }
    }
}