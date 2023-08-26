package transactions;

import accounts.Account;

/**
 * WithdrawTransaction class
 */
public class WithdrawTransaction extends Transaction {

    /**
     * new WithdrawTransaction
     * @param account
     * @param paymentAmount
     */
    public WithdrawTransaction(Account account, double paymentAmount) {
        super(account, paymentAmount);
        account.checkWithdraw(paymentAmount);
        account.withdraw(paymentAmount);
        account.addTransaction(this);
        setSuccessful(true);
    }

    @Override
    public void cancel() {
        if (isCanceled())
            throw new IllegalStateException("Transaction is already canceled");
        if (!isSuccessful())
            throw new IllegalStateException("Transaction is not successful");

        getAccount().deposit(getPaymentAmount());
        setCanceled(true);
    }
}
