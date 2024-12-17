package dao;

import entity.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GenreDAOImpl implements GenreDAO {
    private SessionFactory sessionFactory;

    public GenreDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addGenre(Genre genre) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(genre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Genre getGenreById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Genre.class, id);
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Genre", Genre.class).list();
        }
    }

    @Override
    public void updateGenre(Genre genre) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(genre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGenre(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Genre genre = session.get(Genre.class, id);
            if (genre != null) {
                session.delete(genre);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}