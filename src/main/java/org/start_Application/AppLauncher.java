package org.start_Application;

import entities.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import services.LessonService;
import services.StudentService;

import java.util.Optional;
import java.util.Scanner;

public class AppLauncher {
    static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    static LessonService lessonService = new LessonService(factory);
    static StudentService studentService = new StudentService(factory);
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true){
            System.out.println("Меню");
            System.out.println("Обери 0, якщо бажаешь додати студента");
            System.out.println("Обери 1, якщо бажаешь додати урок");
            System.out.println("Обери 2, якщо бажаешь вивести уроки");
            System.out.println("Обери 3, якщо бажаешь вивести всі уроки за певну дату");
            System.out.println("Обери 4, якщо бажаешь вивести всі уроки на яких був студент");
            System.out.println("Обери 5, якщо бажаешь вивести всіх студентів з середньою оцінкою за урок вище певної");
            System.out.println("Обери 6, якщо бажаешь вивести всіх студентів з середньою оцінкою за дату вище певної");

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice){
                case 0:
                    studentService.createStudent();
                case 1:
                    lessonService.createLesson();
                case 2:
                 lessonService.printLimitedLessons();
                case 3:
                    lessonService.printLimitedLessonsByDate();

                case 4:
                     lessonService.printLessonsByStudentId();

                case 5:
                     lessonService.printLessonsByLessonIdAndAverageMark();
                case 6:
                    lessonService.printLessonsByDateAndAverageMark();
            }

        }

    }
}