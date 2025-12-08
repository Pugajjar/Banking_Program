// designing the banking program
import java.util.Scanner;
public class Main{
    static Scanner scanner = new Scanner(System.in);

    static void showBalance(double balance){
        System.out.println("============================================");
        System.out.printf("$ %.2f \n", balance);
        System.out.println("============================================");
    }
    static double deposit()
    {
        double amount;
        System.out.println("============================================");
        System.out.println("How much money you want to deposit ??");
        System.out.println("============================================");
        amount = scanner.nextDouble();
        if(amount<0){
            System.out.println("Amount cannot be negative");
            return 0;
        }
        else{
            return amount;
        }
    }
    static double withdraw(){
        double amount;
        System.out.println("============================================");
        System.out.println("Enter the amount you want to Withdraw :");
        System.out.println("============================================");
        amount = scanner.nextDouble();
        if(amount <0){
            System.out.println("Negative amount cannot be deducted");
            return 0;
        }
        else {
            return amount;
        }
    }

    public static void main(String[] args){


    // declare the variable
    double balance = 0;
    boolean isRunning = true ;
    int choice ;
    // display the menu to the users

        while(isRunning){
            System.out.println("**********************************************************");
            System.out.println("Banking Program");
            System.out.println("**********************************************************");


            System.out.println("Please Select the Options from above :");
            System.out.println("----------------------------------------------------------");
            System.out.println("1 -> Balance");
            System.out.println("----------------------------------------------------------");
            System.out.println("2 -> Deposit");
            System.out.println("----------------------------------------------------------");
            System.out.println("3 -> Withdraw");
            System.out.println("----------------------------------------------------------");
            System.out.println("4 -> exit..");
            System.out.println("----------------------------------------------------------");

            // as requested the process will start with the app

            System.out.print("Enter the Desired choice [1-4] :");
            choice = scanner.nextInt();


            switch (choice){
                case 1 -> showBalance(balance);
                case 2 -> balance = balance + deposit();
                case 3 -> balance = balance - withdraw();
                case 4 -> isRunning= false;
                default -> System.out.println("INVALID CHOICE !!");

            }

        }


        scanner.close();

    }
}