package banks;

import accounts.Account;
import accounts.CreditAccount;
import accounts.DebitAccount;
import accounts.DepositAccount;
import clients.Client;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import observer.Observable;
import transactions.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Bank class
 */
public class Bank implements Observable {

    /**
     * List of transactions
     */
    private List<Transaction> transactions = new ArrayList<>();

    /**
     * List of clients
     */
    private List<Client> clients = new ArrayList<>();

    /**
     * List of accounts
     */
    private List<Account> accounts = new ArrayList<>();

    /**
     * Bank's name
     */
    @Getter
    private String name;

    /**
     * Bank's settings
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private BankSettings settings;

    /**
     * Bank's id
     */
    @Getter
    private UUID id;

    /**
     * New Bank
     * @param name
     * @param settings
     * @throws IllegalArgumentException if name or settings are invalid
     */

    public Bank(String name, BankSettings settings) {
        if (isNullOrWhiteSpace(name))
            throw new IllegalArgumentException("Name cannot be null or empty");
        if (settings == null)
            throw new IllegalArgumentException("Settings cannot be null");

        this.name = name;
        this.settings = settings;
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
     * Unmodifiable list of clients
     * @return clients
     */

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    /**
     * Unmodifiable list of accounts
     * @return accounts
     */
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    /**
     * Adds client into Bank
     * @param client
     * @throws IllegalArgumentException if client is null
     */
    public void addClient(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null");
        clients.add(client);
    }

    /**
     * Creates Credit Account
     * @param client
     * @throws IllegalArgumentException if client not found or null
     * @return credit account
     */
    public CreditAccount createCreditAccount(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null");
        if (!bankHasClient(client))
            throw new IllegalArgumentException("Client is not registered in this bank");
        CreditAccount account = new CreditAccount(client, this);
        accounts.add(account);
        return account;
    }

    /**
     * Creates Deposit Account
     * @param client
     * @param closingDate
     * @throws IllegalArgumentException if client is null or not registred or closing date not valid
     * @return deposit account
     */
    public DepositAccount createDepositAccount(Client client, LocalDate closingDate) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null");
        if (!bankHasClient(client))
            throw new IllegalArgumentException("Client is not registered in this bank");
        if (isNullOrWhiteSpace(closingDate.toString()))
            throw new IllegalArgumentException("Closing date cannot be null or empty");
        if (closingDate.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Closing date cannot be in the past");
        DepositAccount account = new DepositAccount(client, this, closingDate);
        accounts.add(account);
        return account;
    }

    /**
     * Creates Debit Account
     * @param client
     * @throws IllegalArgumentException if client is null or not registered
     * @return debit client
     */
    public DebitAccount createDebitAccount(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null");
        if (!bankHasClient(client))
            throw new IllegalArgumentException("Client is not registered in this bank");
        DebitAccount account = new DebitAccount(client, this);
        accounts.add(account);
        return account;
    }

    @Override
    public void addObserver(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null");
        if (bankHasClient(client))
            client.changeSubscription(true);
    }

    @Override
    public void removeObserver(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null");
        if (bankHasClient(client))
            client.changeSubscription(false);
    }

    @Override
    public void notifyObservers(String message) {
        List<Client> subscribedClients = clients.stream()
                .filter(Client::isSubscribed)
                .toList();
        for (Client client : subscribedClients) {
            String notification = String.format("Bank %s notifies you: %s", name, message);
            client.update(notification);
        }
    }

    /**
     * Checks if bank has client
     * @param client
     * @throws IllegalArgumentException if client is null
     * @return true or false
     */
    public boolean bankHasClient(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null");
        return clients.contains(client);
    }

    /**
     * Changes Suspicious Client Limit and Notifies observers about changes
     * @param newSuspiciousClientLimit
     */
    public void changeSuspiciousClientLimit(double newSuspiciousClientLimit) {
        settings.changeSuspiciousClientLimit(newSuspiciousClientLimit);
        String notification = String.format("Suspicious client limit changed to %s", newSuspiciousClientLimit);
        notifyObservers(notification);
    }

    /**
     * Changes Debit Interest and Notifies observers about changes
     * @param newDebitInterest
     */
    public void changeDebitInterest(double newDebitInterest) {
        settings.changeDebitInterest(newDebitInterest);
        String notification = String.format("Debit interest changed to %s", newDebitInterest);
        notifyObservers(notification);
    }

    /**
     * Changes Credit Limit and Notifies observers about changes
     * @param newCreditLimit
     */

    public void changeCreditLimit(double newCreditLimit) {
        settings.changeCreditLimit(newCreditLimit);
        String notification = String.format("Credit limit changed to %s", newCreditLimit);
        notifyObservers(notification);
    }

    /**
     * Changes Min Deposit Interest and Notifies observers about changes
     * @param newMinDepositInterest
     */
    public void changeMinDepositInterest(double newMinDepositInterest) {
        settings.changeMinDepositInterest(newMinDepositInterest);
        String notification = String.format("Minimal deposit interest changed to %s", newMinDepositInterest);
        notifyObservers(notification);
    }

    /**
     * Changes Mid Deposit Interest and Notifies observers about changes
     * @param newMidDepositInterest
     */
    public void changeMidDepositInterest(double newMidDepositInterest) {
        settings.changeMidDepositInterest(newMidDepositInterest);
        String notification = String.format("Middle deposit interest changed to %s", newMidDepositInterest);
        notifyObservers(notification);
    }

    /**
     * Changes Max Deposit Interest and Notifies observers about changes
     * @param newMaxDepositInterest
     */
    public void changeMaxDepositInterest(double newMaxDepositInterest) {
        settings.changeMaxDepositInterest(newMaxDepositInterest);
        String notification = String.format("Maximal deposit interest changed to %s", newMaxDepositInterest);
        notifyObservers(notification);
    }

    /**
     * Changes Credit Commission and Notifies observers about changes
     * @param newCreditCommission
     */
    public void changeCreditCommission(double newCreditCommission) {
        settings.changeCreditCommission(newCreditCommission);
        String notification = String.format("Credit commission changed to %s", newCreditCommission);
        notifyObservers(notification);
    }

    /**
     * Checks if String is null or WhiteSpace
     * @param str
     * @return true or false
     */
    private boolean isNullOrWhiteSpace(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }
}
