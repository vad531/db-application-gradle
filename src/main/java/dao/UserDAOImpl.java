package dao;

import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }

    @Override
    public void addUser(String username, String password) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public boolean hasRole(int userId, String roleName) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(ur) FROM UserRole ur " +
                                    "JOIN ur.role r " +
                                    "WHERE ur.user.id = :userId AND r.name = :roleName", Long.class)
                    .setParameter("userId", userId)
                    .setParameter("roleName", roleName)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }

    @Override
    public void addRoleToUser(int userId, String roleName) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Integer roleId = session.createQuery("SELECT r.id FROM Role r WHERE r.name = :roleName", Integer.class)
                    .setParameter("roleName", roleName)
                    .uniqueResult();

            if (roleId != null) {
                session.createNativeQuery("INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)")
                        .setParameter("userId", userId)
                        .setParameter("roleId", roleId)
                        .executeUpdate();
            } else {
                throw new IllegalArgumentException("Роль с именем '" + roleName + "' не найдена.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}