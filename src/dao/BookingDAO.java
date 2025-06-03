package dao;

import model.Booking;
import utility.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IBookingDAO;
import utility.DbConstants;

public class BookingDAO implements IBookingDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.BOOKING_TABLE;
    private static final String ID = DbConstants.BOOKING_ID;
    private static final String USER_ID = DbConstants.USER_ID;
    private static final String SCHEDULE_ID = DbConstants.SCHEDULE_ID;
    private static final String BOOKING_DATE = DbConstants.BOOKING_DATE;
    private static final String TOTAL_FARE = DbConstants.TOTAL_FARE;
    private static final String BOOKING_STATUS = DbConstants.BOOKING_STATUS;
    private static final String PAYMENT_STATUS = DbConstants.PAYMENT_STATUS;
    private static final String NUMBER_OF_SEATS = DbConstants.NUMBER_OF_SEATS;
    private static final String TRIP_DATE = DbConstants.TRIP_DATE;

    public BookingDAO(final DbConnect dbConnect) {
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

    private Booking prepareBookingFromResultSet(ResultSet rs) throws SQLException {
        return new Booking(
            rs.getInt(ID),
            rs.getInt(USER_ID),
            rs.getInt(SCHEDULE_ID),
            rs.getTimestamp(BOOKING_DATE),
            rs.getDouble(TOTAL_FARE),
            rs.getString(BOOKING_STATUS),
            rs.getString(PAYMENT_STATUS),
            rs.getInt(NUMBER_OF_SEATS),
            rs.getDate(TRIP_DATE)
        );
    }

    @Override
    public List<Booking> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<Booking> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareBookingFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final Booking booking) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                TABLE, ID, USER_ID, SCHEDULE_ID, BOOKING_DATE, TOTAL_FARE, BOOKING_STATUS, PAYMENT_STATUS, NUMBER_OF_SEATS, TRIP_DATE);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, booking.getBookingId());
            pst.setInt(2, booking.getUserId());
            pst.setInt(3, booking.getScheduleId());
            pst.setTimestamp(4, booking.getBookingDate());
            pst.setDouble(5, booking.getTotalFare());
            pst.setString(6, booking.getBookingStatus());
            pst.setString(7, booking.getPaymentStatus());
            pst.setInt(8, booking.getNumberOfSeats());
            pst.setDate(9, new java.sql.Date(booking.getTripDate().getTime()));
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final Booking booking) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, USER_ID, SCHEDULE_ID, BOOKING_DATE, TOTAL_FARE, BOOKING_STATUS, PAYMENT_STATUS, NUMBER_OF_SEATS, TRIP_DATE, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, booking.getUserId());
            pst.setInt(2, booking.getScheduleId());
            pst.setTimestamp(3, booking.getBookingDate());
            pst.setDouble(4, booking.getTotalFare());
            pst.setString(5, booking.getBookingStatus());
            pst.setString(6, booking.getPaymentStatus());
            pst.setInt(7, booking.getNumberOfSeats());
            pst.setDate(8, new java.sql.Date(booking.getTripDate().getTime()));
            pst.setInt(9, booking.getBookingId());
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
