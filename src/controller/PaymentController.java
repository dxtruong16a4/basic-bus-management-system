package controller;

import java.util.List;
import java.util.Map;

import dao.PaymentDAO;
import dao.DAO;
import model.Payment;
import utility.DbConnect;

public class PaymentController {
    private DbConnect dbConnect = null;
    private PaymentDAO paymentDAO = null;
    private static PaymentController instance = null;
    
    public static PaymentController getInstance() {
        if (instance == null) {
            instance = new PaymentController();
        }
        return instance;
    }
    
    private PaymentController() {
        dbConnect = new DbConnect();
        paymentDAO = new PaymentDAO(dbConnect);
    }

    public boolean addPayment(Payment payment) {
        try {
            return paymentDAO.insert(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePayment(Payment payment) {
        try {
            return paymentDAO.update(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePayment(int paymentId) {
        try {
            return paymentDAO.delete(paymentId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Payment> getAllPayments() {
        try {
            return paymentDAO.select(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Payment getPaymentBy(String whereClause, String[] params) {
        try {
            List<Payment> payments = paymentDAO.select(whereClause, params);
            return payments.isEmpty() ? null : payments.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getColumnDataTypes() {
        try {
            return DAO.getColumnDataTypes(paymentDAO.getTableName());
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }
}
