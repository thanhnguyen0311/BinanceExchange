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
import static Menu.WelcomePage.RESET;
import static TradeService.BuyOrder.buyOrderUser;

public class BuyCryptoService {
    private static final int AMOUNT = 1;
    private static final int BUY_ALL = 2;

    private static final int BUY_ORDER = 3;
    private static final int CANCEL = 0;
    static double buyAmount;
    static double oldAmount;
    static double newAmount;
    static Scanner sc = new Scanner(System.in);
    public static void buyCrypto(Crypto crypto, Crypto crypto2) throws IOException {
        if (getWallet().get(crypto2).equals(0.0)) {
            System.out.println(red + "You don't have any " + crypto2.getCrypto() + " in your wallet !" + RESET);
            usermain();
        }
        else {
            System.out.println("1. Enter amount. ");
            System.out.println("2. Buy all." + buyAllinfo(crypto,crypto2,getPrice(crypto,crypto2))) ;
            System.out.println("3. Buy Order.");
            System.out.println("3. Cancel.");
                switch (inputCheck(0,3)){
                    case AMOUNT -> enterAmount(crypto,crypto2);
                    case BUY_ALL -> buyAll(crypto,crypto2);
                    case BUY_ORDER -> buyOrderUser(crypto,crypto2);
                    case CANCEL -> usermain();
                }
            }
        }
    static String buyAllinfo(Crypto crypto, Crypto crypto2,double price){
        return GREEN + " (buy " + RESET + YELLOW + getWallet().get(crypto2)/price + " " + crypto.getCrypto() + RESET + GREEN + " for " + RESET +
                YELLOW + getWallet().get(crypto2) + " " + crypto2.getCrypto() + RESET + GREEN + ")" + RESET ;
    }
    static void buyAll(Crypto crypto , Crypto crypto2) throws IOException {
        buyAmount = getWallet().get(crypto2) / getPrice(crypto,crypto2);
        if (getWallet().containsKey(crypto)){
            oldAmount = getWallet().get(crypto);
            newAmount = oldAmount + buyAmount;
            getWallet().replace(crypto,oldAmount,newAmount);
        }
        else {
            getWallet().put(crypto,buyAmount);
        }
        UUID id = UUID.randomUUID();
        addBuyHistoryData(crypto, crypto2,getPrice(crypto,crypto2),buyAmount, String.valueOf(id));
        getWallet().replace(crypto2,0.0);
        walletToFile(getAccountlogged().getUsername());
        System.out.println(GREEN + "Congratulation on your purchase " + RESET + YELLOW + buyAmount + " " +
                crypto.getCrypto() + RESET + GREEN  + " at exchange rate is " + RESET + YELLOW + getPrice(crypto,crypto2) + RESET +"\n");
        System.out.println("Press any key to continue");
        if (sc.hasNext()) {
            System.out.println(" ");
            usermain();
        }
    }
    static void enterAmount(Crypto crypto, Crypto crypto2) throws IOException {
        UUID newID = UUID.randomUUID();
            System.out.println("type " + red + "cancel" + RESET + " to cancel this transaction");
            System.out.println("Enter amount of " + YELLOW + crypto.getCrypto() + RESET + " you want to buy :");
            while (!sc.hasNextDouble()) {
                System.out.println(red + "invalid input" + RESET);
                if (sc.next().equals("cancel")) {
                    System.out.println(" ");
                    usermain();
                    break;
                }
            }
            buyAmount = sc.nextDouble();
            if (getWallet().get(crypto2) / getPrice(crypto,crypto2) < buyAmount) {
                System.out.println(red + "NOT ENOUGH " + crypto2.getCrypto() + RESET);
                buyCrypto(crypto, crypto2);
            } else {
                if (getWallet().containsKey(crypto)) {
                    oldAmount = getWallet().get(crypto);
                    newAmount = oldAmount + buyAmount;
                    getWallet().replace(crypto, oldAmount, newAmount);
                    oldAmount = getWallet().get(crypto2);
                    newAmount = oldAmount - (buyAmount * getPrice(crypto,crypto2));
                    getWallet().replace(crypto2, oldAmount, newAmount);
                    walletToFile(getAccountlogged().getUsername());
                    System.out.println(GREEN + "Congratulation on your purchase " + RESET + YELLOW + buyAmount + " " +
                            crypto.getCrypto() + RESET + GREEN + " at exchange rate is " + RESET + YELLOW + getPrice(crypto,crypto2) + RESET + "\n");
                } else {
                    getWallet().put(crypto, buyAmount);
                    oldAmount = getWallet().get(crypto2);
                    newAmount = oldAmount - (buyAmount * getPrice(crypto,crypto2));
                    getWallet().replace(crypto2, oldAmount, newAmount);
                    walletToFile(getAccountlogged().getUsername());
                    System.out.println(GREEN + "Congratulation on your purchase " + RESET + YELLOW + buyAmount + " " +
                            crypto.getCrypto() + RESET + GREEN + " at exchange rate is " + RESET + YELLOW + getPrice(crypto,crypto2) + RESET + "\n");
                }
                addBuyHistoryData(crypto, crypto2, getPrice(crypto,crypto2), buyAmount, String.valueOf(newID));
                usermain();
            }
    }
    static void addBuyHistoryData(Crypto crypto, Crypto crypto2, double price, double buyamount, String id ) throws IOException {
        String path = "Data/User/" + getAccountlogged().getUsername();
        now = LocalDateTime.now();
        FileWriter fileWriter = new FileWriter(path + "/buysellhistory.txt",true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("confirm,buy," + now + "," + crypto.getCrypto() + "," + crypto2.getCrypto() + "," + price + "," + buyamount + "," + id + "\n");
        bufferedWriter.close();
        fileWriter.close();
    }
}
