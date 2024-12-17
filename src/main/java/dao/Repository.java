package dao;

import java.util.List;

public interface Repository<K, E> {
    K findById(E id);
    List<K> findAll();
    void save(K entity);
    void delete(K entity);
    void update(K entity);
}