package transactions;

import accounts.Account;

/**
 * TopUpTransaction class
 */
public class TopUpTransaction extends Transaction{

    /**
     * new TopUpTransaction
     * @param account
     * @param paymentAmount
     * @throws IllegalArgumentException if amount is not valid
     */
    public TopUpTransaction(Account account, double paymentAmount) {
        super(account, paymentAmount);

        if (paymentAmount < 0)
            throw new IllegalArgumentException("Payment amount cannot be negative");
        account.deposit(paymentAmount);
        account.addTransaction(this);
        setSuccessful(true);
    }

    @Override
    public void cancel() {
        if (isCanceled())
            throw new IllegalStateException("Transaction is already canceled");
        if (!isSuccessful())
            throw new IllegalStateException("Transaction is not successful");

        getAccount().withdraw(getPaymentAmount());
        setCanceled(true);
    }
}
