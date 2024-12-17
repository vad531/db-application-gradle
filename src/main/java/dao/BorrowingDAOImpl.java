package dao;

import entity.Borrowing;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BorrowingDAOImpl implements BorrowingDAO {
    private SessionFactory sessionFactory;

    public BorrowingDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBorrowing(Borrowing borrowing) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(borrowing);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Borrowing getBorrowingById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Borrowing.class, id);
        }
    }

    @Override
    public List<Borrowing> getAllBorrowings() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Borrowing", Borrowing.class).list();
        }
    }

    @Override
    public void updateBorrowing(Borrowing borrowing) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(borrowing);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBorrowing(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Borrowing borrowing = session.get(Borrowing.class, id);
            if (borrowing != null) {
                session.delete(borrowing);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}