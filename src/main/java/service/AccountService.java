package service;

import model.Account;
import repository.AccountRepository;

public class AccountService {
    private final AccountRepository accountRepository = new AccountRepository();

    public void createAccount(String accountNumber, double initialBalance) {
        accountRepository.createAccount(accountNumber, initialBalance);
    }

    public double getBalance(String accountNumber) {
        Account account = accountRepository.getAccountByNumber(accountNumber);
        return (account != null) ? account.getBalance() : -1;
    }
}
