package repository;

import entities.Lesson;
import entities.Student;
import exceptions.CustomerException;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LessonRepository extends BaseRepository<Lesson> {
    private final SessionFactory sessionFactory;

    public LessonRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    static final Logger LOGGER = LoggerFactory.getLogger(LessonRepository.class);

    @Override
    public Optional<Lesson> getById(int id) {
        try{Session session = sessionFactory.openSession();
            String hql = "FROM Lesson WHERE id = :id";
            Query<Lesson> query = session.createQuery(hql);
            query.setParameter("id",id);
            Lesson lesson = query.uniqueResult();
            return Optional.ofNullable(lesson);
        }catch (CustomerException e){
            LOGGER.error("Помилка при отриманні уроку за ID", e);
            return Optional.empty();
        }
    }
    public Optional<Lesson> getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Lesson WHERE name = :name";
            Query<Lesson> query = session.createQuery(hql, Lesson.class);
            query.setParameter("name", name);
            Lesson lesson = query.uniqueResult();
            return Optional.ofNullable(lesson);
        } catch (CustomerException e) {
            LOGGER.error("Error occurred while retrieving lesson by name", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Lesson> getAll() {
        List<Lesson> allLesons = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            String hql = "From Lesson";
            Query<Lesson> query = session.createQuery(hql, Lesson.class);
             allLesons = query.getResultList();
        } catch (CustomerException e){
            LOGGER.error("Error occurred while retrieving all lessons", e);
        }
        return allLesons;
    }

    public List<Lesson> getByDate(LocalDate date) {
        String hql = "SELECT DISTINCT lesson FROM Lesson lesson LEFT JOIN FETCH lesson.students WHERE lesson.date = :date";
        List<Lesson> lessons = null;
        try(Session session = sessionFactory.openSession()){
            lessons = session.createQuery(hql, Lesson.class)
                    .setParameter("date",date)
                    .getResultList();
        } catch (CustomerException e){
            LOGGER.error("Error occurred while retrieving all lessons", e);
        }
        return lessons;
    }
    public List<Lesson> getByStudentId(int studentId) {
        String hql = "SELECT DISTINCT lesson FROM Lesson lesson LEFT JOIN FETCH lesson.students student WHERE student.id = :studentId";
        List<Lesson> lessons = null;
        try(Session session = sessionFactory.openSession()){
            lessons = session.createQuery(hql, Lesson.class)
                    .setParameter("studentId",studentId)
                    .getResultList();
        } catch (CustomerException e){
            LOGGER.error("Error occurred while retrieving all lessons", e);
        }
        return lessons;
    }
    public List<Lesson> getByStudentAverageMArk(float mark, int id) {
        String hql = "SELECT DISTINCT lesson FROM Lesson lesson LEFT JOIN FETCH lesson.students student WHERE lesson.id = :id and lesson.mark > :mark";
        List<Lesson> lessons = null;
        try(Session session = sessionFactory.openSession()){
            lessons = session.createQuery(hql, Lesson.class)
                    .setParameter("mark",mark)
                    .setParameter("id",id)
                    .getResultList();
        } catch (CustomerException e){
            LOGGER.error("Error occurred while retrieving all lessons", e);
        }
        return lessons;
    }
    public List<Lesson> getByStudentAverageMArkAndDAte(float mark, LocalDate date) {
        String hql = "SELECT DISTINCT lesson FROM Lesson lesson LEFT JOIN FETCH lesson.students student WHERE lesson.date = :date and lesson.mark > :mark";
        List<Lesson> lessons = null;
        try(Session session = sessionFactory.openSession()){
            lessons = session.createQuery(hql, Lesson.class)
                    .setParameter("mark",mark)
                    .setParameter("date",date)
                    .getResultList();
        } catch (CustomerException e){
            LOGGER.error("Error occurred while retrieving all lessons", e);
        }
        return lessons;
    }

    public Optional<Lesson> save(Lesson lesson) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(lesson);
            transaction.commit();
            return Optional.ofNullable(lesson);
        } catch (CustomerException e){
            LOGGER.error("Error occurred while saving lesson", e);
            return Optional.empty();
        }
    }

    @Override
    public Lesson update(Lesson entity) {
        return null;
    }
    public List<Lesson> fetchLimitedLessons(int limit) {
        String hql = "SELECT DISTINCT lesson FROM Lesson lesson LEFT JOIN FETCH lesson.students";
        try (Session session = sessionFactory.openSession()) {
            Query<Lesson> query = session.createQuery(hql, Lesson.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching limited lessons", e);
            return Collections.emptyList();
        }
    }
}
