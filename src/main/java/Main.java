import service.AccountService;
import service.TransactionService;

public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();

        accountService.createAccount("yeti", 500.00);
        accountService.createAccount("adam", 250.00);

        System.out.println("Баланс yeti: " + accountService.getBalance("yeti"));
        System.out.println("Баланс adam: " + accountService.getBalance("adam"));

        transactionService.transferMoney("yeti", "adam", 100);

        System.out.println("Баланс yeti: " + accountService.getBalance("yeti"));
        System.out.println("Баланс adam: " + accountService.getBalance("adam"));
    }
}

