package model;

import java.sql.Timestamp;

public class Schedule {
    private int scheduleId;
    private int busId;
    private int routeId;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private String frequency;
    private boolean isActive;
    private Timestamp createdDate;

    public Schedule() {}

    public Schedule(int scheduleId, int busId, int routeId, Timestamp departureTime, Timestamp arrivalTime, String frequency, boolean isActive, Timestamp createdDate) {
        this.scheduleId = scheduleId;
        this.busId = busId;
        this.routeId = routeId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.frequency = frequency;
        this.isActive = isActive;
        this.createdDate = createdDate;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getBusId() {
        return busId;
    }

    public int getRouteId() {
        return routeId;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public boolean isActive() {
        return isActive;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}