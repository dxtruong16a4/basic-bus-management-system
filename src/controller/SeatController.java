package controller;

import java.util.List;
import java.util.Map;

import dao.SeatDAO;
import dao.DAO;
import model.Seat;
import utility.DbConnect;

public class SeatController {
    private DbConnect dbConnect = null;
    private SeatDAO seatDAO = null;
    private static SeatController instance = null;
    
    public static SeatController getInstance() {
        if (instance == null) {
            instance = new SeatController();
        }
        return instance;
    }
    
    private SeatController() {
        dbConnect = DbConnect.getInstance();
        seatDAO = new SeatDAO(dbConnect);
    }

    public List<Seat> getAllSeats() {
        try {
            return seatDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Seat getSeatBy(String whereClause, String[] params) {
        try {
            List<Seat> seats = seatDAO.select(whereClause, params);
            return seats.isEmpty() ? null : seats.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(seatDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public boolean addSeat(Map<String, Object> data) {
        try {
            Seat seat = mapToSeat(data);
            return seatDAO.insert(seat);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSeat(Map<String, Object> data) {
        try {
            Seat seat = mapToSeat(data);
            return seatDAO.update(seat);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSeat(Map<String, Object> data) {
        try {
            int seatId = (int) data.get("seat_id");
            return seatDAO.delete(seatId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Seat mapToSeat(Map<String, Object> data) {
        return new Seat(
            (int) data.get("seat_id"),
            (int) data.get("bus_id"),
            (String) data.get("seat_number"),
            data.get("is_active") instanceof Boolean ? (Boolean) data.get("is_active") : Boolean.parseBoolean(data.get("is_active").toString())
        );
    }

    public DbConnect getDbConnect() {
        return dbConnect;
    }
}
