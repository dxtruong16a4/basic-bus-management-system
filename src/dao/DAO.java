package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utility.db.DbConnect;

public class DAO {
    public static Connection con;

    static {
        DbConnect dbConnect = DbConnect.getInstance();
        con = dbConnect.getConnection();
    }

    public static List<String> getAllTableNames() {
        List<String> tableNames = new ArrayList<>();
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = '"
                + utility.db.DbConstants.DATABASE + "'";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tableNames.add(rs.getString("table_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNames;
    }

    public static Map<String, String> getColumnDataTypes(String tableName) {
        Map<String, String> columnDataTypes = new java.util.LinkedHashMap<>();
        String query = "SELECT column_name, column_type FROM information_schema.columns WHERE table_name = ? AND table_schema = '" + utility.db.DbConstants.DATABASE + "'";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, tableName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    columnDataTypes.put(rs.getString("column_name"), rs.getString("column_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnDataTypes;
    }
}
