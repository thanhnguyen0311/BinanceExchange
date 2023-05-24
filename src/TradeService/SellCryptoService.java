package TradeService;

import Crypto.Crypto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

import static Account.AccService.AccountService.now;
import static Account.Account.getAccountlogged;
import static Account.Wallet.Wallet.getWallet;
import static Account.Wallet.WalletService.walletToFile;
import static Market.MarketService.getPrice;
import static Menu.LoginMain.User.UserMain.usermain;
import static Menu.WelcomePage.*;
import static TradeService.SellOrder.sellOrderUser;

public class SellCryptoService {
    private static final int AMOUNT = 1;
    private static final int SELL_ALL = 2;
    private static final int SELL_ORDER = 3;
    private static final int CANCEL = 0;
    static double sellAmount;

    static double oldAmount;
    static double newAmount;
    static double cryptoSell;
    static Scanner sc = new Scanner(System.in);
    public static void sellCrypto(Crypto crypto, Crypto crypto2) throws IOException {
        if (getWallet().get(crypto) == 0.0 || !getWallet().containsKey(crypto)) {
            System.out.println(red + "You don't have any " + crypto.getCrypto() + " in your wallet !" + RESET);
            usermain();
        }
        else {
            System.out.println("1. Enter amount. ");
            System.out.println("2. Sell all " + sellAllinfo(crypto,crypto2,getPrice(crypto,crypto2)));
            System.out.println("3. Sell Order ");
            System.out.println("0. Cancel.");
                switch (inputCheck(1,3)){
                    case AMOUNT -> enterAmount(crypto,crypto2);
                    case SELL_ALL -> sellAll(crypto,crypto2);
                    case SELL_ORDER -> sellOrderUser(crypto,crypto2);
                    case CANCEL -> usermain();
                }
            }
        }
    static String sellAllinfo(Crypto crypto, Crypto crypto2 ,double price){
        return GREEN + " (sell " + RESET + YELLOW + getWallet().get(crypto) + " " + crypto.getCrypto() + RESET + GREEN + " for " + RESET +
                YELLOW + getWallet().get(crypto)*price + " " + crypto2.getCrypto() + RESET + GREEN + ")" + RESET ;
    }
    static void sellAll(Crypto crypto , Crypto crypto2) throws IOException {
        sellAmount = getWallet().get(crypto) * getPrice(crypto,crypto2);
        cryptoSell = getWallet().get(crypto);
        if (getWallet().containsKey(crypto2)){
            oldAmount = getWallet().get(crypto2);
            newAmount = oldAmount + sellAmount;
            getWallet().replace(crypto2,oldAmount,newAmount);
        }
        else {
            getWallet().put(crypto2,sellAmount);
        }
        UUID id = UUID.randomUUID();
        addSellHistoryData(crypto, crypto2,getPrice(crypto,crypto2),cryptoSell, String.valueOf(id));
        getWallet().replace(crypto,0.0);
        walletToFile(getAccountlogged().getUsername());
        System.out.println(GREEN + "Congratulation on your selling " + RESET + YELLOW + cryptoSell + " " +
                crypto.getCrypto() + RESET + GREEN  + " at exchange rate is " + RESET + YELLOW + getPrice(crypto,crypto2) + RESET +
                GREEN +  " and receive " + RESET + YELLOW + sellAmount +  " " + crypto2.getCrypto() + RESET + "\n");
        usermain();
    }
    static void enterAmount(Crypto crypto, Crypto crypto2) throws IOException {
        System.out.println("type " + red + "cancel" + RESET + " to cancel this transaction");
        System.out.println("Enter amount of " + YELLOW + crypto.getCrypto() + RESET + " you want to sell :");
        while(!sc.hasNextDouble()) {
            System.out.println(red + "invalid input" + RESET);
            if(sc.next().equals("cancel")){
                usermain();
                break;
            }
        }
        cryptoSell = sc.nextDouble();
        if (getWallet().get(crypto) < cryptoSell) {
            System.out.println(red + "NOT ENOUGH " + crypto.getCrypto() + RESET);
            sellCrypto(crypto,crypto2);
        }
        else{
            sellAmount = cryptoSell * getPrice(crypto,crypto2);
            if (getWallet().containsKey(crypto2)){
                oldAmount = getWallet().get(crypto2);
                newAmount = oldAmount + sellAmount;
                getWallet().replace(crypto2,oldAmount,newAmount);
                oldAmount = getWallet().get(crypto);
                newAmount = oldAmount - cryptoSell;
                getWallet().replace(crypto,oldAmount,newAmount);
                walletToFile(getAccountlogged().getUsername());
                System.out.println(GREEN + "Congratulation on your selling " + RESET + YELLOW + cryptoSell + " " +
                        crypto.getCrypto() + RESET + GREEN  + " at exchange rate is " + RESET + YELLOW + getPrice(crypto,crypto2) + RESET +
                        GREEN +  " and receive " + RESET + YELLOW + sellAmount +  " " + crypto2.getCrypto() + RESET + "\n");
            }
            else{
                getWallet().put(crypto2,sellAmount);
                oldAmount = getWallet().get(crypto);
                newAmount = oldAmount - cryptoSell;
                getWallet().replace(crypto2,oldAmount,newAmount);
                walletToFile(getAccountlogged().getUsername());
                System.out.println(GREEN + "Congratulation on your selling " + RESET + YELLOW + cryptoSell + " " +
                        crypto.getCrypto() + RESET + GREEN  + " at exchange rate is " + RESET + YELLOW + getPrice(crypto,crypto2) + RESET +
                        GREEN +  " and receive " + RESET + YELLOW + sellAmount +  " " + crypto2.getCrypto() + RESET + "\n");
            }
            UUID id = UUID.randomUUID();
            addSellHistoryData(crypto, crypto2,getPrice(crypto,crypto2),cryptoSell, String.valueOf(id));
            usermain();
        }
    }
    static void addSellHistoryData(Crypto crypto, Crypto crypto2, double price,double sellamount, String id) throws IOException {
        now = LocalDateTime.now();
        String path = "Data/User/" + getAccountlogged().getUsername();
        FileWriter fileWriter = new FileWriter(path + "/buysellhistory.txt",true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("confirm,sell," + now + "," + crypto.getCrypto() + "," + crypto2.getCrypto() + "," + price + "," + sellamount + "," + id + "\n");
        bufferedWriter.close();
        fileWriter.close();
    }
}
