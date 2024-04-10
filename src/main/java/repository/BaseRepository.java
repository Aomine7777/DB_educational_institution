package repository;

import entities.Student;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {
    public abstract Optional<T> getById(int id);

    public abstract List<T> getAll();

    public abstract Optional<T> save(T entity);

    public abstract T update(T entity);
}
