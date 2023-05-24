package Menu;

import Account.Account;
import Account.Admin;
import Account.UserAccount;

import java.io.IOException;
import java.util.Scanner;

import static Account.AccService.AccountService.*;
import static Account.AccService.UserRequestionService.userGetTransactionData;
import static Account.Account.getAccountlist;
import static Account.Account.getAccountlogged;
import static Account.Wallet.WalletService.getWalletData;
import static Menu.LoginMain.Admin.AdminMain.adminMain;
import static Menu.LoginMain.User.UserMain.usermain;
import static Menu.Register.passwordcheck;
import static Menu.WelcomePage.*;


public class Login {
    static Scanner sc = new Scanner(System.in);
    static String userinput;
    private static final int ENTER_AGAIN = 1;
    private static final int FORGOT = 2;
    private static final int BACK = 3;
    private static final int EXIT = 0;

    public static void login(){
        try {
                System.out.println("username: ");
                userinput = sc.next();
                System.out.println("password: ");
                String passinput = sc.next();
                if (loginCheck(userinput, passinput)) {
                    LogAcc(userinput);
                    System.out.println(GREEN + "You have logged in successfully!!!" + RESET);
                    check = false;
                    if (getAccountlogged() instanceof UserAccount) {
                        getWalletData(userinput);
                        userGetTransactionData(userinput);
                        System.out.println(WHITE + "✌ WELCOME BACK " + BLUE + getAccountlogged().getName() + "\uD83D\uDE00" + RESET);
                        usermain();
                    } else if (getAccountlogged() instanceof Admin) adminMain();
                }
                else {
                    System.out.println(red + "Login failed ! Username or Password do not match :(" + RESET);
                    System.out.println("1. Enter again...");
                    System.out.println("2. Forgot your password ");
                    System.out.println("3. Back ");
                    System.out.println("0. Exit ");
                    switch (inputCheck(0, 3)) {
                        case ENTER_AGAIN -> login();
                        case FORGOT -> forgotPass();
                        case BACK -> welcome();
                        case EXIT -> System.exit(0);
                    }
                }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    static void forgotPass() throws IOException {
        System.out.println("Enter your email to get new password :");
        String input = sc.next();
        String input2;
        if (emailFind(input)) {
            System.out.println("enter your new password :");
            input2 = sc.next();
            while(!passwordcheck(input)){
                input2 = sc.next();
            }
            for (Account account : getAccountlist()){
                if(account.getEmail().equals(input)) passUpdate(account, input2);
            }
            System.out.println("Successfully! Your password is updated");
            welcome();
        }
        System.out.println("NOT FOUND EMAIL");
        welcome();
    }

}
