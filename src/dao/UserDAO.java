package dao;

import model.User;
import utility.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IUserDAO;

import static utility.DbConstants.*;

public class UserDAO implements IUserDAO {
    private final DbConnect dbConnect;
    private Connection con;

    public UserDAO(final DbConnect dbConnect) {
        this.dbConnect = dbConnect;
        this.con = dbConnect.getConnection();
    }

    private boolean checkConnection() throws SQLException, ClassNotFoundException {
        if (con == null || con.isClosed()) {
            con = dbConnect.getConnection();
        }
        return con != null && !con.isClosed();
    }

    @Override
    public List<User> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            return Collections.emptyList();
        }
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(USER_TABLE);
        if (whereClause != null && !whereClause.isEmpty()) {
            query.append(" WHERE ").append(whereClause);
        }
        List<User> result = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(query.toString())) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setString(i + 1, params[i]);
                }
            }
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                        rs.getInt(USER_ID),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD),
                        rs.getString(EMAIL),
                        rs.getString(PHONE_NUMBER),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME),
                        rs.getDate(DATE_OF_BIRTH),
                        rs.getTimestamp(REGISTRATION_DATE),
                        rs.getTimestamp(LAST_LOGIN),
                        rs.getString(ACCOUNT_STATUS),
                        rs.getString(ROLE)
                    );
                    result.add(user);
                }
            }
        }
        return result;
    }

    @Override
    public boolean insert(final User user) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            return false;
        }
        String query = "INSERT INTO " + USER_TABLE + " ("
                + USERNAME + ", "
                + PASSWORD + ", "
                + EMAIL + ", "
                + PHONE_NUMBER + ", "
                + FIRST_NAME + ", "
                + LAST_NAME + ", "
                + DATE_OF_BIRTH + ", "
                + REGISTRATION_DATE + ", "
                + LAST_LOGIN + ", "
                + ACCOUNT_STATUS + ", "
                + ROLE + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPhoneNumber());
            pst.setString(5, user.getFirstName());
            pst.setString(6, user.getLastName());
            pst.setDate(7, user.getDateOfBirth());
            pst.setTimestamp(8, user.getRegistrationDate());
            pst.setTimestamp(9, user.getLastLogin());
            pst.setString(10, user.getAccountStatus());
            pst.setString(11, user.getRole());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final User user) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;
        String query = "UPDATE " + USER_TABLE + " SET " +
                USERNAME + " = ?, " +
                PASSWORD + " = ?, " +
                EMAIL + " = ?, " +
                PHONE_NUMBER + " = ?, " +
                FIRST_NAME + " = ?, " +
                LAST_NAME + " = ?, " +
                DATE_OF_BIRTH + " = ?, " +
                REGISTRATION_DATE + " = ?, " +
                LAST_LOGIN + " = ?, " +
                ACCOUNT_STATUS + " = ?, " +
                ROLE + " = ? WHERE " + USER_ID + " = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPhoneNumber());
            pst.setString(5, user.getFirstName());
            pst.setString(6, user.getLastName());
            pst.setDate(7, user.getDateOfBirth());
            pst.setTimestamp(8, user.getRegistrationDate());
            pst.setTimestamp(9, user.getLastLogin());
            pst.setString(10, user.getAccountStatus());
            pst.setString(11, user.getRole());
            pst.setInt(12, user.getUserId());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(final int id) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;
        String query = "DELETE FROM " + USER_TABLE + " WHERE " + USER_ID + " = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public void close() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    public boolean updateUserRole(final String username, final String newRole) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            return false;
        }
        String query = "UPDATE " + USER_TABLE + " SET " + ROLE + " = ? WHERE " + USERNAME + " = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, newRole);
            pst.setString(2, username);
            return pst.executeUpdate() > 0;
        }
    }

    public boolean updateLastLogin(final String username) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) {
            return false;
        }
        String query = "UPDATE " + USER_TABLE + " SET " + LAST_LOGIN + " = CURRENT_TIMESTAMP WHERE " + USERNAME + " = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            return pst.executeUpdate() > 0;
        }
    }
}