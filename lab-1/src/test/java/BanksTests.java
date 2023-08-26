import accounts.*;
import banks.*;
import clients.*;
import lombok.Getter;
import observer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import transactions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BanksTests {

    @Getter
    private CentralBank centralBank = CentralBank.getInstance();

    @Test
    public void Transaction_TransferWorks() throws Exception {
        BankSettings settings = new BankSettings(0.01, 10000, 0.01, 0.02, 0.03, 0.01, 5000);
        Bank bank = centralBank.createBank("Bank", settings);
        Client client1 = new Client("Biba", "Boba", "pr. Nevsky", "1234567890");
        Client client2 = new Client("Pipa", "Boba", "st. Dybenko ", "0987654321");
        bank.addClient(client1);
        bank.addClient(client2);

        DebitAccount account1 = bank.createDebitAccount(client1);
        DebitAccount account2 = bank.createDebitAccount(client2);
        account1.deposit(200);
        account2.deposit(100);
        Transaction transaction = new TransferTransaction(account1, account2, 100);


        Assertions.assertEquals(100, account1.getBalance());
        Assertions.assertEquals(200, account2.getBalance());
        Assertions.assertTrue(account1.getTransactions().contains(transaction));
        Assertions.assertTrue(account2.getTransactions().contains(transaction));
    }

    @Test
    public void Transaction_WithdrawForSuspiciousClientWorks() {
        try {
            BankSettings settings = new BankSettings(0.01, 10000, 0.01, 0.02, 0.03, 0.01, 5000);
            Bank bank = centralBank.createBank("Tink", settings);
            Client client1 = new Client("Thomas", "Shelby", "", "");
            bank.addClient(client1);

            DebitAccount account1 = bank.createDebitAccount(client1);
            account1.deposit(6000);
            account1.withdraw(6000);

            Assertions.assertEquals(0, account1.getBalance());
        } catch (Exception IllegalArgumentException) {
            Assertions.assertEquals("Unverified client can't withdraw more than 5000", IllegalArgumentException.getMessage());
        }
    }

    @Test
    public void ClientCanSubscribe_Notified() {
        BankSettings settings = new BankSettings(0.01, 10000, 0.01, 0.02, 0.03, 0.01, 5000);
        Bank bank = centralBank.createBank("Sber", settings);
        Client client1 = new Client("Daniil", "Kurepin", "some address", "1234567890");
        bank.addClient(client1);

        DebitAccount account1 = bank.createDebitAccount(client1);
        account1.deposit(10000);
        bank.addObserver(client1);
        bank.changeDebitInterest(0.02);

        Assertions.assertTrue(client1.getNotifications().stream().count() == 1);
        Assertions.assertEquals("Bank Sber notifies you: Debit interest changed to 0.02", client1.getNotifications().get(0));
    }

    @Test
    public void DebitAccountPayouts_RewindTimeWorks() {
        BankSettings settings = new BankSettings(0.01, 10000, 0.01, 0.02, 0.03, 0.01, 5000);
        Bank bank = centralBank.createBank("UB", settings);
        Client client1 = new Client("Top","G", "Top G address", "12213231");
        bank.addClient(client1);

        DebitAccount account1 = bank.createDebitAccount(client1);
        account1.deposit(10000);
        account1.payoutRewindTime(5);

        Assertions.assertEquals(10510.100501, account1.getBalance());
    }
}
