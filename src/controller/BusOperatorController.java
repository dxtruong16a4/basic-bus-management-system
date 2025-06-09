package controller;

import java.util.List;
import java.util.Map;

import dao.BusOperatorDAO;
import dao.DAO;
import model.BusOperator;
import utility.db.DbConnect;

public class BusOperatorController {
    private DbConnect dbConnect = null;
    private BusOperatorDAO busOperatorDAO = null;
    private static BusOperatorController instance = null;
    
    public static BusOperatorController getInstance() {
        if (instance == null) {
            instance = new BusOperatorController();
        }
        return instance;
    }
    
    private BusOperatorController() {
        dbConnect = DbConnect.getInstance();
        busOperatorDAO = new BusOperatorDAO(dbConnect);
    }

    private BusOperator mapToBusOperator(Map<String, Object> data) {
        java.sql.Date joinedDate = null;
        Object joinedDateObj = data.get("joined_date");
        if (joinedDateObj instanceof java.sql.Date) {
            joinedDate = (java.sql.Date) joinedDateObj;
        } else if (joinedDateObj instanceof java.util.Date) {
            joinedDate = new java.sql.Date(((java.util.Date) joinedDateObj).getTime());
        } else if (joinedDateObj != null) {
            try {
                joinedDate = java.sql.Date.valueOf(joinedDateObj.toString());
            } catch (Exception ex) {
                joinedDate = null;
            }
        }
        java.math.BigDecimal rating = null;
        Object ratingObj = data.get("rating");
        if (ratingObj instanceof java.math.BigDecimal) {
            rating = (java.math.BigDecimal) ratingObj;
        } else if (ratingObj instanceof Number) {
            rating = java.math.BigDecimal.valueOf(((Number) ratingObj).doubleValue());
        } else if (ratingObj != null) {
            try {
                rating = new java.math.BigDecimal(ratingObj.toString());
            } catch (Exception ex) {
                rating = null;
            }
        }
        return new BusOperator(
            (int) data.get("operator_id"),
            (String) data.get("operator_name"),
            (String) data.get("contact_person"),
            (String) data.get("contact_email"),
            (String) data.get("contact_phone"),
            (String) data.get("address"),
            rating,
            joinedDate
        );
    }

    public boolean addBusOperator(Map<String, Object> data) {
        try {
            BusOperator operator = mapToBusOperator(data);
            return busOperatorDAO.insert(operator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBusOperator(Map<String, Object> data) {
        try {
            BusOperator operator = mapToBusOperator(data);
            return busOperatorDAO.update(operator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBusOperator(Map<String, Object> data) {
        try {
            int operatorId = (int) data.get("operator_id");
            return busOperatorDAO.delete(operatorId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<BusOperator> getAllBusOperators() {
        try {
            return busOperatorDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<BusOperator> getBusOperatorBy(String whereClause, String[] params) {
        try {
            return busOperatorDAO.select(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(busOperatorDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public DbConnect getDbConnect() {
        return dbConnect;
    }
}
