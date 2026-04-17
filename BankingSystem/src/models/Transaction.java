package models;

import interfaces.ITransaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Transaction implements ITransaction {
    private String transactionID;
    private String type;
    private Double amount;
    private LocalDateTime timestamp;
    private double balanceAfter;
    public Transaction(String transactionID, String type, Double amount, double afterbalance) {
        this.transactionID = transactionID;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.balanceAfter = afterbalance;
    }
    public String getFormattedDetails(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: $%.2f | Balance: $%.2f | ID: %s",
                timestamp.format(dtf),type,amount, balanceAfter,transactionID);
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String getType() {
        return type;
    }
}
