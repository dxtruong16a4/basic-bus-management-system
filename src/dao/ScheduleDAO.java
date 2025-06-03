package dao;

import model.Schedule;
import utility.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.IDAO.IScheduleDAO;
import utility.DbConstants;

public class ScheduleDAO implements IScheduleDAO {
    private final DbConnect dbConnect;
    private Connection con;

    private static final String TABLE = DbConstants.SCHEDULE_TABLE;
    private static final String ID = DbConstants.SCHEDULE_ID;
    private static final String BUS_ID = DbConstants.BUS_ID;
    private static final String ROUTE_ID = DbConstants.ROUTE_ID;
    private static final String DEPARTURE_TIME = DbConstants.DEPARTURE_TIME;
    private static final String ARRIVAL_TIME = DbConstants.ARRIVAL_TIME;
    private static final String FREQUENCY = DbConstants.FREQUENCY;
    private static final String IS_ACTIVE = DbConstants.SCHEDULE_IS_ACTIVE;
    private static final String CREATED_DATE = DbConstants.CREATED_DATE;

    public ScheduleDAO(final DbConnect dbConnect) {
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

    private Schedule prepareScheduleFromResultSet(ResultSet rs) throws SQLException {
        return new Schedule(
            rs.getInt(ID),
            rs.getInt(BUS_ID),
            rs.getInt(ROUTE_ID),
            rs.getTimestamp(DEPARTURE_TIME),
            rs.getTimestamp(ARRIVAL_TIME),
            rs.getString(FREQUENCY),
            rs.getBoolean(IS_ACTIVE),
            rs.getTimestamp(CREATED_DATE)
        );
    }

    @Override
    public List<Schedule> select(final String whereClause, final String[] params) throws SQLException, ClassNotFoundException {
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
                List<Schedule> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(prepareScheduleFromResultSet(rs));
                }
                return result;
            }
        }
    }

    @Override
    public boolean insert(final Schedule schedule) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                TABLE, BUS_ID, ROUTE_ID, DEPARTURE_TIME, ARRIVAL_TIME, FREQUENCY, IS_ACTIVE);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, schedule.getBusId());
            pst.setInt(2, schedule.getRouteId());
            pst.setTimestamp(3, schedule.getDepartureTime());
            pst.setTimestamp(4, schedule.getArrivalTime());
            pst.setString(5, schedule.getFrequency());
            pst.setBoolean(6, schedule.isActive());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(final Schedule schedule) throws SQLException, ClassNotFoundException {
        if (!checkConnection()) return false;

        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE, BUS_ID, ROUTE_ID, DEPARTURE_TIME, ARRIVAL_TIME, FREQUENCY, IS_ACTIVE, ID);

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, schedule.getBusId());
            pst.setInt(2, schedule.getRouteId());
            pst.setTimestamp(3, schedule.getDepartureTime());
            pst.setTimestamp(4, schedule.getArrivalTime());
            pst.setString(5, schedule.getFrequency());
            pst.setBoolean(6, schedule.isActive());
            pst.setInt(7, schedule.getScheduleId());
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
