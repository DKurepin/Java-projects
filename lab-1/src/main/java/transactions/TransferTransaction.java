package transactions;

import accounts.Account;
import lombok.Getter;

/**
 * TransferTransaction class
 */
public class TransferTransaction extends Transaction{

    /**
     * Account receiver
     */
    @Getter
    private Account receiver;

    /**
     * new TransferTransaction
     * @param account
     * @param receiver
     * @param paymentAmount
     * @throws IllegalArgumentException if receiver or account are not valid
     */
    public TransferTransaction(Account account, Account receiver, double paymentAmount) {
        super(account, paymentAmount);

        if (receiver == null)
            throw new IllegalArgumentException("Receiver cannot be null");
        if (account == receiver)
            throw new IllegalArgumentException("Receiver cannot be the same as sender");
        else {
            this.receiver = receiver;
            account.withdraw(paymentAmount);
            account.addTransaction(this);

            receiver.deposit(paymentAmount);
            receiver.addTransaction(this);
            setSuccessful(true);
        }
    }

    @Override
    public void cancel() {
        if (isCanceled())
            throw new IllegalStateException("Transaction is already canceled");
        if (!isSuccessful())
            throw new IllegalStateException("Transaction is not successful");
        getAccount().deposit(getPaymentAmount());
        receiver.checkWithdraw(getPaymentAmount());
        receiver.withdraw(getPaymentAmount());
        setCanceled(true);
    }
}
