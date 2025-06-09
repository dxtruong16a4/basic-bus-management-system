package dao;

import model.BusOperator;
import utility.db.DbConnect;
import utility.db.DbConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IBusOperatorDAO;

public class BusOperatorDAO implements IBusOperatorDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.OPERATOR_TABLE;
    private static final String ID = DbConstants.OPERATOR_ID;
    private static final String NAME = DbConstants.OPERATOR_NAME;
    private static final String CONTACT_PERSON = DbConstants.CONTACT_PERSON;
    private static final String CONTACT_EMAIL = DbConstants.CONTACT_EMAIL;
    private static final String CONTACT_PHONE = DbConstants.CONTACT_PHONE;
    private static final String ADDRESS = DbConstants.ADDRESS;
    private static final String RATING = DbConstants.RATING;
    private static final String JOINED_DATE = DbConstants.JOINED_DATE;

    public BusOperatorDAO(final DbConnect dbConnect) {
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

    private BusOperator prepareBusOperatorFromResultSet(ResultSet rs) throws SQLException {
        return new BusOperator(
            rs.getInt(ID),
            rs.getString(NAME),
            rs.getString(CONTACT_PERSON),
            rs.getString(CONTACT_EMAIL),
            rs.getString(CONTACT_PHONE),
            rs.getString(ADDRESS),
            rs.getBigDecimal(RATING),
            rs.getDate(JOINED_DATE)
        );
    }

    @Override
    public List<BusOperator> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<BusOperator> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareBusOperatorFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final BusOperator busOperator) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                TABLE, ID, NAME, CONTACT_PERSON, CONTACT_EMAIL, CONTACT_PHONE, ADDRESS, RATING, JOINED_DATE);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, busOperator.getOperatorId());
            pst.setString(2, busOperator.getOperatorName());
            pst.setString(3, busOperator.getContactPerson());
            pst.setString(4, busOperator.getContactEmail());
            pst.setString(5, busOperator.getContactPhone());
            pst.setString(6, busOperator.getAddress());
            pst.setBigDecimal(7, busOperator.getRating());
            pst.setDate(8, busOperator.getJoinedDate());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final BusOperator busOperator) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, NAME, CONTACT_PERSON, CONTACT_EMAIL, CONTACT_PHONE, ADDRESS, RATING, JOINED_DATE, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, busOperator.getOperatorName());
            pst.setString(2, busOperator.getContactPerson());
            pst.setString(3, busOperator.getContactEmail());
            pst.setString(4, busOperator.getContactPhone());
            pst.setString(5, busOperator.getAddress());
            pst.setBigDecimal(6, busOperator.getRating());
            pst.setDate(7, busOperator.getJoinedDate());
            pst.setInt(8, busOperator.getOperatorId());
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
