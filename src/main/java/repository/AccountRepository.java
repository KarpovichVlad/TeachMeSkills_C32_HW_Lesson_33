package repository;

import db.Database;
import model.Account;
import java.sql.*;

public class AccountRepository {

    public void createAccount(String accountNumber, double initialBalance) {
        if (getAccountByNumber(accountNumber) != null) {
            System.err.println("Ошибка: Аккаунт уже существует.");
            return;
        }

        String sql = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            stmt.setDouble(2, initialBalance);
            stmt.executeUpdate();
            System.out.println("Аккаунт создан: " + accountNumber);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании аккаунта: " + e.getMessage());
        }
    }

    public Account getAccountByNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("account_number"),
                        rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении аккаунта: " + e.getMessage());
        }
        return null;
    }

    public boolean updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении баланса: " + e.getMessage());
            return false;
        }
    }
}

