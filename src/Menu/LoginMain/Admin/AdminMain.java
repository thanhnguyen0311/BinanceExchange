package Menu.LoginMain.Admin;

import java.io.IOException;
import java.util.Scanner;

import static Account.AccService.AdminService.getRequestTransactionData;
import static Menu.LoginMain.Admin.addCrypto.AddCryptoMenu;
import static Menu.LoginMain.Admin.addMarketMenu.AddCryptoToMarketMenu;
import static Menu.WelcomePage.*;

public class AdminMain {
    private static final int ADD_CRYPTO = 1;
    private static final int ADD_MARKET = 2;
    private static final int REQUEST = 3;
    private static final int LOG_OUT = 4;
    private static final int EXIT = 0;
    static Scanner sc = new Scanner(System.in);
    public static void adminMain() throws IOException {
        System.out.println("1. ADD CRYPTO");
        System.out.println("2. ADD CRYPTO TO MARKET");
        System.out.println("3. TRANSACTION REQUEST");
        System.out.println("4. LOG OUT");
        System.out.println("0. EXIT");
        switch (inputCheck(0, 4)) {
            case ADD_CRYPTO -> {
                AddCryptoMenu();
                adminMain();
            }
            case ADD_MARKET -> AddCryptoToMarketMenu();
            case REQUEST -> getRequestTransactionData();
            case LOG_OUT -> welcome();
            case EXIT -> System.exit(0);
        }
    }
}
