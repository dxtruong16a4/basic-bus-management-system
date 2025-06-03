package dao.IDAO;

import model.BookingDetail;
import java.sql.SQLException;
import java.util.List;

public interface IBookingDetailDAO {
    List<BookingDetail> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(BookingDetail bookingDetail) throws SQLException, ClassNotFoundException;
    boolean update(BookingDetail bookingDetail) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;   
}
