package utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Authentication {
    public static boolean register(final String username, final String password, String email, final String role) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            if (userExists(username)) return false;
            String encryptedPass = AESUtil.encrypt(password);
            DbConnect db = DbConnect.getInstance();
            con = db.getConnection();
            String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, encryptedPass);
            ps.setString(3, email);
            ps.setString(4, role);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}
        }
    }

    public static boolean login(final String username, final String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DbConnect db = DbConnect.getInstance();
            con = db.getConnection();
            String sql = "SELECT password FROM users WHERE username = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                String encryptedPass = rs.getString("password");
                String inputEncrypted = AESUtil.encrypt(password);
                return encryptedPass != null && encryptedPass.equals(inputEncrypted);
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

    public static boolean userExists(final String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DbConnect db = DbConnect.getInstance();
            con = db.getConnection();
            String sql = "SELECT username FROM users WHERE username = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
        }
    }
}
