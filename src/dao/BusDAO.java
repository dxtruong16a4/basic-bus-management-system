package dao;

import model.Bus;
import utility.db.DbConnect;
import utility.db.DbConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IBusDAO;

public class BusDAO implements IBusDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.BUS_TABLE;
    private static final String ID = DbConstants.BUS_ID;
    private static final String NUMBER = DbConstants.BUS_NUMBER;
    private static final String NAME = DbConstants.BUS_NAME;
    private static final String TYPE = DbConstants.BUS_TYPE;
    private static final String TOTAL_SEATS = DbConstants.TOTAL_SEATS;
    private static final String OPERATOR_ID = DbConstants.OPERATOR_ID;
    private static final String REGISTRATION_NUMBER = DbConstants.REGISTRATION_NUMBER;
    private static final String IS_ACTIVE = DbConstants.BUS_IS_ACTIVE;

    public BusDAO(final DbConnect dbConnect) {
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

    private Bus prepareBusFromResultSet(ResultSet rs) throws SQLException {
        return new Bus(
            rs.getInt(ID),
            rs.getString(NUMBER),
            rs.getString(NAME),
            rs.getString(TYPE),
            rs.getInt(TOTAL_SEATS),
            rs.getInt(OPERATOR_ID),
            rs.getString(REGISTRATION_NUMBER),
            rs.getBoolean(IS_ACTIVE)
        );
    }

    @Override
    public List<Bus> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<Bus> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareBusFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final Bus bus) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                TABLE, ID, NUMBER, NAME, TYPE, TOTAL_SEATS, OPERATOR_ID, REGISTRATION_NUMBER, IS_ACTIVE);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, bus.getBusId());
            pst.setString(2, bus.getBusNumber());
            pst.setString(3, bus.getBusName());
            pst.setString(4, bus.getBusType());
            pst.setInt(5, bus.getTotalSeats());
            pst.setInt(6, bus.getOperatorId());
            pst.setString(7, bus.getRegistrationNumber());
            pst.setBoolean(8, bus.isActive());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final Bus bus) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, NUMBER, NAME, TYPE, TOTAL_SEATS, OPERATOR_ID, REGISTRATION_NUMBER, IS_ACTIVE, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, bus.getBusNumber());
            pst.setString(2, bus.getBusName());
            pst.setString(3, bus.getBusType());
            pst.setInt(4, bus.getTotalSeats());
            pst.setInt(5, bus.getOperatorId());
            pst.setString(6, bus.getRegistrationNumber());
            pst.setBoolean(7, bus.isActive());
            pst.setInt(8, bus.getBusId());
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
