package controller;

import java.util.List;
import java.util.Map;

import dao.BusDAO;
import dao.DAO;
import model.Bus;
import utility.db.DbConnect;

public class BusController {
    private DbConnect dbConnect = null;
    private BusDAO busDAO = null;
    private static BusController instance = null;
    
    public static BusController getInstance() {
        if (instance == null) {
            instance = new BusController();
        }
        return instance;
    }
    
    private BusController() {
        dbConnect = DbConnect.getInstance();
        busDAO = new BusDAO(dbConnect);
    }

    private Bus mapToBus(Map<String, Object> data) {
        return new Bus(
            (int) data.get("bus_id"),
            (String) data.get("bus_number"),
            (String) data.get("bus_name"),
            (String) data.get("bus_type"),
            (int) data.get("total_seats"),
            (int) data.get("operator_id"),
            (String) data.get("registration_number"),
            data.get("is_active") instanceof Boolean ? (Boolean) data.get("is_active") : Boolean.parseBoolean(data.get("is_active").toString())
        );
    }

    public boolean addBus(Map<String, Object> data) {
        try {
            Bus bus = mapToBus(data);
            return busDAO.insert(bus);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBus(Map<String, Object> data) {
        try {
            Bus bus = mapToBus(data);
            return busDAO.update(bus);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBus(Map<String, Object> data) {
        try {
            int busId = (int) data.get("bus_id");
            return busDAO.delete(busId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Bus> getAllBuses() {
        try {
            return busDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Bus> getBusesBy(String whereClause, String[] params) {
        try {
            return busDAO.select(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(busDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public DbConnect getDbConnect() {
        return dbConnect;
    }
}