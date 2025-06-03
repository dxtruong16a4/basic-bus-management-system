package dao.IDAO;

import model.Fare;
import java.sql.SQLException;
import java.util.List;

public interface IFareDAO {
    List<Fare> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(Fare fare) throws SQLException, ClassNotFoundException;
    boolean update(Fare fare) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
