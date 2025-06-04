package controller;

import java.util.List;
import java.util.Map;

import dao.BookingDAO;
import dao.DAO;
import model.Booking;
import utility.DbConnect;

public class BookingController {
    private DbConnect dbConnect = null;
    private BookingDAO bookingDAO = null;
    private static BookingController instance = null;
    
    public static BookingController getInstance() {
        if (instance == null) {
            instance = new BookingController();
        }
        return instance;
    }
    
    private BookingController() {
        dbConnect = DbConnect.getInstance();
        bookingDAO = new BookingDAO(dbConnect);
    }

    public boolean addBooking(Map<String, Object> data) {
        try {
            Object bookingDateObj = data.get("booking_date");
            java.sql.Timestamp bookingTimestamp = null;
            if (bookingDateObj instanceof java.sql.Timestamp) {
                bookingTimestamp = (java.sql.Timestamp) bookingDateObj;
            } else if (bookingDateObj instanceof java.util.Date) {
                bookingTimestamp = new java.sql.Timestamp(((java.util.Date) bookingDateObj).getTime());
            } else if (bookingDateObj instanceof String) {
                try {
                    bookingTimestamp = java.sql.Timestamp.valueOf((String) bookingDateObj);
                } catch (Exception ex) {
                    bookingTimestamp = null;
                }
            }
            Object totalFareObj = data.get("total_fare");
            double totalFare = 0.0;
            if (totalFareObj instanceof Number) {
                totalFare = ((Number) totalFareObj).doubleValue();
            } else if (totalFareObj != null) {
                try {
                    totalFare = Double.parseDouble(totalFareObj.toString());
                } catch (Exception ex) {
                    totalFare = 0.0;
                }
            }
            Booking booking = new Booking(
                (int) data.get("booking_id"),
                (int) data.get("user_id"),
                (int) data.get("schedule_id"),
                bookingTimestamp,
                totalFare,
                (String) data.get("booking_status"),
                (String) data.get("payment_status"),
                (int) data.get("number_of_seats"),
                (java.util.Date) data.get("trip_date")
            );
            return bookingDAO.insert(booking);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBooking(Map<String, Object> data) {
        try {
            Object bookingDateObj = data.get("booking_date");
            java.sql.Timestamp bookingTimestamp = null;
            if (bookingDateObj instanceof java.sql.Timestamp) {
                bookingTimestamp = (java.sql.Timestamp) bookingDateObj;
            } else if (bookingDateObj instanceof java.util.Date) {
                bookingTimestamp = new java.sql.Timestamp(((java.util.Date) bookingDateObj).getTime());
            } else if (bookingDateObj instanceof String) {
                try {
                    bookingTimestamp = java.sql.Timestamp.valueOf((String) bookingDateObj);
                } catch (Exception ex) {
                    bookingTimestamp = null;
                }
            }
            Object totalFareObj = data.get("total_fare");
            double totalFare = 0.0;
            if (totalFareObj instanceof Number) {
                totalFare = ((Number) totalFareObj).doubleValue();
            } else if (totalFareObj != null) {
                try {
                    totalFare = Double.parseDouble(totalFareObj.toString());
                } catch (Exception ex) {
                    totalFare = 0.0;
                }
            }
            Booking booking = new Booking(
                (int) data.get("booking_id"),
                (int) data.get("user_id"),
                (int) data.get("schedule_id"),
                bookingTimestamp,
                totalFare,
                (String) data.get("booking_status"),
                (String) data.get("payment_status"),
                (int) data.get("number_of_seats"),
                (java.util.Date) data.get("trip_date")
            );
            return bookingDAO.update(booking);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(Map<String, Object> data) {
        try {
            int bookingId = (int) data.get("booking_id");
            return bookingDAO.delete(bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> getAllBookings() {
        try {
            return bookingDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Booking getBookingBy(String whereClause, String[] params) {
        try {
            List<Booking> bookings = bookingDAO.select(whereClause, params);
            return bookings.isEmpty() ? null : bookings.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(bookingDAO.getTableName());
            
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public DbConnect getDbConnect() {
        return dbConnect;
    }
}
