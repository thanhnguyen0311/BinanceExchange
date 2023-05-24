package Market;

import Crypto.Crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static Menu.LoginMain.Admin.AdminMain.adminMain;
import static TradeService.BuyCryptoService.buyCrypto;
import static Crypto.Crypto.getCryptos;
import static Crypto.CryptoService.*;
import static TradeService.SellCryptoService.sellCrypto;
import static Market.Market.*;
import static Market.MarketService.*;
import static Menu.LoginMain.User.UserMain.usermain;
import static Menu.WelcomePage.*;
import static Menu.WelcomePage.RESET;

public class MarketMenu {
    private static boolean checking = true;
    private static final int BUY = 1;
    private static final int SELL = 2;
    private static int j = 1;
    public static Timer timer3s = new Timer("Timer");
    public static TimerTask price3s ;
    static Scanner sc = new Scanner(System.in);
    public static void getUSDTMarketMenu() throws IOException {
        int i = 1;
        HashMap<Integer, Crypto> list = new HashMap<>();
        for (Crypto crypto : getCryptos()){
            checking = true;
            if(crypto.getCrypto().equals("USDT")) continue;
            getUSDTmarket().forEach((k, v) -> {
                if(k.getCrypto().equals(crypto.getCrypto())) {
                    checking = false;
                }
            });
            if(!checking) continue;
            System.out.println(i + ". " + crypto.getCrypto());
            list.put(i,crypto);
            i++;
        }
        System.out.println("0. Cancel");
        int input = inputCheck(0, list.size());
        if(input == 0) adminMain();
        addCryptoToUSDTMarket(list.get(input));
        System.out.println(GREEN + "Success! You had added " + list.get(input).getCrypto() + " to USDT MARKET" + RESET);
    }
    public static void getBTCMarketMenu() throws IOException {
        int i = 1;
        HashMap<Integer, Crypto> list = new HashMap<>();
        for (Crypto crypto : getCryptos()){
            checking = true;
            if(crypto.getCrypto().equals("BTC")) continue;
            getBTCmarket().forEach((k, v) -> {
                if(k.getCrypto().equals(crypto.getCrypto())) {
                    checking = false;
                }
            });
            if(!checking) continue;
            System.out.println(i + ". " + crypto.getCrypto());
            list.put(i,crypto);
            i++;
        }
        System.out.println("0. Cancel");
        int input = inputCheck(0, list.size());
        if(input == 0) adminMain();
        addCryptoToBTCMarket(list.get(input));
        System.out.println(GREEN + "Success! You had added " + list.get(input).getCrypto() + " to BTC MARKET" + RESET);
    }
    public static void showUSDTMarket() {
        String leftAlignFormat = "%-23s  |   %-10s  %n";
//        System.out.format("+-----------------+-----------+%n");
//        System.out.format("| USDT Market     | Price     |%n");
//        System.out.format("+-----------------+-----------+%n");
        getUSDTmarket().forEach((k, v) -> {
            try {
                String [] arr;
                FileReader fileReader = new FileReader("Data/Market/USDT/" + k.getCrypto() + "USDT.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                arr = bufferedReader.readLine().split(",");
                if (Double.parseDouble(arr[0]) >= Double.parseDouble(arr[1])){
                    System.out.format(leftAlignFormat,WHITE + k.getCrypto()+ RESET  + "/USDT" , GREEN + arr[0] + RESET);
                }
                else{
                    System.out.format(leftAlignFormat,WHITE + k.getCrypto() + RESET + "/USDT" , RED + arr[0] + RESET);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.format("+------------+-----------+%n");
    }
    public static void showCryptoMarket(Crypto crypto, Crypto crypto2) throws IOException{
        String leftAlignFormat = "%-23s  |   %-10s  %n";
        String [] arr;
        FileReader fileReader = new FileReader("Data/Market/" + crypto2.getCrypto() + "/" + crypto.getCrypto() + crypto2.getCrypto() + ".txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        arr = bufferedReader.readLine().split(",");
        if (Double.parseDouble(arr[0]) >= Double.parseDouble(arr[1])){
            System.out.format(leftAlignFormat,WHITE + crypto.getCrypto()+ RESET  + "/" + crypto2.getCrypto() , GREEN + arr[0] + RESET);
        }
        else{
            System.out.format(leftAlignFormat,WHITE + crypto.getCrypto()+ RESET  + "/" + crypto2.getCrypto() , RED + arr[0] + RESET);
        }
    }
    public static void showBTCMarket(){
        String leftAlignFormat = "%-23s  |   %-15s  %n";
//        System.out.format("+-----------------+-----------+%n");
//        System.out.format("| USDT Market     | Price     |%n");
//        System.out.format("+-----------------+-----------+%n");
        getBTCmarket().forEach((k, v) -> {
            try {
                String [] arr;
                FileReader fileReader = new FileReader("Data/Market/BTC/" + k.getCrypto() + "BTC.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                arr = bufferedReader.readLine().split(",");
                if (Double.parseDouble(arr[0]) >= Double.parseDouble(arr[1])){
                    System.out.format(leftAlignFormat,WHITE + k.getCrypto() + RESET + "/BTC", GREEN + arr[0] + RESET);
                }
                else{
                    System.out.format(leftAlignFormat,WHITE + k.getCrypto() + RESET + "/BTC", RED + arr[0] + RESET);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.format("+------------+-----------+%n");
    }
    public static void getUSDTMarketMenuUser(){
        try {
        j=1;
        HashMap<Integer, Crypto> listUSDTmarket = new HashMap<>();
            getUSDTmarket().forEach((k, v) -> {
                listUSDTmarket.put(j,k);
                System.out.println(j + ". " + k.getCrypto() + "/USDT");
                j++;
            });

        int finalInput = inputCheck(1,j);
        System.out.println(listUSDTmarket.get(finalInput).getCrypto() + "/USDT");
        System.out.println("1. Buy");
        System.out.println("2. Sell");
        System.out.println("3. Cancel");
        price3s = new TimerTask() {
            @Override
            public void run() {
                try {
                    showCryptoMarket(listUSDTmarket.get(finalInput),getUSDTcrypto());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer3s = new Timer("Timer");
        long delay3s = 3000L;
        timer3s.schedule(price3s, 0, delay3s);
            switch (inputCheck(1, 3)) {
                case BUY -> buyCrypto(listUSDTmarket.get(finalInput), getUSDTcrypto());
                case SELL -> sellCrypto(listUSDTmarket.get(finalInput), getUSDTcrypto());
                case 3 -> usermain();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getBTCMarketMenuUser(){
        j=1;
        HashMap<Integer, Crypto> listBTCmarket = new HashMap<>();
        getBTCmarket().forEach((k, v) -> {
            listBTCmarket.put(j,k);
            System.out.println(j + ". " + k.getCrypto() + "/USDT");
            j++;
        });
        check = true;
        int input = sc.nextInt();
        while(check) {
            if (input <= j || input > 0) break;
            System.out.println(red + "invalid input" + RESET);
            input = sc.nextInt();
        }
        int finalInput = input;
        System.out.println(listBTCmarket.get(finalInput).getCrypto() + "/BTC");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    showCryptoMarket(listBTCmarket.get(finalInput),getBTCcrypto());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        long delay = 5000L;
        Timer timer = new Timer("Timer");
        timer.schedule(timerTask, 0, delay);
    }
}
