package utility.tableloader;

import java.util.*;
import model.Schedule;
import utility.DbConstants;
import utility.TableHelper;
import utility.AppTranslator;
import controller.ScheduleController;
import javax.swing.JTable;

public class ScheduleTableLoader {
    private final ScheduleController scheduleController;
    private final AppTranslator translator;

    public ScheduleTableLoader(ScheduleController scheduleController, AppTranslator translator) {
        this.scheduleController = scheduleController;
        this.translator = translator;
    }

    public void load(JTable tbDetail) {
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

        List<Map<String, Object>> scheduleData = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
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
