package utility.tableloader;

import java.util.*;
import model.Schedule;
import utility.app.AppTranslator;
import utility.db.DbConstants;
import utility.db.TableHelper;
import controller.ScheduleController;
import javax.swing.JTable;

public class ScheduleTableLoader {
    private final ScheduleController scheduleController;
    private final AppTranslator translator;

    public ScheduleTableLoader(ScheduleController scheduleController, AppTranslator translator) {
        this.scheduleController = scheduleController;
        this.translator = translator;
    }

    public void load(JTable tbDetail, int offset, int limit) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.SCHEDULE_ID, translator.translate("schedule.header.schedule_id"));
        headerMap.put(DbConstants.BUS_ID, translator.translate("schedule.header.bus_id"));
        headerMap.put(DbConstants.ROUTE_ID, translator.translate("schedule.header.route_id"));
        headerMap.put(DbConstants.DEPARTURE_TIME, translator.translate("schedule.header.departure_time"));
        headerMap.put(DbConstants.ARRIVAL_TIME, translator.translate("schedule.header.arrival_time"));
        headerMap.put(DbConstants.FREQUENCY, translator.translate("schedule.header.frequency"));
        headerMap.put(DbConstants.SCHEDULE_IS_ACTIVE, translator.translate("schedule.header.is_active"));
        headerMap.put(DbConstants.CREATED_DATE, translator.translate("schedule.header.created_date"));

        List<Schedule> scheduleList = scheduleController.getAllSchedules();
        int toIndex = Math.min(offset + limit, scheduleList.size());
        List<Schedule> pageList = scheduleList.subList(offset, toIndex);

        List<Map<String, Object>> scheduleData = new ArrayList<>();
        for (Schedule schedule : pageList) {
            Map<String, Object> scheduleMap = new HashMap<>();
            scheduleMap.put(DbConstants.SCHEDULE_ID, schedule.getScheduleId());
            scheduleMap.put(DbConstants.BUS_ID, schedule.getBusId());
            scheduleMap.put(DbConstants.ROUTE_ID, schedule.getRouteId());
            scheduleMap.put(DbConstants.DEPARTURE_TIME, schedule.getDepartureTime());
            scheduleMap.put(DbConstants.ARRIVAL_TIME, schedule.getArrivalTime());
            scheduleMap.put(DbConstants.FREQUENCY, schedule.getFrequency());
            scheduleMap.put(DbConstants.SCHEDULE_IS_ACTIVE, schedule.isActive());
            scheduleMap.put(DbConstants.CREATED_DATE, schedule.getCreatedDate());
            scheduleData.add(scheduleMap);
        }

        TableHelper.loadTable(tbDetail, scheduleData, headerMap);
    }

    public void loadBy(JTable tbDetail, String searchBy, String searchValue, int offset, int limit) {
        Map<String, String> columnMap = Map.of(
            translator.translate("schedule.header.schedule_id"), DbConstants.SCHEDULE_ID,
            translator.translate("schedule.header.bus_id"), DbConstants.BUS_ID,
            translator.translate("schedule.header.route_id"), DbConstants.ROUTE_ID,
            translator.translate("schedule.header.departure_time"), DbConstants.DEPARTURE_TIME,
            translator.translate("schedule.header.arrival_time"), DbConstants.ARRIVAL_TIME,
            translator.translate("schedule.header.frequency"), DbConstants.FREQUENCY,
            translator.translate("schedule.header.is_active"), DbConstants.SCHEDULE_IS_ACTIVE,
            translator.translate("schedule.header.created_date"), DbConstants.CREATED_DATE
        );
        String realColumn = columnMap.getOrDefault(searchBy, searchBy);

        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.SCHEDULE_ID, translator.translate("schedule.header.schedule_id"));
        headerMap.put(DbConstants.BUS_ID, translator.translate("schedule.header.bus_id"));
        headerMap.put(DbConstants.ROUTE_ID, translator.translate("schedule.header.route_id"));
        headerMap.put(DbConstants.DEPARTURE_TIME, translator.translate("schedule.header.departure_time"));
        headerMap.put(DbConstants.ARRIVAL_TIME, translator.translate("schedule.header.arrival_time"));
        headerMap.put(DbConstants.FREQUENCY, translator.translate("schedule.header.frequency"));
        headerMap.put(DbConstants.SCHEDULE_IS_ACTIVE, translator.translate("schedule.header.is_active"));
        headerMap.put(DbConstants.CREATED_DATE, translator.translate("schedule.header.created_date"));

        String whereClause = realColumn + " LIKE ?";
        String[] params = new String[] { "%" + searchValue + "%" };

        List<Schedule> scheduleList = new ArrayList<>();
        try {
            scheduleList = scheduleController.getSchedulesBy(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int toIndex = Math.min(offset + limit, scheduleList.size());
        List<Schedule> pageList = scheduleList.subList(offset, toIndex);

        List<Map<String, Object>> scheduleData = new ArrayList<>();
        for (Schedule schedule : pageList) {
            Map<String, Object> scheduleMap = new HashMap<>();
            scheduleMap.put(DbConstants.SCHEDULE_ID, schedule.getScheduleId());
            scheduleMap.put(DbConstants.BUS_ID, schedule.getBusId());
            scheduleMap.put(DbConstants.ROUTE_ID, schedule.getRouteId());
            scheduleMap.put(DbConstants.DEPARTURE_TIME, schedule.getDepartureTime());
            scheduleMap.put(DbConstants.ARRIVAL_TIME, schedule.getArrivalTime());
            scheduleMap.put(DbConstants.FREQUENCY, schedule.getFrequency());
            scheduleMap.put(DbConstants.SCHEDULE_IS_ACTIVE, schedule.isActive());
            scheduleMap.put(DbConstants.CREATED_DATE, schedule.getCreatedDate());
            scheduleData.add(scheduleMap);
        }

        TableHelper.loadTable(tbDetail, scheduleData, headerMap);
    }
}
