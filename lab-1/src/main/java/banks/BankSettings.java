package banks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * BankSettings class
 */
public class BankSettings {

    /**
     * Debit Interest
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private double debitInterest;

    /**
     * Credit Limit
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private double creditLimit;

    /**
     * Min Deposit Interest
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private double minDepositInterest;

    /**
     * Mid Depodit Interest
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private double midDepositInterest;

    /**
     * Max Deposit Interest
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private double maxDepositInterest;

    /**
     * Credit Commission
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private double creditCommission;

    /**
     * Suspicious Client Limit
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private double suspiciousClientLimit;

    /**
     * New Bank Settings
     * @param debitInterest
     * @param creditLimit
     * @param minDepositInterest
     * @param midDepositInterest
     * @param maxDepositInterest
     * @param creditCommission
     * @param suspiciousClientLimit
     * @throws IllegalArgumentException if params not valid
     */
    public BankSettings(double debitInterest,
                        double creditLimit,
                        double minDepositInterest,
                        double midDepositInterest,
                        double maxDepositInterest,
                        double creditCommission,
                        double suspiciousClientLimit) {
        checkInterest(debitInterest);
        this.debitInterest = debitInterest;
        if (creditLimit < 0)
            throw new IllegalArgumentException("Credit limit cannot be negative");
        this.creditLimit = creditLimit;
        checkBoundariesDepositInterest(minDepositInterest, midDepositInterest, maxDepositInterest);
        checkInterest(minDepositInterest);
        checkInterest(midDepositInterest);
        checkInterest(maxDepositInterest);
        this.minDepositInterest = minDepositInterest;
        this.midDepositInterest = midDepositInterest;
        this.maxDepositInterest = maxDepositInterest;
        if (creditCommission < 0)
            throw new IllegalArgumentException("Credit commission cannot be negative");
        this.creditCommission = creditCommission;
        if (suspiciousClientLimit < 0)
            throw new IllegalArgumentException("Suspicious client limit cannot be negative");
        this.suspiciousClientLimit = suspiciousClientLimit;
    }

    /**
     * Changes Suspicious Client Limit
     * @param newSuspiciousClientLimit
     * @throws IllegalArgumentException if limit invalid
     */
    public void changeSuspiciousClientLimit(double newSuspiciousClientLimit) {
        if (suspiciousClientLimit < 0)
            throw new IllegalArgumentException("Suspicious client limit cannot be negative");
        this.suspiciousClientLimit = newSuspiciousClientLimit;
    }

    /**
     * Changes Debit Interest
     * @param newDebitInterest
     */
    public void changeDebitInterest(double newDebitInterest) {
        checkInterest(debitInterest);
        this.debitInterest = newDebitInterest;
    }

    /**
     * Changes Credit Limit
     * @param newCreditLimit
     */
    public void changeCreditLimit(double newCreditLimit) {
        if (creditLimit < 0)
            throw new IllegalArgumentException("Credit limit cannot be negative");
        this.creditLimit = newCreditLimit;
    }

    /**
     * Changes Min Deposit Interest
     * @param newMinDepositInterest
     */
    public void changeMinDepositInterest(double newMinDepositInterest) {
        if (newMinDepositInterest < 0 || newMinDepositInterest > 1)
            throw new IllegalArgumentException("Interest must be between 0 and 1");
        if (newMinDepositInterest > midDepositInterest || newMinDepositInterest > maxDepositInterest)
            throw new IllegalArgumentException("Min interest must be less than mid interest and mid interest must be less than max interest");
        this.minDepositInterest = newMinDepositInterest;
    }

    /**
     * Changes Mid Deposit Interest
     * @param newMidDepositInterest
     * @throws IllegalArgumentException if interest is not valid
     */
    public void changeMidDepositInterest(double newMidDepositInterest) {
        if (newMidDepositInterest < 0 || newMidDepositInterest > 1)
            throw new IllegalArgumentException("Interest must be between 0 and 1");
        if (newMidDepositInterest < minDepositInterest || newMidDepositInterest > maxDepositInterest)
            throw new IllegalArgumentException("Min interest must be greater than mid interest and mid interest must be greater than max interest");
        this.midDepositInterest = newMidDepositInterest;
    }

    /**
     * Changes Max Deposit Interest
     * @param newMaxDepositInterest
     * @throws IllegalArgumentException if interest is not valid
     */
    public void changeMaxDepositInterest(double newMaxDepositInterest) {
        if (newMaxDepositInterest < 0 || newMaxDepositInterest > 1)
            throw new IllegalArgumentException("Interest must be between 0 and 1");
        if (newMaxDepositInterest < minDepositInterest || newMaxDepositInterest < midDepositInterest)
            throw new IllegalArgumentException("Min interest must be greater than max interest and mid interest must be greater than max interest");
        this.maxDepositInterest = newMaxDepositInterest;
    }

    /**
     * Changes Credit Commission
     * @param newCreditCommission
     * @throws IllegalArgumentException if commission invalid
     */
    public void changeCreditCommission(double newCreditCommission) {
        if (creditCommission < 0)
            throw new IllegalArgumentException("Credit commission cannot be negative");
        this.creditCommission = newCreditCommission;
    }

    /**
     * Checks if Interest is valid
     * @param interest
     * @throws IllegalArgumentException if interest < 0 or interest > 1
     */
    private void checkInterest(double interest) {
        if (interest < 0 || interest > 1)
            throw new IllegalArgumentException("Interest must be between 0 and 1");
    }

    /**
     * Checks Interests are valid
     * @param minInterest
     * @param midInterest
     * @param maxInterest
     * @throws IllegalArgumentException if Interest is not valid
     */
    private void checkBoundariesDepositInterest(double minInterest, double midInterest, double maxInterest) {
        if (minInterest > midInterest || minInterest > maxInterest)
            throw new IllegalArgumentException("Min interest must be less than mid interest and mid interest must be less than max interest");
        if (midInterest < minInterest || midInterest > maxInterest)
            throw new IllegalArgumentException("Min interest must be greater than mid interest and mid interest must be greater than max interest");
        if (maxInterest < minInterest || maxInterest < midInterest)
            throw new IllegalArgumentException("Min interest must be greater than max interest and mid interest must be greater than max interest");
    }
}
