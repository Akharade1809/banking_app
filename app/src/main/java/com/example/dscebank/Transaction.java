package com.example.dscebank;

public class Transaction {
    private int id;
    private String username;
    private double amount;
    private String transferType;
    private String recipientAccount;
    private String transactionDate; // You can add a timestamp for each transaction

    public Transaction() {
        // Empty constructor, required for some database operations
    }

    public Transaction(String username, double amount, String transferType, String recipientAccount, String transactionDate) {
        this.username = username;
        this.amount = amount;
        this.transferType = transferType;
        this.recipientAccount = recipientAccount;
        this.transactionDate = transactionDate;
    }

    public Transaction(double amount, String transferType, String recipientAccount, String timestamp) {
        this.amount=amount;
        this.transferType=transferType;
        this.recipientAccount=recipientAccount;
        transactionDate=timestamp;
    }

    // Add getter and setter methods for each field

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", amount=" + amount +
                ", transferType='" + transferType + '\'' +
                ", recipientAccount='" + recipientAccount + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }
}
