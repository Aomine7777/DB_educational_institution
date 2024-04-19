package repository;

import entities.Student;
import exceptions.CustomerException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class StudentRepository implements BaseRepository<Student> {

    private final SessionFactory sessionFactory;

    public StudentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    static final Logger LOGGER = LoggerFactory.getLogger(StudentRepository.class);

    @Override
    public Optional<Student> getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Student student = session.get(Student.class, id);
            return Optional.ofNullable(student);
        } catch (CustomerException e) {
            LOGGER.error("Error occurred while get by id student", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Student> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("FROM Student", Student.class);
            return query.getResultList();
        } catch (CustomerException e) {
            LOGGER.error("Error occurred while get students", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Student save(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            return student;
        } catch (CustomerException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occurred while saving student", e);
            return new Student();
        }
    }

    @Override
    public Student update(Student entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return entity;
        } catch (CustomerException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error occurred during update");
            return new Student();
        }
    }

    public Optional<Student> getByUserName(String userName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("From Student Where userName = :userName", Student.class);
            query.setParameter("userName", userName);

            return Optional.ofNullable(query.uniqueResult());
        } catch (CustomerException e) {
            LOGGER.error("Error occurred while get by name student", e);
            return Optional.empty();
        }
    }
}