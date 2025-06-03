package controller;

import java.util.List;
import java.util.Map;

import dao.BusDAO;
import dao.DAO;
import model.Bus;
import utility.DbConnect;


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
        dbConnect = new DbConnect();
        busDAO = new BusDAO(dbConnect);
    }

    public List<Bus> getAllBuses() {
        try {
            return busDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Bus getBusBy(String whereClause, String[] params) {
        try {
            List<Bus> buses = busDAO.select(whereClause, params);
            return buses.isEmpty() ? null : buses.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
}