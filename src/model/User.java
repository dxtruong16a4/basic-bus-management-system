package model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private java.sql.Date dateOfBirth;
    private java.sql.Timestamp registrationDate;
    private java.sql.Timestamp lastLogin;
    private String accountStatus;
    private String role;

    public User() {}

    public User(int userId, String username, String password, String email, String phoneNumber, String firstName, String lastName, java.sql.Date dateOfBirth, java.sql.Timestamp registrationDate, java.sql.Timestamp lastLogin, String accountStatus, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
        this.accountStatus = accountStatus;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public java.sql.Date getDateOfBirth() { return dateOfBirth; }
    public java.sql.Timestamp getRegistrationDate() { return registrationDate; }
    public java.sql.Timestamp getLastLogin() { return lastLogin; }
    public String getAccountStatus() { return accountStatus; }
    public String getRole() { return role; }
}
