package dao.IDAO;

import model.Route;
import java.sql.SQLException;
import java.util.List;

public interface IRouteDAO {
    List<Route> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(Route route) throws SQLException, ClassNotFoundException;
    boolean update(Route route) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
