package controller;

import java.util.List;
import java.util.Map;

import dao.BookingDetailDAO;
import dao.DAO;
import model.BookingDetail;
import utility.DbConnect;

public class BookingDetailController {
    private DbConnect dbConnect = null;
    private BookingDetailDAO bookingDetailDAO = null;
    private static BookingDetailController instance = null;
    
    public static BookingDetailController getInstance() {
        if (instance == null) {
            instance = new BookingDetailController();
        }
        return instance;
    }
    
    private BookingDetailController() {
        dbConnect = new DbConnect();
        bookingDetailDAO = new BookingDetailDAO(dbConnect);
    }

    public List<BookingDetail> getAllBookingDetails() {
        try {
            return bookingDetailDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<BookingDetail> getBookingDetailsByBookingId(int bookingId) {
        try {
            return bookingDetailDAO.select("booking_id = ?", new String[]{String.valueOf(bookingId)});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public BookingDetail getBookingDetailBy(String whereClause, String[] params) {
        try {
            List<BookingDetail> details = bookingDetailDAO.select(whereClause, params);
            return details.isEmpty() ? null : details.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(bookingDetailDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public boolean addBookingDetail(Map<String, Object> data) {
        try {
            BookingDetail bookingDetail = mapToBookingDetail(data);
            return bookingDetailDAO.insert(bookingDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookingDetail(Map<String, Object> data) {
        try {
            BookingDetail bookingDetail = mapToBookingDetail(data);
            return bookingDetailDAO.update(bookingDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBookingDetail(Map<String, Object> data) {
        try {
            int bookingDetailId = (int) data.get("booking_detail_id");
            return bookingDetailDAO.delete(bookingDetailId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private BookingDetail mapToBookingDetail(Map<String, Object> data) {
        return new BookingDetail(
            (int) data.get("booking_detail_id"),
            (int) data.get("booking_id"),
            (int) data.get("seat_id"),
            (String) data.get("passenger_name"),
            (int) data.get("passenger_age"),
            (String) data.get("passenger_gender"),
            data.get("fare") instanceof Number ? ((Number) data.get("fare")).doubleValue() : Double.parseDouble(data.get("fare").toString()),
            (String) data.get("seat_number")
        );
    }
}
