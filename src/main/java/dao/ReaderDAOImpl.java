package dao;

import entity.Reader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class ReaderDAOImpl implements ReaderDAO {
    private SessionFactory sessionFactory;

    public ReaderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addReader(Reader reader) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(reader);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Reader getReaderById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Reader.class, id);
        }
    }

    @Override
    public List<Reader> getAllReaders() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Reader", Reader.class).list();
        }
    }

    @Override
    public void updateReader(Reader reader) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(reader);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReader(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Reader reader = session.get(Reader.class, id);
            if (reader != null) {
                session.delete(reader);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
