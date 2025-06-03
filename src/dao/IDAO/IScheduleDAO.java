package dao.IDAO;

import model.Schedule;
import java.sql.SQLException;
import java.util.List;

public interface IScheduleDAO {
    List<Schedule> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(Schedule schedule) throws SQLException, ClassNotFoundException;
    boolean update(Schedule schedule) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
