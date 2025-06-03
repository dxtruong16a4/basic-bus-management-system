package dao.IDAO;

import model.Bus;
import java.sql.SQLException;
import java.util.List;

public interface IBusDAO {
    List<Bus> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(Bus bus) throws SQLException, ClassNotFoundException;
    boolean update(Bus bus) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
