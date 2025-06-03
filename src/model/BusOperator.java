package model;

import java.math.BigDecimal;
import java.sql.Date;

public class BusOperator {
    private int operatorId;
    private String operatorName;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private BigDecimal rating;
    private Date joinedDate;

    public BusOperator() {}

    public BusOperator(int operatorId, String operatorName, String contactPerson, String contactEmail, String contactPhone, String address, BigDecimal rating, Date joinedDate) {
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.address = address;
        this.rating = rating;
        this.joinedDate = joinedDate;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }
}
