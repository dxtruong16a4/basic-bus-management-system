package model;

public class Bus {
    private int busId;
    private String busNumber;
    private String busName;
    private String busType;
    private int totalSeats;
    private int operatorId;
    private String registrationNumber;
    private boolean isActive;

    public Bus() {}

    public Bus(int busId, String busNumber, String busName, String busType, int totalSeats, int operatorId, String registrationNumber, boolean isActive) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busName = busName;
        this.busType = busType;
        this.totalSeats = totalSeats;
        this.operatorId = operatorId;
        this.registrationNumber = registrationNumber;
        this.isActive = isActive;
    }

    public int getBusId() {
        return busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public String getBusType() {
        return busType;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean isActive() {
        return isActive;
    }
}