package services;

import entities.Lesson;
import entities.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.LessonRepository;
import repository.StudentRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;


public class LessonService extends LessonRepository {

    static final Logger LOGGER = LoggerFactory.getLogger(LessonService.class);

    public LessonService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    LessonRepository lessonRepository = new LessonRepository(factory);

    StudentRepository studentRepository = new StudentRepository(factory);
    Scanner scanner = new Scanner(System.in);

    public void createLesson() {
        System.out.println("Створення предмету");
        System.out.println("Введіть назву предмету");
        String lessonName = scanner.nextLine();

        Lesson newLesson = new Lesson();
        newLesson.setName(lessonName);

        System.out.println("Чи присутній студент? (true/false):");
        boolean isStudentPresent = scanner.nextBoolean();
        newLesson.setStudentPresent(isStudentPresent);

        Float mark = 0f;

        if (isStudentPresent) {
            System.out.println("Введіть оцінку");
            mark = scanner.nextFloat();
            newLesson.setMark(mark);
        }

        System.out.println("Введіть дату уроку (рік-місяць-день):");
        String dateStr = scanner.next();
        LocalDate date = LocalDate.parse(dateStr);
        newLesson.setDate(date);

        System.out.println("введіть id студента (0 - завершити):");
        int studentID;
        while ((studentID = scanner.nextInt()) != 0) {
            Optional<Student> studentOptional = studentRepository.getById(studentID);
            if (studentOptional.isPresent()) {
                newLesson.getStudents().add(studentOptional.get());
            } else {
                System.out.println("Студент з таким ID не знайдено.");
            }
            System.out.println("Введіть наступний id студента (0 - завершити):");
        }
        lessonRepository.save(newLesson);
        System.out.println("Урок уcпішно додано.");
    }

    public void printLimitedLessons() {
        try {
            System.out.println("Введи ліміт отриманих уроків");
            int limit = scanner.nextInt();
            List<Lesson> lessons = lessonRepository.fetchLimitedLessons(limit);
            printLessons(lessons);
        } catch (Exception e) {
            LOGGER.error("Error occurred while printing limited lessons", e);
        }
    }


    public void printLimitedLessonsByDate() {
        try {
            System.out.println("Введіть дату уроку (рік-місяць-день):");
            String inputDate = scanner.nextLine();
            LocalDate date = LocalDate.parse(inputDate);
            List<Lesson> lessons = getByDate(date);

            printLessons(lessons);
        } catch (DateTimeParseException e) {
            System.out.println("Некоректна дата! Будь ласка, введіть дату у форматі рік-місяць-день (наприклад, 2024-04-05).");
        } catch (Exception e) {
            LOGGER.error("Error occurred while printing limited lessons", e);
        }
    }

    public void printLessons(List<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            System.out.println(lesson.toString());
        }
    }

    public void printLessonsByStudentId() {
        try {
            System.out.println("Введіть ідентифікатор студента:");
            int studentId = scanner.nextInt();

            List<Lesson> lessons = getByStudentId(studentId);
            printLessons(lessons);
        } catch (InputMismatchException e) {
            System.out.println("Некоректний формат ідентифікатора студента!");
        } catch (Exception e) {
            LOGGER.error("Error occurred while printing lessons by student ID", e);
        }
    }

    public void printLessonsByLessonIdAndAverageMark() {
        try {
            System.out.println("Введіть ID урока:");
            int lessonId = scanner.nextInt();

            System.out.println("Введіть значення середньої оцінки:");
            float averageMark = scanner.nextFloat();

            List<Lesson> lessons = getByStudentAverageMark(averageMark, lessonId);
            printLessons(lessons);
        } catch (InputMismatchException e) {
            System.out.println("Некоректний формат введених даних!");
        } catch (Exception e) {
            LOGGER.error("Помилка при виведенні уроків за ID та середньою оцінкою", e);
        }
    }

    public void printLessonsByDateAndAverageMark() {
        try {
            System.out.println("Введіть дату уроків (рік-місяць-день):");
            String inputDate = scanner.nextLine();
            LocalDate date = LocalDate.parse(inputDate);

            System.out.println("Введіть значення середньої оцінки:");
            float averageMark = scanner.nextFloat();

            List<Lesson> lessons = getByStudentAverageMarkAndDate(averageMark, date);
            printLessons(lessons);
        } catch (DateTimeParseException e) {
            System.out.println("Некоректна дата! Будь ласка, введіть дату у форматі рік-місяць-день (наприклад, 2024-04-05).");
        } catch (Exception e) {
            LOGGER.error("Error occurred while printing limited lessons", e);
        }
    }
}