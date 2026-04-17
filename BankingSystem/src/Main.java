import services.*;
import models.Account;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Java is looking here: " + new java.io.File(".").getAbsolutePath());
        Bank bank = new Bank();
        FileStorageManager fileStorageManager = new FileStorageManager();
        Scanner sc = new Scanner(System.in);

        fileStorageManager.loadBankData(bank);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== ENTERPRISE BANKING SYSTEM ===");
            System.out.println("1. Register New Customer");
            System.out.println("2. Open New Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Print Account Statement");
            System.out.println("6. Exit & Save");
            System.out.print("Select an option (1-6): ");
            int choice = sc.nextInt();
            sc.nextLine();
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Enter Customer Name: ");
                        String name = sc.nextLine();
                        String newId = bank.registerCustomer(name);
                        System.out.println("New Customer ID: " + newId);
                        fileStorageManager.saveCustomer(bank);
                        break;
                    case 2:
                        System.out.println("Enter Existing customer ID: ");
                        String custId = sc.nextLine();
                        System.out.println("Type 'S' for Savings or 'C' for Current");
                        String type = sc.nextLine().toUpperCase();
                        System.out.println("Enter initial deposit amount: $");
                        double initalDeposit = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("Enter your 4 digit pin");
                        String pin = sc.nextLine();

                        if (type.equals("S")) {
                            System.out.println("Enter interest rate(e.g., 4.5): ");
                            double interest = Double.parseDouble(sc.nextLine());
                            String accNum = bank.openSavingsAccount(custId, initalDeposit, interest,pin);
                            System.out.println("Savings account ID: " + accNum);
                        } else if (type.equals("C")) {
                            System.out.println("Enter overDraftLimit: $");
                            double overDraft = Double.parseDouble(sc.nextLine());
                            String accNum = bank.openCurrentAccount(custId, initalDeposit, overDraft,pin);
                            System.out.println("Current Account Opened! Account ID: " + accNum);
                        } else {
                            System.out.println("Invalid input");
                        }
                        fileStorageManager.saveCustomer(bank);
                        fileStorageManager.saveAccounts(bank);
                        break;
                    case 3:
                        System.out.print("Enter account number:");
                        String depaccNum = sc.nextLine();
                        System.out.print("Enter deposit amount: $");
                        double deposit = Double.parseDouble(sc.nextLine());
                        bank.getAccount(depaccNum).deposit(deposit);
                        System.out.println("Deposit amount has been deposited!");
                        fileStorageManager.saveAccounts(bank);
                        break;
                    case 4:
                        System.out.println("Enter account number:");
                        String accNum = sc.nextLine();
                        System.out.println("Enter the pin");
                        String enteredpin = sc.nextLine();
                        if(!bank.getAccount(accNum).verifyPin(enteredpin)) {
                            System.out.println("Invalid pin!");
                            break;
                        }
                        System.out.print("Enter withdrawal amount: $");
                        double withdrawal = Double.parseDouble(sc.nextLine());
                        bank.getAccount(accNum).withdraw(withdrawal);
                        System.out.println("Withdrawal amount has been withdrawn!");
                        fileStorageManager.saveAccounts(bank);
                        break;
                    case 5:
                        System.out.println("Enter account number:");
                        String statAccNum = sc.nextLine();
                        bank.getAccount(statAccNum).printStatement();
                        break;
                    case 6:
                        isRunning = false;
                        break;
                        default:
                            System.out.println("Invalid input");
                }

            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        }
    }
}
