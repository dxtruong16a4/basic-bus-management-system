package controller;

import java.util.List;
import java.util.Map;

import dao.RouteDAO;
import dao.DAO;
import model.Route;
import utility.DbConnect;

public class RouteController {
    private DbConnect dbConnect = null;
    private RouteDAO routeDAO = null;
    private static RouteController instance = null;
    
    public static RouteController getInstance() {
        if (instance == null) {
            instance = new RouteController();
        }
        return instance;
    }
    
    private RouteController() {
        dbConnect = DbConnect.getInstance();
        routeDAO = new RouteDAO(dbConnect);
    }

    private Route mapToRoute(Map<String, Object> data) {
        java.math.BigDecimal distance = null;
        Object distanceObj = data.get("distance");
        if (distanceObj instanceof java.math.BigDecimal) {
            distance = (java.math.BigDecimal) distanceObj;
        } else if (distanceObj instanceof Number) {
            distance = java.math.BigDecimal.valueOf(((Number) distanceObj).doubleValue());
        } else if (distanceObj != null) {
            try { distance = new java.math.BigDecimal(distanceObj.toString()); } catch (Exception ex) { distance = null; }
        }
        return new Route(
            (int) data.get("route_id"),
            (String) data.get("origin_city"),
            (String) data.get("destination_city"),
            distance,
            (int) data.get("estimated_duration")
        );
    }

    public boolean addRoute(Map<String, Object> data) {
        try {
            Route route = mapToRoute(data);
            return routeDAO.insert(route);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRoute(Map<String, Object> data) {
        try {
            Route route = mapToRoute(data);
            return routeDAO.update(route);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRoute(Map<String, Object> data) {
        try {
            int routeId = (int) data.get("route_id");
            return routeDAO.delete(routeId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Route> getAllRoutes() {
        try {
            return routeDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Route> getRoutesBy(String whereClause, String[] params) {
        try {
            return routeDAO.select(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(routeDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public DbConnect getDbConnect() {
        return dbConnect;
    }
}
