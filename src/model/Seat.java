package model;

public class Seat {
    private int seatId;
    private int busId;
    private String seatNumber;
    private boolean isActive;

    public Seat() {}

    public Seat(int seatId, int busId, String seatNumber, boolean isActive) {
        this.seatId = seatId;
        this.busId = busId;
        this.seatNumber = seatNumber;
        this.isActive = isActive;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getBusId() {
        return busId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isActive() {
        return isActive;
    }
}
