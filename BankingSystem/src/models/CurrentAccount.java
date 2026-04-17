package models;

public class CurrentAccount extends Account {
    private double overdraftLimit;
    public CurrentAccount(String accountNumber,double initialBalance, double overdraftLimit,String pin){
        super(accountNumber,initialBalance,pin);
        this.overdraftLimit = overdraftLimit;
    }
    @Override
    public void withdraw(double amount) throws Exception{
        if(amount<= 0){
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        double availableFunds = balance+overdraftLimit;
        if(amount> availableFunds){
            throw new Exception("Withdrawal exceeds overdraft limit! Max available: $" + availableFunds);
        }
        balance -= amount;
        recordTransaction("WITHDRAW",amount);
        if(balance < 0){
            System.out.println("Warning:Account is currently overdrawn");
        }
    }
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

}
