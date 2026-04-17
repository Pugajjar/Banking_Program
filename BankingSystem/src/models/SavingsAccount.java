    package models;

    public class SavingsAccount extends Account {
        private double interestRate;

        public SavingsAccount(String accountNumber, double initialBalance, double interestRate,String pin) {
            super(accountNumber, initialBalance,pin);
            this.interestRate = interestRate;
        }

        @Override
        public void withdraw(double amount) throws Exception {
            if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive.");
            if (amount > balance) throw new Exception("Insufficient funds! Available: $" + balance);

            balance -= amount;
            recordTransaction("WITHDRAW", amount);
        }
        public void applyInterest() {
            double interest = balance * (interestRate / 100);
            balance += interest;
            recordTransaction("INTEREST_ADDED", interest);
        }

        public double getInterestRate() { return interestRate; }
    }