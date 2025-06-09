package utility.tableloader;

import java.util.*;
import model.Booking;
import utility.app.AppTranslator;
import utility.db.DbConstants;
import utility.db.TableHelper;
import controller.BookingController;
import javax.swing.JTable;

public class BookingTableLoader {
    private final BookingController bookingController;
    private final AppTranslator translator;

    public BookingTableLoader(BookingController bookingController, AppTranslator translator) {
        this.bookingController = bookingController;
        this.translator = translator;
    }

    public void load(JTable tbDetail, int offset, int limit) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.BOOKING_ID, translator.translate("booking.header.booking_id"));
        headerMap.put(DbConstants.USER_ID, translator.translate("booking.header.user_id"));
        headerMap.put(DbConstants.SCHEDULE_ID, translator.translate("booking.header.schedule_id"));
        headerMap.put(DbConstants.BOOKING_DATE, translator.translate("booking.header.booking_date"));
        headerMap.put(DbConstants.TOTAL_FARE, translator.translate("booking.header.total_fare"));
        headerMap.put(DbConstants.BOOKING_STATUS, translator.translate("booking.header.booking_status"));
        headerMap.put(DbConstants.PAYMENT_STATUS, translator.translate("booking.header.payment_status"));
        headerMap.put(DbConstants.NUMBER_OF_SEATS, translator.translate("booking.header.number_of_seats"));
        headerMap.put(DbConstants.TRIP_DATE, translator.translate("booking.header.trip_date"));

        List<Booking> bookingList = bookingController.getAllBookings();
        int toIndex = Math.min(offset + limit, bookingList.size());
        List<Booking> pageList = bookingList.subList(offset, toIndex);

        List<Map<String, Object>> bookingData = new ArrayList<>();
        for (Booking booking : pageList) {
            Map<String, Object> bookingMap = new HashMap<>();
            bookingMap.put(DbConstants.BOOKING_ID, booking.getBookingId());
            bookingMap.put(DbConstants.USER_ID, booking.getUserId());
            bookingMap.put(DbConstants.SCHEDULE_ID, booking.getScheduleId());
            bookingMap.put(DbConstants.BOOKING_DATE, booking.getBookingDate());
            bookingMap.put(DbConstants.TOTAL_FARE, booking.getTotalFare());
            bookingMap.put(DbConstants.BOOKING_STATUS, booking.getBookingStatus());
            bookingMap.put(DbConstants.PAYMENT_STATUS, booking.getPaymentStatus());
            bookingMap.put(DbConstants.NUMBER_OF_SEATS, booking.getNumberOfSeats());
            bookingMap.put(DbConstants.TRIP_DATE, booking.getTripDate());
            bookingData.add(bookingMap);
        }

        TableHelper.loadTable(tbDetail, bookingData, headerMap);
    }

    public void loadBy(JTable tbDetail, String searchBy, String searchValue, int offset, int limit) {
        Map<String, String> columnMap = Map.of(
            translator.translate("booking.header.booking_id"), DbConstants.BOOKING_ID,
            translator.translate("booking.header.user_id"), DbConstants.USER_ID,
            translator.translate("booking.header.schedule_id"), DbConstants.SCHEDULE_ID,
            translator.translate("booking.header.booking_date"), DbConstants.BOOKING_DATE,
            translator.translate("booking.header.total_fare"), DbConstants.TOTAL_FARE,
            translator.translate("booking.header.booking_status"), DbConstants.BOOKING_STATUS,
            translator.translate("booking.header.payment_status"), DbConstants.PAYMENT_STATUS,
            translator.translate("booking.header.number_of_seats"), DbConstants.NUMBER_OF_SEATS,
            translator.translate("booking.header.trip_date"), DbConstants.TRIP_DATE
        );
        String realColumn = columnMap.getOrDefault(searchBy, searchBy);

        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put(DbConstants.BOOKING_ID, translator.translate("booking.header.booking_id"));
        headerMap.put(DbConstants.USER_ID, translator.translate("booking.header.user_id"));
        headerMap.put(DbConstants.SCHEDULE_ID, translator.translate("booking.header.schedule_id"));
        headerMap.put(DbConstants.BOOKING_DATE, translator.translate("booking.header.booking_date"));
        headerMap.put(DbConstants.TOTAL_FARE, translator.translate("booking.header.total_fare"));
        headerMap.put(DbConstants.BOOKING_STATUS, translator.translate("booking.header.booking_status"));
        headerMap.put(DbConstants.PAYMENT_STATUS, translator.translate("booking.header.payment_status"));
        headerMap.put(DbConstants.NUMBER_OF_SEATS, translator.translate("booking.header.number_of_seats"));
        headerMap.put(DbConstants.TRIP_DATE, translator.translate("booking.header.trip_date"));

        String whereClause = realColumn + " LIKE ?";
        String[] params = new String[] { "%" + searchValue + "%" };

        List<Booking> bookingList = new ArrayList<>();
        try {
            bookingList = bookingController.getBookingsBy(whereClause, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int toIndex = Math.min(offset + limit, bookingList.size());
        List<Booking> pageList = bookingList.subList(offset, toIndex);

        List<Map<String, Object>> bookingData = new ArrayList<>();
        for (Booking booking : pageList) {
            Map<String, Object> bookingMap = new HashMap<>();
            bookingMap.put(DbConstants.BOOKING_ID, booking.getBookingId());
            bookingMap.put(DbConstants.USER_ID, booking.getUserId());
            bookingMap.put(DbConstants.SCHEDULE_ID, booking.getScheduleId());
            bookingMap.put(DbConstants.BOOKING_DATE, booking.getBookingDate());
            bookingMap.put(DbConstants.TOTAL_FARE, booking.getTotalFare());
            bookingMap.put(DbConstants.BOOKING_STATUS, booking.getBookingStatus());
            bookingMap.put(DbConstants.PAYMENT_STATUS, booking.getPaymentStatus());
            bookingMap.put(DbConstants.NUMBER_OF_SEATS, booking.getNumberOfSeats());
            bookingMap.put(DbConstants.TRIP_DATE, booking.getTripDate());
            bookingData.add(bookingMap);
        }

        TableHelper.loadTable(tbDetail, bookingData, headerMap);
    }
}
