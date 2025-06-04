package utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

public class UserManager {

    private static UserManager instance = null;
    private User currentUser = null;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager() {

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getUserName() {
        if (currentUser != null) {
            return currentUser.getFirstName() + " " + currentUser.getLastName();
        }
        return "Guest";
    }

    public boolean login(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DbConnect db = DbConnect.getInstance();
            con = db.getConnection();
            if (con == null || con.isClosed()) {
                System.err.println("Database connection is not available.");
                return false;
            }
            password = AESUtil.encrypt(password);
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                currentUser = new User(
                    rs.getInt(DbConstants.USER_ID),
                    rs.getString(DbConstants.USERNAME),
                    rs.getString(DbConstants.PASSWORD),
                    rs.getString(DbConstants.EMAIL),
                    rs.getString(DbConstants.PHONE_NUMBER),
                    rs.getString(DbConstants.FIRST_NAME),
                    rs.getString(DbConstants.LAST_NAME),
                    rs.getDate(DbConstants.DATE_OF_BIRTH),
                    rs.getTimestamp(DbConstants.REGISTRATION_DATE),
                    rs.getTimestamp(DbConstants.LAST_LOGIN),
                    rs.getString(DbConstants.ACCOUNT_STATUS),
                    rs.getString(DbConstants.ROLE)
                );
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
        }
    }

    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }
}