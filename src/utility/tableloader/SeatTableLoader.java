package utility.tableloader;

import java.util.*;
import model.Seat;
import utility.DbConstants;
import utility.TableHelper;
import utility.AppTranslator;
import controller.SeatController;
import javax.swing.JTable;

public class SeatTableLoader {
    private final SeatController seatController;
    private final AppTranslator translator;

    public SeatTableLoader(SeatController seatController, AppTranslator translator) {
        this.seatController = seatController;
        this.translator = translator;
    }

    public void load(JTable tbDetail, int offset, int limit) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.SEAT_ID, translator.translate("seat.header.seat_id"));
        headerMap.put(DbConstants.BUS_ID, translator.translate("seat.header.bus_id"));
        headerMap.put(DbConstants.SEAT_NUMBER, translator.translate("seat.header.seat_number"));
        headerMap.put(DbConstants.SEAT_IS_ACTIVE, translator.translate("seat.header.is_active"));

        List<Seat> seatList = seatController.getAllSeats();
        int toIndex = Math.min(offset + limit, seatList.size());
        List<Seat> pageList = seatList.subList(offset, toIndex);

        List<Map<String, Object>> seatData = new ArrayList<>();
        for (Seat seat : pageList) {
            Map<String, Object> seatMap = new HashMap<>();
            seatMap.put(DbConstants.SEAT_ID, seat.getSeatId());
            seatMap.put(DbConstants.BUS_ID, seat.getBusId());
            seatMap.put(DbConstants.SEAT_NUMBER, seat.getSeatNumber());
            seatMap.put(DbConstants.SEAT_IS_ACTIVE, seat.isActive());
            seatData.add(seatMap);
        }

        TableHelper.loadTable(tbDetail, seatData, headerMap);
    }

    public void loadBy(JTable tbDetail, String searchBy, String searchValue, int offset, int limit) {
        Map<String, String> columnMap = Map.of(
            translator.translate("seat.header.seat_id"), DbConstants.SEAT_ID,
            translator.translate("seat.header.bus_id"), DbConstants.BUS_ID,
            translator.translate("seat.header.seat_number"), DbConstants.SEAT_NUMBER,
            translator.translate("seat.header.is_active"), DbConstants.SEAT_IS_ACTIVE
        );
        String realColumn = columnMap.getOrDefault(searchBy, searchBy);

        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.SEAT_ID, translator.translate("seat.header.seat_id"));
        headerMap.put(DbConstants.BUS_ID, translator.translate("seat.header.bus_id"));
        headerMap.put(DbConstants.SEAT_NUMBER, translator.translate("seat.header.seat_number"));
        headerMap.put(DbConstants.SEAT_IS_ACTIVE, translator.translate("seat.header.is_active"));

        String whereClause = realColumn + " LIKE ?";
        String[] params = new String[] { "%" + searchValue + "%" };

        List<Seat> seatList = new ArrayList<>();
        try {
            seatList = seatController.getSeatsBy(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int toIndex = Math.min(offset + limit, seatList.size());
        List<Seat> pageList = seatList.subList(offset, toIndex);

        List<Map<String, Object>> seatData = new ArrayList<>();
        for (Seat seat : pageList) {
            Map<String, Object> seatMap = new HashMap<>();
            seatMap.put(DbConstants.SEAT_ID, seat.getSeatId());
            seatMap.put(DbConstants.BUS_ID, seat.getBusId());
            seatMap.put(DbConstants.SEAT_NUMBER, seat.getSeatNumber());
            seatMap.put(DbConstants.SEAT_IS_ACTIVE, seat.isActive());
            seatData.add(seatMap);
        }

        TableHelper.loadTable(tbDetail, seatData, headerMap);
    }
}
