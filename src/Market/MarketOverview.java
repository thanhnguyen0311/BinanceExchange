package Market;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static Market.MarketMenu.showBTCMarket;
import static Market.MarketMenu.showUSDTMarket;
import static Menu.WelcomePage.welcome;

public class MarketOverview {
    static Scanner sc = new Scanner(System.in);
     public static void USDTMarketOverview(){
        System.out.format("+-------------+-----------+%n");
        System.out.format("| USDT Market | Price     |%n");
        System.out.format("+-------------+-----------+%n");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                showUSDTMarket();
                System.out.println("Press any key to continue");
            }
        };
        long delay = 5000L;
        Timer timer = new Timer("Timer");
        timer.schedule(timerTask, 0, delay);
        if(sc.hasNext()){
            timer.cancel();
        }
        sc.next();
        welcome();
    }
    public static void BTCMarketOverview(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                showBTCMarket();
                System.out.println("Press any key to continue");
            }
        };
        long delay = 5000L;
        Timer timer = new Timer("Timer");
        timer.schedule(timerTask, 0, delay);
        if(sc.hasNext()){
            timer.cancel();
        }
        sc.next();
        welcome();
    }
}
