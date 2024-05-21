import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Base class representing a generic bank account
abstract class Account {
    private String accountNumber;
    private String accountHolder;
    protected double balance;

    public Account(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Abstract methods for deposit and withdrawal
    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);
}

// Subclass representing a savings account
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance, double interestRate) {
        super(accountNumber, accountHolder, initialBalance);
        this.interestRate = interestRate;
    }

    // Getters and Setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    // Method to calculate interest
    public void calculateInterest() {
        double interest = balance * interestRate / 100;
        balance += interest;
        System.out.println("Interest added: " + interest + ". New Balance: " + balance);
    }
}

// Subclass representing a current account
class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolder, double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolder, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    // Getters and Setters
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount or exceeds overdraft limit.");
        }
    }
}

// Main class to simulate the bank account system
public class BankAccountSystem {
    private List<Account> accounts;

    public BankAccountSystem() {
        this.accounts = new ArrayList<>();
    }

    // Methods to manage accounts
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void deleteAccount(String accountNumber) {
        accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
    }

    public Account findAccountByNumber(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        BankAccountSystem bankSystem = new BankAccountSystem();
        Scanner scanner = new Scanner(System.in);

        // Sample data
        SavingsAccount savingsAccount = new SavingsAccount("S001", "Harsh", 1000.0, 3.5);
        CurrentAccount currentAccount = new CurrentAccount("C001", "Yash", 500.0, 200.0);
        bankSystem.addAccount(savingsAccount);
        bankSystem.addAccount(currentAccount);

        // Example operations
        System.out.println("Depositing to Savings Account:");
        savingsAccount.deposit(200.0);

        System.out.println("\nWithdrawing from Savings Account:");
        savingsAccount.withdraw(100.0);

        System.out.println("\nCalculating Interest for Savings Account:");
        savingsAccount.calculateInterest();

        System.out.println("\nDepositing to Current Account:");
        currentAccount.deposit(300.0);

        System.out.println("\nWithdrawing from Current Account:");
        currentAccount.withdraw(600.0);

        System.out.println("\nAttempting to Withdraw Exceeding Overdraft Limit in Current Account:");
        currentAccount.withdraw(200.0);

        // Display account balances
        System.out.println("\nFinal Account Balances:");
        System.out.println("Savings Account Balance: " + savingsAccount.getBalance());
        System.out.println("Current Account Balance: " + currentAccount.getBalance());

        scanner.close();
    }
}
