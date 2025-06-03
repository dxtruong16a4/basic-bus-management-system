package model;

import java.sql.Timestamp;
import java.util.Date;

public class Review {
    private int reviewId;
    private int userId;
    private int busId;
    private int operatorId;
    private int rating;
    private String reviewText;
    private Timestamp reviewDate;
    private Date tripDate;

    public Review() {}

    public Review(int reviewId, int userId, int busId, int operatorId,
                 int rating, String reviewText, Timestamp reviewDate, Date tripDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.busId = busId;
        this.operatorId = operatorId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.tripDate = tripDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBusId() {
        return busId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public Date getTripDate() {
        return tripDate;
    }
}
