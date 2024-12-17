package dao;

import entity.User;

public interface UserDAO {
    User getUserByUsername(String username);

    void addUser(String username, String password);

    void addRoleToUser(int userId, String roleName);

    boolean hasRole(int userId, String roleName);
}

