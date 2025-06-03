package dao;

import model.Seat;
import utility.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.ISeatDAO;
import utility.DbConstants;

public class SeatDAO implements ISeatDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.SEAT_TABLE;
    private static final String ID = DbConstants.SEAT_ID;
    private static final String BUS_ID = DbConstants.BUS_ID;
    private static final String SEAT_NUMBER = DbConstants.SEAT_NUMBER;
    private static final String IS_ACTIVE = DbConstants.SEAT_IS_ACTIVE;

    public SeatDAO(final DbConnect dbConnect) {
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

    private Seat prepareSeatFromResultSet(ResultSet rs) throws SQLException {
        return new Seat(
            rs.getInt(ID),
            rs.getInt(BUS_ID),
            rs.getString(SEAT_NUMBER),
            rs.getBoolean(IS_ACTIVE)
        );
    }

    @Override
    public List<Seat> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<Seat> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareSeatFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final Seat seat) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                TABLE, ID, BUS_ID, SEAT_NUMBER, IS_ACTIVE);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, seat.getSeatId());
            pst.setInt(2, seat.getBusId());
            pst.setString(3, seat.getSeatNumber());
            pst.setBoolean(4, seat.isActive());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final Seat seat) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, BUS_ID, SEAT_NUMBER, IS_ACTIVE, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, seat.getBusId());
            pst.setString(2, seat.getSeatNumber());
            pst.setBoolean(3, seat.isActive());
            pst.setInt(4, seat.getSeatId());
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
