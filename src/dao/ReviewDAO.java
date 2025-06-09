package dao;

import model.Review;
import utility.db.DbConnect;
import utility.db.DbConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IReviewDAO;

public class ReviewDAO implements IReviewDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.REVIEW_TABLE;
    private static final String ID = DbConstants.REVIEW_ID;
    private static final String USER_ID = DbConstants.USER_ID;
    private static final String BUS_ID = DbConstants.BUS_ID;
    private static final String OPERATOR_ID = DbConstants.OPERATOR_ID;
    private static final String RATING = DbConstants.RATING;
    private static final String REVIEW_TEXT = DbConstants.REVIEW_TEXT;
    private static final String REVIEW_DATE = DbConstants.REVIEW_DATE;
    private static final String TRIP_DATE = DbConstants.TRIP_DATE;

    public ReviewDAO(final DbConnect dbConnect) {
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

    private Review prepareReviewFromResultSet(ResultSet rs) throws SQLException {
        return new Review(
            rs.getInt(ID),
            rs.getInt(USER_ID),
            rs.getInt(BUS_ID),
            rs.getInt(OPERATOR_ID),
            rs.getInt(RATING),
            rs.getString(REVIEW_TEXT),
            rs.getTimestamp(REVIEW_DATE),
            rs.getDate(TRIP_DATE)
        );
    }

    @Override
    public List<Review> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<Review> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareReviewFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final Review review) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                TABLE, ID, USER_ID, BUS_ID, OPERATOR_ID, RATING, REVIEW_TEXT, REVIEW_DATE, TRIP_DATE);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, review.getReviewId());
            pst.setInt(2, review.getUserId());
            pst.setInt(3, review.getBusId());
            pst.setInt(4, review.getOperatorId());
            pst.setInt(5, review.getRating());
            pst.setString(6, review.getReviewText());
            pst.setTimestamp(7, review.getReviewDate());
            pst.setDate(8, new java.sql.Date(review.getTripDate().getTime()));
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final Review review) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, USER_ID, BUS_ID, OPERATOR_ID, RATING, REVIEW_TEXT, REVIEW_DATE, TRIP_DATE, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, review.getUserId());
            pst.setInt(2, review.getBusId());
            pst.setInt(3, review.getOperatorId());
            pst.setInt(4, review.getRating());
            pst.setString(5, review.getReviewText());
            pst.setTimestamp(6, review.getReviewDate());
            pst.setDate(7, new java.sql.Date(review.getTripDate().getTime()));
            pst.setInt(8, review.getReviewId());
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
