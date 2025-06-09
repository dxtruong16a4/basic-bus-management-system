package controller;

import java.util.List;
import java.util.Map;

import dao.ScheduleDAO;
import dao.DAO;
import model.Schedule;
import utility.DbConnect;

public class ScheduleController {
    private DbConnect dbConnect = null;
    private ScheduleDAO scheduleDAO = null;
    private static ScheduleController instance = null;
    
    public static ScheduleController getInstance() {
        if (instance == null) {
            instance = new ScheduleController();
        }
        return instance;
    }
    
    private ScheduleController() {
        dbConnect = DbConnect.getInstance();
        scheduleDAO = new ScheduleDAO(dbConnect);
    }

    private Schedule mapToSchedule(Map<String, Object> data) {
        java.sql.Timestamp departureTime = null, arrivalTime = null, createdDate = null;
        Object departureTimeObj = data.get("departure_time");
        if (departureTimeObj instanceof java.sql.Timestamp) {
            departureTime = (java.sql.Timestamp) departureTimeObj;
        } else if (departureTimeObj instanceof java.util.Date) {
            departureTime = new java.sql.Timestamp(((java.util.Date) departureTimeObj).getTime());
        } else if (departureTimeObj != null) {
            try { departureTime = java.sql.Timestamp.valueOf(departureTimeObj.toString()); } catch (Exception ex) { departureTime = null; }
        }
        Object arrivalTimeObj = data.get("arrival_time");
        if (arrivalTimeObj instanceof java.sql.Timestamp) {
            arrivalTime = (java.sql.Timestamp) arrivalTimeObj;
        } else if (arrivalTimeObj instanceof java.util.Date) {
            arrivalTime = new java.sql.Timestamp(((java.util.Date) arrivalTimeObj).getTime());
        } else if (arrivalTimeObj != null) {
            try { arrivalTime = java.sql.Timestamp.valueOf(arrivalTimeObj.toString()); } catch (Exception ex) { arrivalTime = null; }
        }
        Object createdDateObj = data.get("created_date");
        if (createdDateObj instanceof java.sql.Timestamp) {
            createdDate = (java.sql.Timestamp) createdDateObj;
        } else if (createdDateObj instanceof java.util.Date) {
            createdDate = new java.sql.Timestamp(((java.util.Date) createdDateObj).getTime());
        } else if (createdDateObj != null) {
            try { createdDate = java.sql.Timestamp.valueOf(createdDateObj.toString()); } catch (Exception ex) { createdDate = null; }
        }
        return new Schedule(
            (int) data.get("schedule_id"),
            (int) data.get("bus_id"),
            (int) data.get("route_id"),
            departureTime,
            arrivalTime,
            (String) data.get("frequency"),
            data.get("is_active") instanceof Boolean ? (Boolean) data.get("is_active") : Boolean.parseBoolean(data.get("is_active").toString()),
            createdDate
        );
    }

    public boolean addSchedule(Map<String, Object> data) {
        try {
            Schedule schedule = mapToSchedule(data);
            return scheduleDAO.insert(schedule);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSchedule(Map<String, Object> data) {
        try {
            Schedule schedule = mapToSchedule(data);
            return scheduleDAO.update(schedule);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSchedule(Map<String, Object> data) {
        try {
            int scheduleId = (int) data.get("schedule_id");
            return scheduleDAO.delete(scheduleId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Schedule> getAllSchedules() {
        try {
            return scheduleDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Schedule> getSchedulesBy(String whereClause, String[] params) {
        try {
            return scheduleDAO.select(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(scheduleDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public DbConnect getDbConnect() {
        return dbConnect;
    }
}
