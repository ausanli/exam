package repositories;

import java.util.List;

public interface Repository<K, T> {
    T findById(K id);

    List<T> findAll();

    void deleteById(K id);

    void save(T employee);
}
