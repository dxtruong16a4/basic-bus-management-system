package utility.tableloader;

import java.util.*;
import model.Fare;
import utility.DbConstants;
import utility.TableHelper;
import utility.AppTranslator;
import controller.FareController;
import javax.swing.JTable;

public class FareTableLoader {
    private final FareController fareController;
    private final AppTranslator translator;

    public FareTableLoader(FareController fareController, AppTranslator translator) {
        this.fareController = fareController;
        this.translator = translator;
    }

    public void load(JTable tbDetail) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.FARE_ID, translator.translate("fare.header.fare_id"));
        headerMap.put(DbConstants.BUS_ID, translator.translate("fare.header.bus_id"));
        headerMap.put(DbConstants.ROUTE_ID, translator.translate("fare.header.route_id"));
        headerMap.put(DbConstants.BASE_FARE, translator.translate("fare.header.base_fare"));
        headerMap.put(DbConstants.TAXES, translator.translate("fare.header.taxes"));
        headerMap.put(DbConstants.SERVICE_CHARGE, translator.translate("fare.header.service_charge"));
        headerMap.put(DbConstants.LAST_UPDATED, translator.translate("fare.header.last_updated"));

        List<Fare> fareList = fareController.getAllFares();

        List<Map<String, Object>> fareData = new ArrayList<>();
        for (Fare fare : fareList) {
            Map<String, Object> fareMap = new HashMap<>();
            fareMap.put(DbConstants.FARE_ID, fare.getFareId());
            fareMap.put(DbConstants.BUS_ID, fare.getBusId());
            fareMap.put(DbConstants.ROUTE_ID, fare.getRouteId());
            fareMap.put(DbConstants.BASE_FARE, fare.getBaseFare());
            fareMap.put(DbConstants.TAXES, fare.getTaxes());
            fareMap.put(DbConstants.SERVICE_CHARGE, fare.getServiceCharge());
            fareMap.put(DbConstants.LAST_UPDATED, fare.getLastUpdated());
            fareData.add(fareMap);
        }

        TableHelper.loadTable(tbDetail, fareData, headerMap);
    }
}
