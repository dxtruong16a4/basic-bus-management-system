package utility.tableloader;

import java.util.*;
import model.BookingDetail;
import utility.DbConstants;
import utility.TableHelper;
import utility.AppTranslator;
import controller.BookingDetailController;
import javax.swing.JTable;

public class BookingDetailTableLoader {
    private final BookingDetailController bookingDetailController;
    private final AppTranslator translator;

    public BookingDetailTableLoader(BookingDetailController bookingDetailController, AppTranslator translator) {
        this.bookingDetailController = bookingDetailController;
        this.translator = translator;
    }

    public void load(JTable tbDetail) {
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

        List<Map<String, Object>> bookingDetailData = new ArrayList<>();
        for (BookingDetail detail : bookingDetailList) {
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

    public void loadByBookingId(JTable tbDetail, int bookingId) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.BOOKING_DETAIL_ID, translator.translate("booking_detail.header.booking_detail_id"));
        headerMap.put(DbConstants.BOOKING_ID, translator.translate("booking_detail.header.booking_id"));
        headerMap.put(DbConstants.SEAT_ID, translator.translate("booking_detail.header.seat_id"));
        headerMap.put(DbConstants.FARE, translator.translate("booking_detail.header.fare"));
        headerMap.put(DbConstants.SEAT_NUMBER, translator.translate("booking_detail.header.seat_number"));

        List<BookingDetail> bookingDetailList = bookingDetailController.getBookingDetailsByBookingId(bookingId);

        List<Map<String, Object>> bookingDetailData = new ArrayList<>();
        for (BookingDetail detail : bookingDetailList) {
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
