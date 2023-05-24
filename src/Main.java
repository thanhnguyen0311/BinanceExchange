import Account.AccService.AccountService;

import java.util.Timer;
import java.util.TimerTask;

import static Crypto.CryptoData.getCryptoFromData;
import static Market.MarketData.updateMarketfromData;
import static Market.MarketMenu.showUSDTMarket;
import static Market.MarketService.updatePrice5s;
import static Menu.WelcomePage.welcome;
import static TradeService.BuySellHistoryService.getOrdersfromData;


public class Main {

    public static void main(String[] args){
        try {
            getCryptoFromData();
            getOrdersfromData();
            updateMarketfromData();
            AccountService.importUserfromData();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    updatePrice5s();
                }
            };
            long delay = 3000L;
            Timer timer = new Timer("Timer");
            timer.schedule(timerTask, 0, delay);


//            TimerTask timerTask2 = new TimerTask() {
//                @Override
//                public void run() {
//                    try {
////                        CheckBuySellOrder();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            };
//            long delay2 = 2000L;
//            Timer timer2 = new Timer("Timer");
//            timer2.schedule(timerTask2, 0, delay2);
            showUSDTMarket();
            welcome();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}