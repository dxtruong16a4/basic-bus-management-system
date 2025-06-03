package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Fare {
    private int fareId;
    private int busId;
    private int routeId;
    private BigDecimal baseFare;
    private BigDecimal taxes;
    private BigDecimal serviceCharge;
    private Timestamp lastUpdated;

    public Fare() {}

    public Fare(int fareId, int busId, int routeId, BigDecimal baseFare, BigDecimal taxes, BigDecimal serviceCharge, Timestamp lastUpdated) {
        this.fareId = fareId;
        this.busId = busId;
        this.routeId = routeId;
        this.baseFare = baseFare;
        this.taxes = taxes;
        this.serviceCharge = serviceCharge;
        this.lastUpdated = lastUpdated;
    }

    public int getFareId() {
        return fareId;
    }

    public int getBusId() {
        return busId;
    }

    public int getRouteId() {
        return routeId;
    }

    public BigDecimal getBaseFare() {
        return baseFare;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }
}