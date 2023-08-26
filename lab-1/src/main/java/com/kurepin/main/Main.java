package com.kurepin.main;

import accounts.DebitAccount;
import banks.Bank;
import banks.BankSettings;
import banks.CentralBank;
import clients.Client;
import clients.ClientBuilder;

public class Main {
    public static void main(String[] args) {

        CentralBank centralBank = CentralBank.getInstance();
        BankSettings settings = new BankSettings(0.01, 10000, 0.01, 0.02, 0.03, 0.01, 5000);
        Bank bank = centralBank.createBank("Sber", settings);
        Client client1 = new Client("Daniil", "Kurepin", "some address", "1234567890");
        bank.addClient(client1);

        DebitAccount account1 = bank.createDebitAccount(client1);
        account1.deposit(10000);
        bank.addObserver(client1);
        bank.changeDebitInterest(0.02);

        System.out.println(client1.getNotifications());
    }
}