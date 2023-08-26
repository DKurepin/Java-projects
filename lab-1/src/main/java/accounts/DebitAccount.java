package accounts;

import banks.Bank;
import banks.CentralBank;
import clients.Client;
import lombok.Getter;

import java.time.LocalDate;

/**
 * DebitAccount class
 */
public class DebitAccount extends Account {

    /**
     * Account's payout
     */
    @Getter
    private double payout = 0;

    /**
     * Debit account
     * @param client
     * @param bank
     */
    public DebitAccount(Client client, Bank bank) {
        super(client, bank);
    }

    @Override
    public void payoutRewindTime(int days) {
        if (days < 0)
            throw new IllegalArgumentException("Days cannot be negative");
        LocalDate endPaymentDate = CentralBank.getInstance().getTimeManager().getCentralBankTime().plusDays(days);
        if (CentralBank.getInstance().getTimeManager().getCentralBankTime().getMonth() == endPaymentDate.getMonth()) {
            double money = getBalance();
            for (int day = 0; day < days; day++) {
                money += (money * getConcreteBank().getSettings().getDebitInterest());
            }
            setBalance(money);
        } else {
            for (int day = 0; day < days; day++) {
                payout += (getBalance() * getConcreteBank().getSettings().getDebitInterest());
                if (CentralBank.getInstance().getTimeManager().isItLastDayOfMonth()) {
                    deposit(payout);
                    payout = 0;
                }
                CentralBank.getInstance().getTimeManager().rewindTimeDays(1);
            }
        }
    }

    @Override
    public void checkWithdraw(double amount) {
        if (getBalance() < amount)
            throw new IllegalArgumentException("Not enough money");
        if (!getAccountHolder().isVerified() && amount > getConcreteBank().getSettings().getSuspiciousClientLimit()) {
            String message = String.format("Unverified client can't withdraw more than %f",
                    getConcreteBank().getSettings().getSuspiciousClientLimit());
            throw new IllegalArgumentException(message);
        }
    }
}
