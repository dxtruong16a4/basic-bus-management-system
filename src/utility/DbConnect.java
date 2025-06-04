package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static utility.DbConstants.DB_DRIVER;
import static utility.DbConstants.DB_URL;
import static utility.DbConstants.DB_USER;
import static utility.DbConstants.DB_PASSWORD;

public class DbConnect {
    public static DbConnect instance = null;
    private Connection con = null;

    public static DbConnect getInstance() {
        if (instance == null) {
            instance = new DbConnect();
        }
        return instance;
    }
    
    private DbConnect() {
        try {
            Class.forName(DB_DRIVER);
            this.con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Database driver not found", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public Connection getConnection() {
        return this.con;
    }
    
    public void Close() throws SQLException {
        if (this.con != null) {
            this.con.close();
        }
    }
    
    public static void main(String[] args) {
        DbConnect dbc =  DbConnect.getInstance();
    }
}
