package utility.tableloader;

import java.util.*;
import model.BusOperator;
import utility.DbConstants;
import utility.TableHelper;
import utility.AppTranslator;
import controller.BusOperatorController;
import javax.swing.JTable;

public class BusOperatorTableLoader {
    private final BusOperatorController busOperatorController;
    private final AppTranslator translator;

    public BusOperatorTableLoader(BusOperatorController busOperatorController, AppTranslator translator) {
        this.busOperatorController = busOperatorController;
        this.translator = translator;
    }

    public void load(JTable tbDetail) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.OPERATOR_ID, translator.translate("bus_operator.header.operator_id"));
        headerMap.put(DbConstants.OPERATOR_NAME, translator.translate("bus_operator.header.operator_name"));
        headerMap.put(DbConstants.CONTACT_PERSON, translator.translate("bus_operator.header.contact_person"));
        headerMap.put(DbConstants.CONTACT_EMAIL, translator.translate("bus_operator.header.contact_email"));
        headerMap.put(DbConstants.CONTACT_PHONE, translator.translate("bus_operator.header.contact_phone"));
        headerMap.put(DbConstants.ADDRESS, translator.translate("bus_operator.header.address"));
        headerMap.put(DbConstants.RATING, translator.translate("bus_operator.header.rating"));
        headerMap.put(DbConstants.JOINED_DATE, translator.translate("bus_operator.header.joined_date"));

        List<BusOperator> busOperatorList = busOperatorController.getAllBusOperators();

        List<Map<String, Object>> busOperatorData = new ArrayList<>();
        for (BusOperator busOperator : busOperatorList) {
            Map<String, Object> busOperatorMap = new HashMap<>();
            busOperatorMap.put(DbConstants.OPERATOR_ID, busOperator.getOperatorId());
            busOperatorMap.put(DbConstants.OPERATOR_NAME, busOperator.getOperatorName());
            busOperatorMap.put(DbConstants.CONTACT_PERSON, busOperator.getContactPerson());
            busOperatorMap.put(DbConstants.CONTACT_EMAIL, busOperator.getContactEmail());
            busOperatorMap.put(DbConstants.CONTACT_PHONE, busOperator.getContactPhone());
            busOperatorMap.put(DbConstants.ADDRESS, busOperator.getAddress());
            busOperatorMap.put(DbConstants.RATING, busOperator.getRating());
            busOperatorMap.put(DbConstants.JOINED_DATE, busOperator.getJoinedDate());
            busOperatorData.add(busOperatorMap);
        }

        TableHelper.loadTable(tbDetail, busOperatorData, headerMap);
    }
}
