package services;
import models.Account;
import models.CurrentAccount;
import models.Customer;
import models.SavingsAccount;

import java.util.Collection;
import java.util.HashMap;
public class Bank {
    private HashMap<String, Customer> customers;
    private HashMap<String, Account> globalAccounts;
    private int accountCounter = 1000;
    private int customerCounter = 1;
    public Bank(){
        this.customers = new HashMap<>();
        this.globalAccounts = new HashMap<>();
    }
    public String registerCustomer(String name){
        String customerId = "CUST" + (customerCounter++);
        Customer newCustomer = new Customer(customerId, name);
        customers.put(customerId, newCustomer);
        return customerId;
    }
    public String openSavingsAccount(String customerId, double initalDeposit,double interestRate,String pin) throws Exception{
        validateCustomerId(customerId);
        String accNum = "SAV" + (accountCounter++);
        SavingsAccount acc = new SavingsAccount(accNum, initalDeposit, interestRate,pin);
        customers.get(customerId).addAccount(acc);
        globalAccounts.put(accNum, acc);
        return accNum;
    }
    public String openCurrentAccount(String customerId, double initalDeposit,double overDraftLimit,String pin) throws Exception{
        validateCustomerId(customerId);
        String accNum = "CUR" + (accountCounter++);
        CurrentAccount acc = new CurrentAccount(accNum, initalDeposit, overDraftLimit,pin);
        customers.get(customerId).addAccount(acc);
        globalAccounts.put(accNum, acc);
        return accNum;
    }
    public Account getAccount(String accountNumber) throws Exception{
        if(!globalAccounts.containsKey(accountNumber)){
            throw new Exception("Account does not exist");
        }
        return globalAccounts.get(accountNumber);
    }
    private void validateCustomerId(String customerId) throws Exception{
        if(!customers.containsKey(customerId)){
            throw new Exception("Customer does not exist");
        }
    }
    public Collection<Customer> getAllCustomers(){
        return customers.values();
    }
    public  Collection<Account> getAllAccounts(){
        return globalAccounts.values();
    }
    public void injectExistingCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);

        // --- THE COUNTER FIX ---
        // Remove "CUST" from "CUST1" to isolate the number '1'
        int idNumber = Integer.parseInt(customer.getCustomerId().replace("CUST", ""));

        // If this loaded ID is equal to or higher than our counter, push the counter up!
        if (idNumber >= customerCounter) {
            customerCounter = idNumber + 1;
        }
    }
    public void injectExistingAccount(Account account, String customerId) {
        globalAccounts.put(account.getAccountNumber(), account);
        customers.get(customerId).addAccount(account);

        // --- THE COUNTER FIX ---
        // Accounts are "SAV1000" or "CUR1001".
        // .substring(3) chops off the first 3 letters to give us the raw number.
        int accNumber = Integer.parseInt(account.getAccountNumber().substring(3));

        if (accNumber >= accountCounter) {
            accountCounter = accNumber + 1;
        }
    }

}