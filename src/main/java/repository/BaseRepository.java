package repository;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    public abstract Optional<T> getById(int id);

    public abstract List<T> getAll();

    public abstract T save(T entity);

    public abstract T update(T entity);
}