package model;

import java.sql.Timestamp;
import java.util.Date;

public class Booking {
    private int bookingId;
    private int userId;
    private int scheduleId;
    private Timestamp bookingDate;
    private double totalFare;
    private String bookingStatus;
    private String paymentStatus;
    private int numberOfSeats;
    private Date tripDate;

    public Booking() {}

    public Booking(int bookingId, int userId, int scheduleId, Timestamp bookingDate, 
                  double totalFare, String bookingStatus, String paymentStatus, 
                  int numberOfSeats, Date tripDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.bookingDate = bookingDate;
        this.totalFare = totalFare;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.numberOfSeats = numberOfSeats;
        this.tripDate = tripDate;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public Date getTripDate() {
        return tripDate;
    }
}
