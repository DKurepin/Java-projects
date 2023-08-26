package accounts;

import banks.Bank;
import clients.Client;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import transactions.Transaction;

import java.util.*;

/**
 * Account abstract class
 */
public abstract class Account {

    /**
     * List of transactions
     */
    private List<Transaction> transactions = new ArrayList<>();

    /**
     * Account holder
     */
    @Getter
    private Client accountHolder;

    /**
     * Client's bank
     */
    @Getter
    private Bank concreteBank;

    /**
     * Account id
     */
    @Getter
    private UUID id;

    /**
     * Account balance
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private double balance;

    /**
     * Abstract account
     * @param client
     * @param bank
     * @throws IllegalArgumentException if client or bank is null
     */
    public Account(Client client, Bank bank) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null");
        if (bank == null)
            throw new IllegalArgumentException("Bank cannot be null");

        this.accountHolder = client;
        this.concreteBank = bank;
        this.id = UUID.randomUUID();

    }

    /**
     * Unmodifiable list of transactions
     * @return transactions
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Deposits money into account
     * @param amount
     */
    public void deposit(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        balance += amount;
    }

    /**
     * Withdraws money from account
     * @param amount
     */
    public void withdraw(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        balance -= amount;
    }

    /**
     * Rewinds time in account and accumalutates money in account
     * @param days
     */
    public void payoutRewindTime(int days){
    }

    /**
     * Adds transaction in list of transactions
     * @param transaction
     * @throws IllegalArgumentException if transaction is null
     */
    public void addTransaction(Transaction transaction) {
        if (transaction == null)
            throw new IllegalArgumentException("Transaction cannot be null");
        transactions.add(transaction);
    }

    /**
     * Checks if transaction is in list of transactions
     * @param transaction
     * @throws IllegalArgumentException if transaction is not found
     */
    public void transactionCheck(Transaction transaction) {
        if (!transactions.contains(transaction))
            throw new IllegalArgumentException("Transaction is not found");
    }

    /**
     * Checks if withdraw from account is possible
     * @param amount
     */
    public void checkWithdraw(double amount) {
    }

}
