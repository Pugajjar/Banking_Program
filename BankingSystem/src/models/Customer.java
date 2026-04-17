package models;

import java.util.ArrayList;

public class Customer {
    private String customerId;
    private String name;
    private ArrayList<Account> accounts;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
    public String getName() {
        return name;
    }
    public String getCustomerId() {
        return customerId;
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}