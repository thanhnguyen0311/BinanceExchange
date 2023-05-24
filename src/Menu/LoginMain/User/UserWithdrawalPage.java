package Menu.LoginMain.User;

import java.util.Scanner;

import static Account.AccService.UserRequestionService.userNewRequest;
import static Account.Account.getAccountlogged;
import static Account.Wallet.Wallet.getWallet;
import static Account.Wallet.WalletService.walletToFile;
import static Crypto.CryptoService.getUSDTcrypto;
import static Menu.LoginMain.User.UserMain.usermain;
import static Menu.WelcomePage.*;

public class UserWithdrawalPage {
    private static Scanner sc = new Scanner(System.in);

    static void withdrawalPage(){
            System.out.println(WHITE + "Your money will be deposited in your bank within T+1 business days of successful amount withdrawn. " + RESET);
            System.out.println("type " + red + "0" + RESET + " to cancel this transaction");
            System.out.println("Enter amount USDT:");
            while (!sc.hasNextDouble()) {
                System.out.println(red + "invalid input" + RESET);
                sc.next();
            }
            double inputWithdrawal = sc.nextDouble();
            if(inputWithdrawal == 0) usermain();
            double oldUSDT = getWallet().get(getUSDTcrypto());
            if (inputWithdrawal <= oldUSDT){
                double newUSDT = oldUSDT - inputWithdrawal;
                getWallet().replace(getUSDTcrypto(),oldUSDT,newUSDT);
                walletToFile(getAccountlogged().getUsername());
                userNewRequest(inputWithdrawal, "USDT", "withdraw");
                System.out.println(GREEN + "SUCCESS" + RESET);
            }
            else{
                System.out.println(red + "You dont have enough USDT to withdraw" + RESET);
            }
            System.out.println("Press any key to continue");
            if (sc.hasNext()) usermain();
        }
    }