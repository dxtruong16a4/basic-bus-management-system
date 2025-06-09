package dao;

import model.Route;
import utility.db.DbConnect;
import utility.db.DbConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IRouteDAO;

public class RouteDAO implements IRouteDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.ROUTE_TABLE;
    private static final String ID = DbConstants.ROUTE_ID;
    private static final String ORIGIN_CITY = DbConstants.ORIGIN_CITY;
    private static final String DESTINATION_CITY = DbConstants.DESTINATION_CITY;
    private static final String DISTANCE = DbConstants.DISTANCE;
    private static final String ESTIMATED_DURATION = DbConstants.ESTIMATED_DURATION;

    public RouteDAO(final DbConnect dbConnect) {
        this.dbConnect = dbConnect;
        this.con = dbConnect.getConnection();
    }

    private boolean checkConnection() throws SQLException, ClassNotFoundException {
        if (con == null || con.isClosed()) {
            con = dbConnect.getConnection();
        }
        return con != null && !con.isClosed();
    }

    public String getTableName() {
        return TABLE;
    }

    private Route prepareRouteFromResultSet(ResultSet rs) throws SQLException {
        return new Route(
            rs.getInt(ID),
            rs.getString(ORIGIN_CITY),
            rs.getString(DESTINATION_CITY),
            rs.getBigDecimal(DISTANCE),
            rs.getInt(ESTIMATED_DURATION)
        );
    }

    @Override
    public List<Route> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return Collections.emptyList();

        StringBuilder query = new StringBuilder("SELECT * FROM ").append(TABLE);
        if (whereClause != null && !whereClause.isEmpty()) {
            query.append(" WHERE ").append(whereClause);
        }

        try (PreparedStatement pst = con.prepareStatement(query.toString())) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setString(i + 1, params[i]);
                }
            }

            try (ResultSet rs = pst.executeQuery()) {
                List<Route> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareRouteFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final Route route) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                TABLE, ORIGIN_CITY, DESTINATION_CITY, DISTANCE, ESTIMATED_DURATION);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, route.getOriginCity());
            pst.setString(2, route.getDestinationCity());
            pst.setBigDecimal(3, route.getDistance());
            pst.setInt(4, route.getEstimatedDuration());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final Route route) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, ORIGIN_CITY, DESTINATION_CITY, DISTANCE, ESTIMATED_DURATION, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, route.getOriginCity());
            pst.setString(2, route.getDestinationCity());
            pst.setBigDecimal(3, route.getDistance());
            pst.setInt(4, route.getEstimatedDuration());
            pst.setInt(5, route.getRouteId());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(final int id) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("DELETE FROM %s WHERE %s = ?", TABLE, ID);

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
}
