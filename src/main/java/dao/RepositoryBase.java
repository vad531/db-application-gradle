package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public abstract class RepositoryBase<K, E> implements Repository<K, E> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<K> entityClass;

    public RepositoryBase(Class<K> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public K findById(E id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<K> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getName() + " e", entityClass).getResultList();
    }

    @Override
    public void save(K entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(K entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void update(K entity) {
        entityManager.merge(entity);
    }
}