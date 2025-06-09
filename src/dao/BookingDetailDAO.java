package dao;

import model.BookingDetail;
import utility.db.DbConnect;
import utility.db.DbConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IBookingDetailDAO;

public class BookingDetailDAO implements IBookingDetailDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.BOOKING_DETAIL_TABLE;
    private static final String ID = DbConstants.BOOKING_DETAIL_ID;
    private static final String BOOKING_ID = DbConstants.BOOKING_ID;
    private static final String SEAT_ID = DbConstants.SEAT_ID;
    private static final String PASSENGER_NAME = DbConstants.PASSENGER_NAME;    
    private static final String PASSENGER_AGE = DbConstants.PASSENGER_AGE;
    private static final String PASSENGER_GENDER = DbConstants.PASSENGER_GENDER;
    private static final String FARE = DbConstants.FARE;
    private static final String SEAT_NUMBER = DbConstants.SEAT_NUMBER       ;

    public BookingDetailDAO(final DbConnect dbConnect) {
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

    private BookingDetail prepareBookingDetailFromResultSet(ResultSet rs) throws SQLException {
        return new BookingDetail(
            rs.getInt(ID),
            rs.getInt(BOOKING_ID),
            rs.getInt(SEAT_ID),
            rs.getString(PASSENGER_NAME),
            rs.getInt(PASSENGER_AGE),
            rs.getString(PASSENGER_GENDER),
            rs.getDouble(FARE),
            rs.getString(SEAT_NUMBER)
        );
    }

    @Override
    public List<BookingDetail> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<BookingDetail> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareBookingDetailFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final BookingDetail bookingDetail) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                TABLE, ID, BOOKING_ID, SEAT_ID, PASSENGER_NAME, PASSENGER_AGE, PASSENGER_GENDER, FARE, SEAT_NUMBER);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, bookingDetail.getBookingDetailId());
            pst.setInt(2, bookingDetail.getBookingId());
            pst.setInt(3, bookingDetail.getSeatId());
            pst.setString(4, bookingDetail.getPassengerName());
            pst.setInt(5, bookingDetail.getPassengerAge());
            pst.setString(6, bookingDetail.getPassengerGender());
            pst.setDouble(7, bookingDetail.getFare());
            pst.setString(8, bookingDetail.getSeatNumber());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final BookingDetail bookingDetail) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, BOOKING_ID, SEAT_ID, PASSENGER_NAME, PASSENGER_AGE, PASSENGER_GENDER, FARE, SEAT_NUMBER, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, bookingDetail.getBookingId());
            pst.setInt(2, bookingDetail.getSeatId());
            pst.setString(3, bookingDetail.getPassengerName());
            pst.setInt(4, bookingDetail.getPassengerAge());
            pst.setString(5, bookingDetail.getPassengerGender());
            pst.setDouble(6, bookingDetail.getFare());
            pst.setString(7, bookingDetail.getSeatNumber());
            pst.setInt(8, bookingDetail.getBookingDetailId());
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
