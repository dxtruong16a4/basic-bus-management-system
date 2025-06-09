package utility.db;

import static utility.db.DbConstants.DB_DRIVER;
import static utility.db.DbConstants.DB_PASSWORD;
import static utility.db.DbConstants.DB_URL;
import static utility.db.DbConstants.DB_USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
