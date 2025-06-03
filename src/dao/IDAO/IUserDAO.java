package dao.IDAO;

import model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    List<User> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(User user) throws SQLException, ClassNotFoundException;
    boolean update(User user) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
