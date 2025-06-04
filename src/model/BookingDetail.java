package model;


public class BookingDetail {
    private int bookingDetailId;
    private int bookingId;
    private int seatId;
    private String passengerName;
    private int passengerAge;
    private String passengerGender;
    private double fare;
    private String seatNumber;

    public BookingDetail() {}

    public BookingDetail(int bookingDetailId, int bookingId, int seatId, String passengerName, int passengerAge, String passengerGender, double fare, String seatNumber) {
        this.bookingDetailId = bookingDetailId;
        this.bookingId = bookingId;
        this.seatId = seatId;
        this.passengerName = passengerName;
        this.passengerAge = passengerAge;
        this.passengerGender = passengerGender;
        this.fare = fare;
        this.seatNumber = seatNumber;
    }

    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }   

    public int getPassengerAge() {
        return passengerAge;
    }

    public void setPassengerAge(int passengerAge) {
        this.passengerAge = passengerAge;
    }

    public String getPassengerGender() {
        return passengerGender;
    }

    public void setPassengerGender(String passengerGender) {
        this.passengerGender = passengerGender;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}