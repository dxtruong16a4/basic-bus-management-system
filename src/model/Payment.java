package model;

import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private int bookingId;
    private double amount;
    private Timestamp paymentDate;
    private String paymentMethod;
    private String transactionId;
    private String paymentStatus;
    private String receiptUrl;

    public Payment() {}

    public Payment(int paymentId, int bookingId, double amount, Timestamp paymentDate,
                  String paymentMethod, String transactionId, String paymentStatus,
                  String receiptUrl) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.receiptUrl = receiptUrl;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }
}
