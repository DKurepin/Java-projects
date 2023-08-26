package transactions;

import accounts.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Transaction abstract class
 */
public abstract class Transaction {

    /**
     * Account for transaction
     */
    @Getter
    private Account account;

    /**
     * Payment Amount
     */
    @Getter
    private double paymentAmount;

    /**
     * Time of transaction
     */
    @Getter
    private LocalDate time;

    /**
     * Translation id
     */
    @Getter
    private UUID id;

    /**
     * Boolean is Transaction canceled
     */
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private boolean isCanceled;

    /**
     * Boolean is Transaction successful
     */
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private boolean isSuccessful;


    /**
     * Transaction
     * @param account
     * @param paymentAmount
     * @throws IllegalArgumentException is account or amount are not valid
     */
    public Transaction(Account account, double paymentAmount) {
        if (account == null)
            throw new IllegalArgumentException("Account cannot be null");
        if (paymentAmount < 0)
            throw new IllegalArgumentException("Payment amount cannot be negative");

        this.account = account;
        this.paymentAmount = paymentAmount;
        this.time = LocalDate.now();
        this.id = UUID.randomUUID();
        this.isCanceled = false;
        this.isSuccessful = false;
    }

    /**
     * Cancellation of transaction
     */
    public void cancel() {
    }
}

