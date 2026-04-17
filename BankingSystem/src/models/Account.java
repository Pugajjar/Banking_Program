package models;

import interfaces.IAccount;

import java.util.ArrayList;

public abstract class Account implements IAccount{
    protected String accountNumber;
    protected double balance;
    protected ArrayList<Transaction> transactionHistory;
    private int transactionCounter = 1;
    protected String pin;
    public Account(String accountNumber, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        recordTransaction("INITIAL_DEPOSIT", initialBalance);
        this.pin = pin;
    }
    public boolean verifyPin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }
    public String getPin() {
        return this.pin;
    }

    public void deposit(double amount){
        if(amount <=0 ) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        balance += amount;
        recordTransaction("Deposit", amount);
    }
    public abstract void withdraw(double amount) throws Exception;


    protected  void recordTransaction(String type, double amount){
        String txID = accountNumber + "-TX" + (transactionCounter++);
        Transaction tx = new Transaction(txID,type,amount,balance);
        transactionHistory.add(tx);
        new services.FileStorageManager().logTransaction(this.accountNumber,tx);
    }
    @Override
    public void printStatement(){
        System.out.println("\n--- Statement for Account: " + accountNumber + " ---");
        for(Transaction tx : transactionHistory){
            System.out.println(tx.getFormattedDetails());
        }
        System.out.println("----------------------------------------\n");
    }
    public double getBalance(){
        return balance;
    }
    public String getAccountNumber(){
        return accountNumber;
    }

}