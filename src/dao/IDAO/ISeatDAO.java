package dao.IDAO;

import model.Seat;
import java.sql.SQLException;
import java.util.List;

public interface ISeatDAO {
    List<Seat> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(Seat seat) throws SQLException, ClassNotFoundException;
    boolean update(Seat seat) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
