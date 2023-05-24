package Menu.LoginMain.Admin;

import java.io.IOException;

import static Market.MarketMenu.*;
import static Menu.LoginMain.Admin.AdminMain.adminMain;
import static Menu.WelcomePage.*;

public class addMarketMenu {
    private static final int USDT_market = 1;
    private static final int BTC_market = 2;
    private static final int BACK = 3;
    private static final int EXIT = 0;
    public static void AddCryptoToMarketMenu() throws IOException {
        System.out.println("1. Add Crypto to USDT market");
        System.out.println("2. Add Crypto to BTC market");
        System.out.println("3. Back");
        System.out.println("0. Exit");
        switch (inputCheck(0, 3)) {
            case USDT_market -> {
                getUSDTMarketMenu();
                adminMain();
            }
            case BTC_market -> {
                getBTCMarketMenu();
                adminMain();
            }
            case BACK -> adminMain();
            case EXIT -> System.exit(0);
        }
    }
}
