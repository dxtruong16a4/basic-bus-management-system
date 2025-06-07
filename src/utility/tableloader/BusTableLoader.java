package utility.tableloader;

import java.util.*;
import model.Bus;
import utility.DbConstants;
import utility.TableHelper;
import utility.AppTranslator;
import controller.BusController;
import javax.swing.JTable;

public class BusTableLoader {
    private final BusController busController;
    private final AppTranslator translator;

    public BusTableLoader(BusController busController, AppTranslator translator) {
        this.busController = busController;
        this.translator = translator;
    }

    public void load(JTable tbDetail, int offset, int limit) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.BUS_ID, translator.translate("bus.header.bus_id"));
        headerMap.put(DbConstants.BUS_NUMBER, translator.translate("bus.header.bus_number"));
        headerMap.put(DbConstants.BUS_NAME, translator.translate("bus.header.bus_name"));
        headerMap.put(DbConstants.BUS_TYPE, translator.translate("bus.header.bus_type"));
        headerMap.put(DbConstants.TOTAL_SEATS, translator.translate("bus.header.total_seats"));
        headerMap.put(DbConstants.OPERATOR_ID, translator.translate("bus.header.operator_id"));
        headerMap.put(DbConstants.REGISTRATION_NUMBER, translator.translate("bus.header.registration_number"));
        headerMap.put(DbConstants.BUS_IS_ACTIVE, translator.translate("bus.header.is_active"));

        List<Bus> busList = busController.getAllBuses();
        int toIndex = Math.min(offset + limit, busList.size());
        List<Bus> pageList = busList.subList(offset, toIndex);

        List<Map<String, Object>> busData = new ArrayList<>();
        for (Bus bus : pageList) {
            Map<String, Object> busMap = new HashMap<>();
            busMap.put(DbConstants.BUS_ID, bus.getBusId());
            busMap.put(DbConstants.BUS_NUMBER, bus.getBusNumber());
            busMap.put(DbConstants.BUS_NAME, bus.getBusName());
            busMap.put(DbConstants.BUS_TYPE, bus.getBusType());
            busMap.put(DbConstants.TOTAL_SEATS, bus.getTotalSeats());
            busMap.put(DbConstants.OPERATOR_ID, bus.getOperatorId());
            busMap.put(DbConstants.REGISTRATION_NUMBER, bus.getRegistrationNumber());
            busMap.put(DbConstants.BUS_IS_ACTIVE, bus.isActive());
            busData.add(busMap);
        }

        TableHelper.loadTable(tbDetail, busData, headerMap);
    }
}