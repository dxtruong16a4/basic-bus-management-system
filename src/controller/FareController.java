package controller;

import java.util.List;
import java.util.Map;

import dao.FareDAO;
import dao.DAO;
import model.Fare;
import utility.db.DbConnect;

public class FareController {
    private DbConnect dbConnect = null;
    private FareDAO fareDAO = null;
    private static FareController instance = null;
    
    public static FareController getInstance() {
        if (instance == null) {
            instance = new FareController();
        }
        return instance;
    }
    
    private FareController() {
        dbConnect = DbConnect.getInstance();
        fareDAO = new FareDAO(dbConnect);
    }

    private Fare mapToFare(Map<String, Object> data) {
        java.math.BigDecimal baseFare = null, taxes = null, serviceCharge = null;
        Object baseFareObj = data.get("base_fare");
        if (baseFareObj instanceof java.math.BigDecimal) {
            baseFare = (java.math.BigDecimal) baseFareObj;
        } else if (baseFareObj instanceof Number) {
            baseFare = java.math.BigDecimal.valueOf(((Number) baseFareObj).doubleValue());
        } else if (baseFareObj != null) {
            try { baseFare = new java.math.BigDecimal(baseFareObj.toString()); } catch (Exception ex) { baseFare = null; }
        }
        Object taxesObj = data.get("taxes");
        if (taxesObj instanceof java.math.BigDecimal) {
            taxes = (java.math.BigDecimal) taxesObj;
        } else if (taxesObj instanceof Number) {
            taxes = java.math.BigDecimal.valueOf(((Number) taxesObj).doubleValue());
        } else if (taxesObj != null) {
            try { taxes = new java.math.BigDecimal(taxesObj.toString()); } catch (Exception ex) { taxes = null; }
        }
        Object serviceChargeObj = data.get("service_charge");
        if (serviceChargeObj instanceof java.math.BigDecimal) {
            serviceCharge = (java.math.BigDecimal) serviceChargeObj;
        } else if (serviceChargeObj instanceof Number) {
            serviceCharge = java.math.BigDecimal.valueOf(((Number) serviceChargeObj).doubleValue());
        } else if (serviceChargeObj != null) {
            try { serviceCharge = new java.math.BigDecimal(serviceChargeObj.toString()); } catch (Exception ex) { serviceCharge = null; }
        }
        java.sql.Timestamp lastUpdated = null;
        Object lastUpdatedObj = data.get("last_updated");
        if (lastUpdatedObj instanceof java.sql.Timestamp) {
            lastUpdated = (java.sql.Timestamp) lastUpdatedObj;
        } else if (lastUpdatedObj instanceof java.util.Date) {
            lastUpdated = new java.sql.Timestamp(((java.util.Date) lastUpdatedObj).getTime());
        } else if (lastUpdatedObj != null) {
            try { lastUpdated = java.sql.Timestamp.valueOf(lastUpdatedObj.toString()); } catch (Exception ex) { lastUpdated = null; }
        }
        return new Fare(
            (int) data.get("fare_id"),
            (int) data.get("bus_id"),
            (int) data.get("route_id"),
            baseFare,
            taxes,
            serviceCharge,
            lastUpdated
        );
    }

    public boolean addFare(Map<String, Object> data) {
        try {
            Fare fare = mapToFare(data);
            return fareDAO.insert(fare);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFare(Map<String, Object> data) {
        try {
            Fare fare = mapToFare(data);
            return fareDAO.update(fare);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFare(Map<String, Object> data) {
        try {
            int fareId = (int) data.get("fare_id");
            return fareDAO.delete(fareId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Fare> getAllFares() {
        try {
            return fareDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Fare> getFaresBy(String whereClause, String[] params) {
        try {
            return fareDAO.select(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(fareDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public DbConnect getDbConnect() {
        return dbConnect;
    }
}
