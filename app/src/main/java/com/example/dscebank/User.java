package com.example.dscebank;

public class User {
    private String username;
    private String accountNumber;
    private String contactNumber;
    private String email;

    public User(String username, String accountNumber, String contactNumber, String email, String string) {
        this.username = username;
        this.accountNumber = accountNumber;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }
}
