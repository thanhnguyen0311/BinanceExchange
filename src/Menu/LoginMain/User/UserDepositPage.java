package Menu.LoginMain.User;


import java.util.Scanner;

import static Account.AccService.UserRequestionService.userNewRequest;
import static Menu.LoginMain.User.UserMain.usermain;
import static Menu.WelcomePage.*;

public class UserDepositPage {
    private static final int CANCEL = 3;
     private static Scanner sc = new Scanner(System.in);
    static void userDepositPage(){
        System.out.println("Choose a Crypto you want to Deposit");
        System.out.println("1. USDT");
        System.out.println("2. BTC");
        System.out.println("3. Cancel");
        int input = inputCheck(1,3);
        if (input == CANCEL) {
            usermain();
        } else {
            System.out.println(WHITE + "Make sure you have contact us and pay. Momo 0937404039" + RESET);
            System.out.println("Enter amount");
            while (!sc.hasNextDouble()) {
                System.out.println(red + "invalid input" + RESET);
                sc.next();
            }
            double inputDeposit = sc.nextDouble();
            if (input == 1) userNewRequest(inputDeposit, "USDT", "deposit");
            if (input == 2) userNewRequest(inputDeposit, "BTC", "deposit");
            usermain();
        }
    }
}
