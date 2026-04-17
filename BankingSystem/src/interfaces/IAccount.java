package interfaces;

public interface IAccount {
    void deposit(double amount) throws IllegalArgumentException;
    void withdraw(double amount)throws Exception;
    double getBalance();
    String getAccountNumber();
    void printStatement();
}
