package dao.IDAO;

import model.Review;
import java.sql.SQLException;
import java.util.List;

public interface IReviewDAO {
    List<Review> select(String whereClause, String[] params) throws SQLException, ClassNotFoundException;
    boolean insert(Review review) throws SQLException, ClassNotFoundException;
    boolean update(Review review) throws SQLException, ClassNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    void close() throws SQLException;
}
