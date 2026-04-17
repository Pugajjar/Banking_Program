package services;
import models.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class FileStorageManager {
    private static final String CUSTOMERS_FILE = "data/customer.txt";
    private static final String ACCOUNTS_FILE = "data/account.txt";
    private static final String TRANSACTIONS_FILE = "data/transaction.txt";

    public void saveCustomer(Bank bank){
        try{
            FileWriter fw = new FileWriter(CUSTOMERS_FILE);
            for(Customer c:bank.getAllCustomers()){
                fw.write(c.getCustomerId() + ","+ c.getName() + "\n");
            }
            fw.close();
            System.out.println("Customer saved successfully");
        }catch(Exception e){
            System.out.println("Error: Couldn't store Customre data");
            e.printStackTrace();
        }
    }
    public void saveAccounts(Bank bank) {
        try {
            // 1. Open your FileWriter pointing to ACCOUNTS_FILE in overwrite mode
            FileWriter writer = new FileWriter(ACCOUNTS_FILE);

            // 2. Loop through every customer
            for (Customer customer : bank.getAllCustomers()) {
                String ownerId = customer.getCustomerId();

                // 3. Loop through that specific customer's accounts
                for (Account acc : customer.getAccounts()) {

                    // 4. Check the true identity of the account
                    if (acc instanceof SavingsAccount) {
                        // "Cast" it to a SavingsAccount so you can access getInterestRate()
                        SavingsAccount sa = (SavingsAccount) acc;

                        // Format: SAVINGS,ACC1000,CUST1,5000.0,4.5
                        writer.write("SAVINGS," + sa.getAccountNumber() + "," +
                                ownerId + "," + sa.getBalance() + "," +
                                sa.getInterestRate() +","+ sa.getPin()+"\n");

                    } else if (acc instanceof CurrentAccount) {
                        // "Cast" it to a CurrentAccount so you can access getOverdraftLimit()
                        CurrentAccount ca = (CurrentAccount) acc;

                        // Format: CURRENT,ACC1001,CUST1,1500.0,500.0
                        writer.write("CURRENT," + ca.getAccountNumber() + "," +
                                ownerId + "," + ca.getBalance() + "," +
                                ca.getOverdraftLimit()+","+ca.getPin() + "\n");
                    }
                }
            }

            // 5. Close the writer
            writer.close();
            System.out.println("Account data saved successfully.");

        } catch (Exception e) {
            System.out.println("Error: Could not save account data.");
            e.printStackTrace();
        }
    }
    public void logTransaction(String accountNumber, Transaction tx)  {
        try {
            FileWriter fw = new FileWriter(TRANSACTIONS_FILE,true);
            fw.write("Account: "+ accountNumber+"|"+ tx.getFormattedDetails() + "\n");
            fw.close();

        }catch(IOException e){
            System.out.println("Error: Could not save transaction to the file.");
            e.printStackTrace();
        }
    }
    public void loadBankData(Bank bank){
        try{
            File customerfile = new File(CUSTOMERS_FILE);
            if(customerfile.exists()){
                Scanner sc = new Scanner(customerfile);
                while(sc.hasNextLine()){
                    String[] parts = sc.nextLine().split(",");
                    Customer c = new Customer(parts[0], parts[1]);
                    bank.injectExistingCustomer(c);
                }
                sc.close();
                System.out.println("Customer memory loaded successfully");
            }
            File accountfile = new File(ACCOUNTS_FILE);
            if(accountfile.exists()) {
                Scanner sc = new Scanner(accountfile);
                while (sc.hasNextLine()) {
                    String[] parts = sc.nextLine().split(",");
                    String type = parts[0];
                    String accNum = parts[1];
                    String OwnerId = parts[2];
                    double Balance = Double.parseDouble(parts[3]);
                    if (type.equals("SAVINGS")) {
                        double interestRate = Double.parseDouble(parts[4]);
                        String pin = parts[5];
                        SavingsAccount sa = new SavingsAccount(accNum, Balance, interestRate,pin);
                        bank.injectExistingAccount(sa, OwnerId);
                    } else if (type.equals("CURRENT")) {
                        double overdraftLimit = Double.parseDouble(parts[4]);
                        String pin = parts[5];
                        CurrentAccount ca = new CurrentAccount(accNum, Balance, overdraftLimit,pin);
                        bank.injectExistingAccount(ca, OwnerId);
                    }
                }
                sc.close();
                System.out.println("Account data memory loaded successfully");
            }
        }catch(Exception e){
            System.out.println("Error: Loading data:" + e.getMessage());
        }
    }
}