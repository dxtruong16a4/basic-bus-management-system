package dao;

import model.Payment;
import utility.db.DbConnect;
import utility.db.DbConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IPaymentDAO;

public class PaymentDAO implements IPaymentDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.PAYMENT_TABLE;
    private static final String ID = DbConstants.PAYMENT_ID;
    private static final String BOOKING_ID = DbConstants.BOOKING_ID;
    private static final String AMOUNT = DbConstants.AMOUNT;
    private static final String PAYMENT_DATE = DbConstants.PAYMENT_DATE;
    private static final String PAYMENT_METHOD = DbConstants.PAYMENT_METHOD;
    private static final String TRANSACTION_ID = DbConstants.TRANSACTION_ID;
    private static final String PAYMENT_STATUS = DbConstants.PAYMENT_STATUS;
    private static final String RECEIPT_URL = DbConstants.RECEIPT_URL;

    public PaymentDAO(final DbConnect dbConnect) {
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

    private Payment preparePaymentFromResultSet(ResultSet rs) throws SQLException {
        return new Payment(
            rs.getInt(ID),
            rs.getInt(BOOKING_ID),
            rs.getDouble(AMOUNT),
            rs.getTimestamp(PAYMENT_DATE),
            rs.getString(PAYMENT_METHOD),
            rs.getString(TRANSACTION_ID),
            rs.getString(PAYMENT_STATUS),
            rs.getString(RECEIPT_URL)
        );
    }

    @Override
    public List<Payment> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<Payment> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(preparePaymentFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final Payment payment) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                TABLE, ID, BOOKING_ID, AMOUNT, PAYMENT_DATE, PAYMENT_METHOD, TRANSACTION_ID, PAYMENT_STATUS, RECEIPT_URL);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, payment.getPaymentId());
            pst.setInt(2, payment.getBookingId());
            pst.setDouble(3, payment.getAmount());
            pst.setTimestamp(4, payment.getPaymentDate());
            pst.setString(5, payment.getPaymentMethod());
            pst.setString(6, payment.getTransactionId());
            pst.setString(7, payment.getPaymentStatus());
            pst.setString(8, payment.getReceiptUrl());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final Payment payment) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, BOOKING_ID, AMOUNT, PAYMENT_DATE, PAYMENT_METHOD, TRANSACTION_ID, PAYMENT_STATUS, RECEIPT_URL, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, payment.getBookingId());
            pst.setDouble(2, payment.getAmount());
            pst.setTimestamp(3, payment.getPaymentDate());
            pst.setString(4, payment.getPaymentMethod());
            pst.setString(5, payment.getTransactionId());
            pst.setString(6, payment.getPaymentStatus());
            pst.setString(7, payment.getReceiptUrl());
            pst.setInt(8, payment.getPaymentId());
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
