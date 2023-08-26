package banks;

import accounts.TimeManager;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * CentralBank class
 */
public class CentralBank {

    /**
     * Instance of Central Bank
     */
    private static CentralBank instance = null;

    /**
     * List of banks
     */
    private List<Bank> banks;

    /**
     * Time Manager
     */
    @Getter
    private TimeManager timeManager;

    /**
     * CentralBank constructor
     */
    private CentralBank() {
        banks = new ArrayList<>();
        timeManager = new TimeManager();
    }

    /**
     * Unmodifiable list of banks
     * @return banks
     */
    public List<Bank> getBanks() {
        return Collections.unmodifiableList(banks);
    }

    /**
     * Checks if Central Bank created
     * @return true or false
     */
    public boolean isCentralBankCreated() {
        return instance != null;
    }

    /**
     * Get Instance of Central Bank
     * @return instance
     */
    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }

    /**
     * Checks if bank with this name exists
     * @param name
     * @throws IllegalArgumentException if name is not valid
     * @return true or false
     */
    public boolean bankExists(String name) {
        if (isNullOrWhiteSpace(name))
            throw new IllegalArgumentException("Name cannot be null or empty");
        return banks.stream().anyMatch(bank -> bank.getName().equals(name));
    }

    /**
     * Creates Bank
     * @param name
     * @param settings
     * @throws IllegalArgumentException if name is not valid or not in bank
     * @return bank
     */
    public Bank createBank(String name, BankSettings settings) {
        if (isNullOrWhiteSpace(name))
            throw new IllegalArgumentException("Name cannot be null or empty");
        if (settings == null)
            throw new IllegalArgumentException("Settings cannot be null");
        if (bankExists(name))
            throw new IllegalArgumentException("Bank with this name already exists");
        Bank bank = new Bank(name, settings);
        banks.add(bank);
        return bank;
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
