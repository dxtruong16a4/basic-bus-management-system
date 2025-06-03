package dao.IDAO;

import model.Booking;
import java.sql.SQLException;
import java.util.List;

public interface IBookingDAO {
    List<Booking> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(Booking booking) throws SQLException, ClassNotFoundException;
    boolean update(Booking booking) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
