package model;

import java.math.BigDecimal;

public class Route {
    private int routeId;
    private String originCity;
    private String destinationCity;
    private BigDecimal distance;
    private int estimatedDuration;

    public Route() {}

    public Route(int routeId, String originCity, String destinationCity, BigDecimal distance, int estimatedDuration) {
        this.routeId = routeId;
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.distance = distance;
        this.estimatedDuration = estimatedDuration;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getOriginCity() {
        return originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }
}
