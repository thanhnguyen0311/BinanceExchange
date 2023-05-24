package Menu;

import static Market.MarketOverview.BTCMarketOverview;
import static Market.MarketOverview.USDTMarketOverview;
import static Menu.Login.login;
import static Menu.Login.sc;
import static Menu.Register.register;

public class WelcomePage {
    public static boolean check = true;
    private static  final int EXIT = 0;
    private static final int LOGIN = 3;
    private static final int REGISTER = 4;
    private static final int USDT_MARKET = 1;
    private static final int BTC_MARKET = 2;
    public static final String YELLOW = "\033[1;33m";
    public static final String RESET = "\u001B[0m";
    public static final String WHITE = "\033[1;97m";

    public static final String BLUE = "\033[1;94m";
    public static final String GREEN = "\033[0;32m";
    public static final String red = "\033[0;31m";
    public static final String RED = "\033[1;31m";

    public static void welcome() {
        System.out.println( YELLOW + "WELCOME TO BINANCE" + RESET);
        System.out.println("1. USDT MARKET ");
        System.out.println("2. BTC MARKET");
        System.out.println("3. Login to your account");
        System.out.println("4. Register new account");
        System.out.println("0. Exit");
            switch (inputCheck(0,4)) {
                case USDT_MARKET -> USDTMarketOverview();
                case BTC_MARKET -> BTCMarketOverview();
                case LOGIN -> login();
                case REGISTER -> register();
                case EXIT -> System.exit(0);
                }
            }

    public static int inputCheck(int min, int max){
        check = true;
        System.out.println(YELLOW + "ENTER YOUR CHOICE :" + RESET);
        int input;
        while (check){
            while(!sc.hasNextInt()){
                System.out.println(red + "invalid input" + RESET);
                sc.next();
            }
            input = sc.nextInt();
            if (input >= min && input <= max){
                return input;
            }
            System.out.println(red + "invalid input" + RESET);
        }
        return EXIT;
    }
}
