package dao.IDAO;

import model.Payment;
import java.sql.SQLException;
import java.util.List;

public interface IPaymentDAO {
    List<Payment> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(Payment payment) throws SQLException, ClassNotFoundException;
    boolean update(Payment payment) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
