package service;

import repository.AccountRepository;
import model.Account;
import db.Database;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionService {
    private final AccountRepository accountRepository = new AccountRepository();

    public void transferMoney(String fromAccount, String toAccount, double amount) {
        if (fromAccount.equals(toAccount)) {
            System.err.println("Ошибка: Нельзя перевести деньги.");
            return;
        }

        Account sender = accountRepository.getAccountByNumber(fromAccount);
        Account receiver = accountRepository.getAccountByNumber(toAccount);

        if (sender == null || receiver == null) {
            System.err.println("Ошибка: Один из счетов не найден.");
            return;
        }

        if (sender.getBalance() < amount) {
            System.err.println("Ошибка: Недостаточно средств.");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            boolean withdrawn = accountRepository.updateBalance(fromAccount, sender.getBalance() - amount);
            boolean deposited = accountRepository.updateBalance(toAccount, receiver.getBalance() + amount);

            if (withdrawn && deposited) {
                conn.commit();
                System.out.println("Перевод выполнен: " + amount + " с " + fromAccount + " на " + toAccount);
            } else {
                conn.rollback();
                System.err.println("Ошибка: Перевод не выполнен.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка базы данных: " + e.getMessage());
        }
    }
}
