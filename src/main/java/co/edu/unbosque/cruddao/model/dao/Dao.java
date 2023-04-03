package co.edu.unbosque.cruddao.model.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(final Integer id);

    List<T> getAll();

    Optional<T> save(final T t);

    Optional<T> update(final T t);

    Boolean delete(final Integer id);
}