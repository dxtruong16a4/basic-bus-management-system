package dao;

import model.Fare;
import utility.db.DbConnect;
import utility.db.DbConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IFareDAO;

public class FareDAO implements IFareDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.FARE_TABLE;
    private static final String ID = DbConstants.FARE_ID;
    private static final String BUS_ID = DbConstants.BUS_ID;
    private static final String ROUTE_ID = DbConstants.ROUTE_ID;
    private static final String BASE_FARE = DbConstants.BASE_FARE;
    private static final String TAXES = DbConstants.TAXES;
    private static final String SERVICE_CHARGE = DbConstants.SERVICE_CHARGE;
    private static final String LAST_UPDATED = DbConstants.LAST_UPDATED;

    public FareDAO(final DbConnect dbConnect) {
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

    private Fare prepareFareFromResultSet(ResultSet rs) throws SQLException {
        return new Fare(
            rs.getInt(ID),
            rs.getInt(BUS_ID),
            rs.getInt(ROUTE_ID),
            rs.getBigDecimal(BASE_FARE),
            rs.getBigDecimal(TAXES),
            rs.getBigDecimal(SERVICE_CHARGE),
            rs.getTimestamp(LAST_UPDATED)
        );
    }

    @Override
    public List<Fare> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<Fare> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareFareFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final Fare fare) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                TABLE, BUS_ID, ROUTE_ID, BASE_FARE, TAXES, SERVICE_CHARGE, LAST_UPDATED);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, fare.getBusId());
            pst.setInt(2, fare.getRouteId());
            pst.setBigDecimal(3, fare.getBaseFare());
            pst.setBigDecimal(4, fare.getTaxes());
            pst.setBigDecimal(5, fare.getServiceCharge());
            pst.setTimestamp(6, fare.getLastUpdated());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final Fare fare) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, BUS_ID, ROUTE_ID, BASE_FARE, TAXES, SERVICE_CHARGE, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, fare.getBusId());
            pst.setInt(2, fare.getRouteId());
            pst.setBigDecimal(3, fare.getBaseFare());
            pst.setBigDecimal(4, fare.getTaxes());
            pst.setBigDecimal(5, fare.getServiceCharge());
            pst.setInt(6, fare.getFareId());
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
