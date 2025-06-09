package utility.tableloader;

import java.util.*;
import model.BookingDetail;
import utility.app.AppTranslator;
import utility.db.DbConstants;
import utility.db.TableHelper;
import controller.BookingDetailController;
import javax.swing.JTable;

public class BookingDetailTableLoader {
    private final BookingDetailController bookingDetailController;
    private final AppTranslator translator;

    public BookingDetailTableLoader(BookingDetailController bookingDetailController, AppTranslator translator) {
        this.bookingDetailController = bookingDetailController;
        this.translator = translator;
    }

    public void load(JTable tbDetail, int offset, int limit) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.BOOKING_DETAIL_ID, translator.translate("booking_detail.header.booking_detail_id"));
        headerMap.put(DbConstants.BOOKING_ID, translator.translate("booking_detail.header.booking_id"));
        headerMap.put(DbConstants.SEAT_ID, translator.translate("booking_detail.header.seat_id"));
        headerMap.put(DbConstants.PASSENGER_NAME, translator.translate("booking_detail.header.passenger_name"));
        headerMap.put(DbConstants.PASSENGER_AGE, translator.translate("booking_detail.header.passenger_age"));
        headerMap.put(DbConstants.PASSENGER_GENDER, translator.translate("booking_detail.header.passenger_gender"));
        headerMap.put(DbConstants.FARE, translator.translate("booking_detail.header.fare"));
        headerMap.put(DbConstants.SEAT_NUMBER, translator.translate("booking_detail.header.seat_number"));

        List<BookingDetail> bookingDetailList = bookingDetailController.getAllBookingDetails();
        int toIndex = Math.min(offset + limit, bookingDetailList.size());
        List<BookingDetail> pageList = bookingDetailList.subList(offset, toIndex);

        List<Map<String, Object>> bookingDetailData = new ArrayList<>();
        for (BookingDetail detail : pageList) {
            Map<String, Object> detailMap = new HashMap<>();
            detailMap.put(DbConstants.BOOKING_DETAIL_ID, detail.getBookingDetailId());
            detailMap.put(DbConstants.BOOKING_ID, detail.getBookingId());
            detailMap.put(DbConstants.SEAT_ID, detail.getSeatId());
            detailMap.put(DbConstants.PASSENGER_NAME, detail.getPassengerName());
            detailMap.put(DbConstants.PASSENGER_AGE, detail.getPassengerAge());
            detailMap.put(DbConstants.PASSENGER_GENDER, detail.getPassengerGender());
            detailMap.put(DbConstants.FARE, detail.getFare());
            detailMap.put(DbConstants.SEAT_NUMBER, detail.getSeatNumber());
            bookingDetailData.add(detailMap);
        }

        TableHelper.loadTable(tbDetail, bookingDetailData, headerMap);
    }

    public void loadBy(JTable tbDetail, String searchBy, String searchValue, int offset, int limit) {
        Map<String, String> columnMap = Map.of(
            translator.translate("booking_detail.header.booking_detail_id"), DbConstants.BOOKING_DETAIL_ID,
            translator.translate("booking_detail.header.booking_id"), DbConstants.BOOKING_ID,
            translator.translate("booking_detail.header.seat_id"), DbConstants.SEAT_ID,
            translator.translate("booking_detail.header.passenger_name"), DbConstants.PASSENGER_NAME,
            translator.translate("booking_detail.header.passenger_age"), DbConstants.PASSENGER_AGE,
            translator.translate("booking_detail.header.passenger_gender"), DbConstants.PASSENGER_GENDER,
            translator.translate("booking_detail.header.fare"), DbConstants.FARE,
            translator.translate("booking_detail.header.seat_number"), DbConstants.SEAT_NUMBER
        );
        String realColumn = columnMap.getOrDefault(searchBy, searchBy);

        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.BOOKING_DETAIL_ID, translator.translate("booking_detail.header.booking_detail_id"));
        headerMap.put(DbConstants.BOOKING_ID, translator.translate("booking_detail.header.booking_id"));
        headerMap.put(DbConstants.SEAT_ID, translator.translate("booking_detail.header.seat_id"));
        headerMap.put(DbConstants.PASSENGER_NAME, translator.translate("booking_detail.header.passenger_name"));
        headerMap.put(DbConstants.PASSENGER_AGE, translator.translate("booking_detail.header.passenger_age"));
        headerMap.put(DbConstants.PASSENGER_GENDER, translator.translate("booking_detail.header.passenger_gender"));
        headerMap.put(DbConstants.FARE, translator.translate("booking_detail.header.fare"));
        headerMap.put(DbConstants.SEAT_NUMBER, translator.translate("booking_detail.header.seat_number"));

        String whereClause = realColumn + " LIKE ?";
        String[] params = new String[] { "%" + searchValue + "%" };

        List<BookingDetail> bookingDetailList = new ArrayList<>();
        try {
            bookingDetailList = bookingDetailController.getBookingDetailsBy(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int toIndex = Math.min(offset + limit, bookingDetailList.size());
        List<BookingDetail> pageList = bookingDetailList.subList(offset, toIndex);

        List<Map<String, Object>> bookingDetailData = new ArrayList<>();
        for (BookingDetail detail : pageList) {
            Map<String, Object> detailMap = new HashMap<>();
            detailMap.put(DbConstants.BOOKING_DETAIL_ID, detail.getBookingDetailId());
            detailMap.put(DbConstants.BOOKING_ID, detail.getBookingId());
            detailMap.put(DbConstants.SEAT_ID, detail.getSeatId());
            detailMap.put(DbConstants.PASSENGER_NAME, detail.getPassengerName());
            detailMap.put(DbConstants.PASSENGER_AGE, detail.getPassengerAge());
            detailMap.put(DbConstants.PASSENGER_GENDER, detail.getPassengerGender());
            detailMap.put(DbConstants.FARE, detail.getFare());
            detailMap.put(DbConstants.SEAT_NUMBER, detail.getSeatNumber());
            bookingDetailData.add(detailMap);
        }

        TableHelper.loadTable(tbDetail, bookingDetailData, headerMap);
    }
}
