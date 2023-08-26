package accounts;

import banks.Bank;
import banks.CentralBank;
import clients.Client;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DepositAccount class
 */
public class DepositAccount extends Account {

    /**
     * Account payout
     */
    @Getter
    private double payout = 0;

    /**
     * Closing date of deposit account
     */
    @Getter
    private LocalDate closingDate;

    /**
     * Is account expired
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private boolean isAccountExpired;

    /**
     * Const var of minimum deposit days
     */
    private static final int MIN_DEPOSIT_DAYS = 30;

    /**
     * Deposit account
     * @param client
     * @param bank
     * @param closingDate
     * @throws IllegalArgumentException if closing date is invalid
     */

    public DepositAccount(Client client, Bank bank, LocalDate closingDate) {
        super(client, bank);

        if (closingDate.isBefore(CentralBank.getInstance().getTimeManager().getCentralBankTime()))
            throw new IllegalArgumentException("Closing date cannot be in the past");
        if (closingDate.minusDays(MIN_DEPOSIT_DAYS).isBefore(CentralBank.getInstance().getTimeManager().getCentralBankTime()))
            throw new IllegalArgumentException("Deposit must be at least 30 days");
        this.closingDate = closingDate;
        isAccountExpired = false;
    }

    @Override
    public void payoutRewindTime(int days) {
        if (days < 0)
            throw new IllegalArgumentException("Days cannot be negative");
        double currentInterest = checkBalance(getBalance());
        LocalDate endPaymentDate = CentralBank.getInstance().getTimeManager().getCentralBankTime().plusDays(days);
        if (CentralBank.getInstance().getTimeManager().getCentralBankTime().getMonth() == endPaymentDate.getMonth()
                && CentralBank.getInstance().getTimeManager().getCentralBankTime().plusDays(days).isAfter(closingDate)) {
            payout = (getBalance() * currentInterest) * days;
            deposit(payout);
            payout = 0;
        } else {
            for (int day = 0; day < days; day++) {
                payout += (getBalance() * currentInterest);
                if (CentralBank.getInstance().getTimeManager().isItLastDayOfMonth()) {
                    deposit(payout);
                    payout = 0;
                }
                if (CentralBank.getInstance().getTimeManager().getCentralBankTime().equals(closingDate)) {
                    deposit(payout);
                    isAccountExpired = true;
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
        if (CentralBank.getInstance().getTimeManager().getCentralBankTime().isBefore(closingDate))
            throw new IllegalArgumentException("Deposit is not closed yet");
        if (!getAccountHolder().isVerified() && amount > getConcreteBank().getSettings().getSuspiciousClientLimit()) {
            String message = String.format("Unverified client can't withdraw more than %f",
                    getConcreteBank().getSettings().getSuspiciousClientLimit());
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks amount and gets appropriate deposit interest
     * @param amount
     * @return deposit interest
     */
    private double checkBalance(double amount) {
        if (amount < 50000)
            return getConcreteBank().getSettings().getMinDepositInterest();
        else if (amount < 100000)
            return getConcreteBank().getSettings().getMidDepositInterest();
        else
            return getConcreteBank().getSettings().getMaxDepositInterest();
    }
}
