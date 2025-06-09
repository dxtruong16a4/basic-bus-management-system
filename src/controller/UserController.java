package controller;

import java.util.List;

import dao.UserDAO;
import model.User;
import utility.db.DbConnect;

public class UserController {
    private DbConnect dbConnect = null;
    private UserDAO userDAO = null;
    private static UserController instance = null;
    
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
    
    private UserController() {
        dbConnect = DbConnect.getInstance();
        userDAO = new UserDAO(dbConnect);
    }

    public boolean addUser(User user) {
        try {
            return userDAO.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            return userDAO.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        try {
            return userDAO.delete(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public User getUserBy(String whereClause, String[] params) {
        try {
            List<User> users = userDAO.select(whereClause, params);
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DbConnect getDbConnect() {
        return dbConnect;
    }

    // public Map<String, String> getColumnDataTypes() {
    //     try {
    //         return DAO.getColumnDataTypes(userDAO.getTableName());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return Map.of();
    //     }
    // }
}
