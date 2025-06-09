package utility.tableloader;

import java.util.*;
import model.Bus;
import utility.app.AppTranslator;
import utility.db.DbConstants;
import utility.db.TableHelper;
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

    public void loadBy(JTable tbDetail, String searchBy, String searchValue, int offset, int limit) {
        Map<String, String> columnMap = Map.of(
            translator.translate("bus.header.bus_id"), DbConstants.BUS_ID,
            translator.translate("bus.header.bus_number"), DbConstants.BUS_NUMBER,
            translator.translate("bus.header.bus_name"), DbConstants.BUS_NAME,
            translator.translate("bus.header.bus_type"), DbConstants.BUS_TYPE,
            translator.translate("bus.header.total_seats"), DbConstants.TOTAL_SEATS,
            translator.translate("bus.header.operator_id"), DbConstants.OPERATOR_ID,
            translator.translate("bus.header.registration_number"), DbConstants.REGISTRATION_NUMBER,
            translator.translate("bus.header.is_active"), DbConstants.BUS_IS_ACTIVE
        );
        String realColumn = columnMap.getOrDefault(searchBy, searchBy);

        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.BUS_ID, translator.translate("bus.header.bus_id"));
        headerMap.put(DbConstants.BUS_NUMBER, translator.translate("bus.header.bus_number"));
        headerMap.put(DbConstants.BUS_NAME, translator.translate("bus.header.bus_name"));
        headerMap.put(DbConstants.BUS_TYPE, translator.translate("bus.header.bus_type"));
        headerMap.put(DbConstants.TOTAL_SEATS, translator.translate("bus.header.total_seats"));
        headerMap.put(DbConstants.OPERATOR_ID, translator.translate("bus.header.operator_id"));
        headerMap.put(DbConstants.REGISTRATION_NUMBER, translator.translate("bus.header.registration_number"));
        headerMap.put(DbConstants.BUS_IS_ACTIVE, translator.translate("bus.header.is_active"));

        String whereClause = realColumn + " LIKE ?";
        String[] params = new String[] { "%" + searchValue + "%" };

        List<Bus> busList = new ArrayList<>();
        try {
            busList = busController.getBusesBy(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

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