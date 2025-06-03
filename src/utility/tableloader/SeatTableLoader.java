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

    public void load(JTable tbDetail) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.SEAT_ID, translator.translate("seat.header.seat_id"));
        headerMap.put(DbConstants.BUS_ID, translator.translate("seat.header.bus_id"));
        headerMap.put(DbConstants.SEAT_NUMBER, translator.translate("seat.header.seat_number"));
        headerMap.put(DbConstants.SEAT_IS_ACTIVE, translator.translate("seat.header.is_active"));

        List<Seat> seatList = seatController.getAllSeats();

        List<Map<String, Object>> seatData = new ArrayList<>();
        for (Seat seat : seatList) {
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
