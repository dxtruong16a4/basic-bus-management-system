package controller;

import java.util.List;
import java.util.Map;

import dao.ReviewDAO;
import dao.DAO;
import model.Review;
import utility.DbConnect;

public class ReviewController {
    private DbConnect dbConnect = null;
    private ReviewDAO reviewDAO = null;
    private static ReviewController instance = null;
    
    public static ReviewController getInstance() {
        if (instance == null) {
            instance = new ReviewController();
        }
        return instance;
    }
    
    private ReviewController() {
        dbConnect = DbConnect.getInstance();
        reviewDAO = new ReviewDAO(dbConnect);
    }

    public boolean addReview(Review review) {
        try {
            return reviewDAO.insert(review);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateReview(Review review) {
        try {
            return reviewDAO.update(review);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReview(int reviewId) {
        try {
            return reviewDAO.delete(reviewId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Review> getAllReviews() {
        try {
            return reviewDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Review getReviewBy(String whereClause, String[] params) {
        try {
            List<Review> reviews = reviewDAO.select(whereClause, params);
            return reviews.isEmpty() ? null : reviews.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(reviewDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }
    
    public DbConnect getDbConnect() {
        return dbConnect;
    }
}
