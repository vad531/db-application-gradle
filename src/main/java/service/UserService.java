package service;

import dao.UserDAO;
import entity.User;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(String username, String password) {

        if (userDAO.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Пользователь уже существует");
        }

        userDAO.addUser(username, password);
        int userId = userDAO.getUserByUsername(username).getUserId();
        userDAO.addRoleToUser(userId, "USER");
    }

    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public boolean hasRole(int userId, String roleName) {
        return userDAO.hasRole(userId, roleName);
    }
}
