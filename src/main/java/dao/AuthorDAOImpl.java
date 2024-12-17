package dao;

import entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {
    private SessionFactory sessionFactory;

    public AuthorDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * HQL запрос для получения всех авторов, чье имя начинается с заданной буквы используя HQL
     */
    public List<Author> getAuthorsByInitialLetter(String initial) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Author a WHERE a.name LIKE :initial";
            return session.createQuery(hql, Author.class)
                    .setParameter("initial", initial + "%") //
                    .list();
        }
    }

    @Override
    public void addAuthor(Author author) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(author);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Author getAuthorById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Author.class, id);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Author", Author.class).list();
        }
    }

    @Override
    public void updateAuthor(Author author) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(author);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAuthor(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Author author = session.get(Author.class, id);
            if (author != null) {
                session.delete(author);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}