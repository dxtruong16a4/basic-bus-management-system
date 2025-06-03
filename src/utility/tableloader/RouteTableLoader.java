package utility.tableloader;

import java.util.*;
import model.Route;
import utility.DbConstants;
import utility.TableHelper;
import utility.AppTranslator;
import controller.RouteController;
import javax.swing.JTable;

public class RouteTableLoader {
    private final RouteController routeController;
    private final AppTranslator translator;

    public RouteTableLoader(RouteController routeController, AppTranslator translator) {
        this.routeController = routeController;
        this.translator = translator;
    }

    public void load(JTable tbDetail) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.ROUTE_ID, translator.translate("route.header.route_id"));
        headerMap.put(DbConstants.ORIGIN_CITY, translator.translate("route.header.origin_city"));
        headerMap.put(DbConstants.DESTINATION_CITY, translator.translate("route.header.destination_city"));
        headerMap.put(DbConstants.DISTANCE, translator.translate("route.header.distance"));
        headerMap.put(DbConstants.ESTIMATED_DURATION, translator.translate("route.header.estimated_duration"));

        List<Route> routeList = routeController.getAllRoutes();

        List<Map<String, Object>> routeData = new ArrayList<>();
        for (Route route : routeList) {
            Map<String, Object> routeMap = new HashMap<>();
            routeMap.put(DbConstants.ROUTE_ID, route.getRouteId());
            routeMap.put(DbConstants.ORIGIN_CITY, route.getOriginCity());
            routeMap.put(DbConstants.DESTINATION_CITY, route.getDestinationCity());
            routeMap.put(DbConstants.DISTANCE, route.getDistance());
            routeMap.put(DbConstants.ESTIMATED_DURATION, route.getEstimatedDuration());
            routeData.add(routeMap);
        }

        TableHelper.loadTable(tbDetail, routeData, headerMap);
    }
}
