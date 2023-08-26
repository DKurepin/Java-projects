package accounts;

import banks.Bank;
import clients.Client;
import lombok.Getter;

/**
 * CreditAccount class
 */
public class CreditAccount extends Account {

    /**
     * Credit account
     * @param client
     * @param bank
     */
    public CreditAccount(Client client, Bank bank) {
        super(client, bank);
    }

    /**
     * Credit commission
     * @return credit commission percentage
     */
    public double Commission() {
        return getConcreteBank().getSettings().getCreditCommission();
    }

    @Override
    public void checkWithdraw(double amount) {
        if (!getAccountHolder().isVerified() && amount > getConcreteBank().getSettings().getSuspiciousClientLimit())
            throw new IllegalArgumentException("Suspicious client limit exceeded");
        if (getBalance() - amount < -getConcreteBank().getSettings().getCreditLimit())
            throw new IllegalArgumentException("Credit limit exceeded");
        if (getBalance() < amount)
            setBalance(getBalance() - Commission());
    }
}
