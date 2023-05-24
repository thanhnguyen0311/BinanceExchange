package Menu.LoginMain.User;

import Account.Account;

import java.util.Scanner;

import static Account.AccService.UserRequestionService.getTransactionUser;
import static Account.AccService.UserRequestionService.transactionHistory;
import static Account.Account.*;
import static Account.Wallet.Wallet.getWallet;
import static Account.Wallet.WalletService.showWallet;
import static Market.MarketMenu.*;
import static Menu.LoginMain.User.UserDepositPage.userDepositPage;
import static Menu.LoginMain.User.UserWithdrawalPage.withdrawalPage;
import static Menu.WelcomePage.*;
import static TradeService.BuySellHistoryService.getBuySellMenu;


public class UserMain {
    static Scanner sc = new Scanner(System.in);
    private static final int USDT_market_user = 1;
    private static final int BTC_market_user = 2;
    private static final int WITHDRAWAL = 3;
    private static final int DEPOSIT = 4;

    private static final int TRANSACTION_HISTORY = 5;

    private static final int BUYSELL_HISTORY = 6;
    private static final int LOG_OUT = 7;
    private static final int EXIT = 0;

    public static void usermain(){
        timer3s.cancel();
        showWallet();
        System.out.println("1. USDT MARKET");
        System.out.println("2. BTC MARKET");
        System.out.println("3. Withdrawal");
        System.out.println("4. Deposit");
        System.out.println("5. Transaction History");
        System.out.println("6. Buy Sell History/Order");
        System.out.println("7. Log out");
        System.out.println("0. EXIT");
            switch (inputCheck(0,7)) {
                case USDT_market_user -> getUSDTMarketMenuUser();
                case BTC_market_user -> getBTCMarketMenuUser();
                case WITHDRAWAL -> withdrawalPage();
                case DEPOSIT -> userDepositPage();
                case TRANSACTION_HISTORY -> transactionHistory();
                case BUYSELL_HISTORY -> getBuySellMenu();
                case LOG_OUT -> {
                    setAccount(new Account(){} );
                    getWallet().clear();
                    getTransactionUser().clear();
                    welcome();
                }
                case EXIT -> System.exit(0);
            }
    }
}
