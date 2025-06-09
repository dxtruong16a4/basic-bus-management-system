package utility.db;

public class DbConstants {
    public static final String DATABASE = "qlxekhach";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE;
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";
    
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String REGISTRATION_DATE = "registration_date";
    public static final String LAST_LOGIN = "last_login";
    public static final String ACCOUNT_STATUS = "account_status";
    public static final String ROLE = "role";

    public static final String BUS_TABLE = "buses";
    public static final String BUS_ID = "bus_id";
    public static final String BUS_NUMBER = "bus_number";
    public static final String BUS_NAME = "bus_name";
    public static final String BUS_TYPE = "bus_type";
    public static final String TOTAL_SEATS = "total_seats";
    public static final String OPERATOR_ID = "operator_id";
    public static final String REGISTRATION_NUMBER = "registration_number";
    public static final String BUS_IS_ACTIVE = "is_active";

    public static final String OPERATOR_TABLE = "bus_operators";
    public static final String OPERATOR_NAME = "operator_name";
    public static final String CONTACT_PERSON = "contact_person";
    public static final String CONTACT_EMAIL = "contact_email";
    public static final String CONTACT_PHONE = "contact_phone";
    public static final String ADDRESS = "address";
    public static final String RATING = "rating";
    public static final String JOINED_DATE = "joined_date";

    public static final String ROUTE_TABLE = "routes";
    public static final String ROUTE_ID = "route_id";
    public static final String ORIGIN_CITY = "origin_city";
    public static final String DESTINATION_CITY = "destination_city";
    public static final String DISTANCE = "distance";
    public static final String ESTIMATED_DURATION = "estimated_duration";

    public static final String SCHEDULE_TABLE = "Schedules";
    public static final String SCHEDULE_ID = "schedule_id";
    public static final String DEPARTURE_TIME = "departure_time";
    public static final String ARRIVAL_TIME = "arrival_time";
    public static final String FREQUENCY = "frequency";
    public static final String SCHEDULE_IS_ACTIVE = "is_active";
    public static final String CREATED_DATE = "created_date";

    public static final String SEAT_TABLE = "Seats";
    public static final String SEAT_ID = "seat_id";
    public static final String SEAT_NUMBER = "seat_number";
    public static final String SEAT_IS_ACTIVE = "is_active";

    public static final String BOOKING_TABLE = "Bookings";
    public static final String BOOKING_ID = "booking_id";
    public static final String BOOKING_DATE = "booking_date";
    public static final String TOTAL_FARE = "total_fare";
    public static final String BOOKING_STATUS = "booking_status";
    public static final String PAYMENT_STATUS = "payment_status";
    public static final String NUMBER_OF_SEATS = "number_of_seats";
    public static final String TRIP_DATE = "trip_date";

    public static final String BOOKING_DETAIL_TABLE = "Booking_Details";
    public static final String BOOKING_DETAIL_ID = "booking_detail_id";
    public static final String PASSENGER_NAME = "passenger_name";
    public static final String PASSENGER_AGE = "passenger_age";
    public static final String PASSENGER_GENDER = "passenger_gender";
    public static final String FARE = "fare";

    public static final String PAYMENT_TABLE = "Payments";
    public static final String PAYMENT_ID = "payment_id";
    public static final String AMOUNT = "amount";
    public static final String PAYMENT_DATE = "payment_date";
    public static final String PAYMENT_METHOD = "payment_method";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String RECEIPT_URL = "receipt_url";

    public static final String FARE_TABLE = "Fares";
    public static final String FARE_ID = "fare_id";
    public static final String BASE_FARE = "base_fare";
    public static final String TAXES = "taxes";
    public static final String SERVICE_CHARGE = "service_charge";
    public static final String LAST_UPDATED = "last_updated";

    public static final String REVIEW_TABLE = "Reviews";
    public static final String REVIEW_ID = "review_id";
    public static final String REVIEW_TEXT = "review_text";
    public static final String REVIEW_DATE = "review_date";

    public static final String HIDDEN1 = "payments";
    public static final String HIDDEN2 = "reviews";
    public static final String HIDDEN3 = "sqlquery";
    public static final String HIDDEN4 = "users";
}
