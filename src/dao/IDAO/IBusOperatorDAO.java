package dao.IDAO;

import model.BusOperator;
import java.sql.SQLException;
import java.util.List;

public interface IBusOperatorDAO {
    List<BusOperator> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(BusOperator busOperator) throws SQLException, ClassNotFoundException;
    boolean update(BusOperator busOperator) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
