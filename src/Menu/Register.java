package Menu;
import Account.Account;
import Account.UserAccount;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Account.AccService.AccountService.*;
import static Account.Account.getAccountlist;
import static Menu.WelcomePage.*;

public class Register {
    static Scanner sc = new Scanner(System.in);
    private static final int AGREE = 1;
    private static final int RESET_ALL = 2;
    private static final int CANCEL = 3;
    public static void register(){
        UserAccount.UserBuilder userBuilder = new UserAccount.UserBuilder();
        System.out.println(YELLOW + "BECOME A BINANCE MEMBER " + RESET);
        System.out.println("Your Name: ");
        String nameinput = sc.nextLine();
        if (nameinput.equals("")) userBuilder.name("anonymous");
        userBuilder.name(nameinput);
        System.out.println("username: ");
        String userinput = sc.nextLine();
        while (!usernamecheck(userinput)) {
            System.out.println("enter again...");
            userinput = sc.nextLine();
        }
        userBuilder.username(userinput);
        System.out.println("password: ");
        String passinput = sc.nextLine();
        while (!passwordcheck(passinput)) {
            System.out.println("enter again...");
            passinput = sc.nextLine();
        }
        userBuilder.password(passinput);
        System.out.println("Email: ");
        String emailinput = sc.nextLine();
        while (!emailcheck(emailinput)) {
            System.out.println("enter again...");
            emailinput = sc.nextLine();
        }
        userBuilder.email(emailinput);
        System.out.println("Your name : " + nameinput);
        System.out.println("username : " + userinput);
        System.out.println("password : " + passinput);
        System.out.println("Email : " + emailinput);
        System.out.println("1. Agree and Register");
        System.out.println("2. Reset all");
        System.out.println("3. Cancel");
        switch (inputCheck(1, 3)) {
            case AGREE -> {
                UserAccount userAccount = userBuilder.build();
                ExtractToFolder(userAccount);
                getAccountlist().add(userAccount);
                ExtractToList();
                System.out.println(GREEN + "Congratulations, you have successfully registered!" + RESET);
                welcome();
            }
            case RESET_ALL -> register();
            case CANCEL -> welcome();
        }
    }
    public static boolean usernamecheck(String input){
        if (input.length()<=6){
            System.out.println("Enter a username longer than 6 characters");
            return false;
        }
        for (Account account : getAccountlist()){
            if (account.getUsername().equals(input)){
                System.out.println("Your username is already used.");
                return false;
            }
        }
        char [] arr = input.toCharArray();
        for (char c : arr){
            if (c == 32){
                System.out.println("Username cannot contain spaces");
                return false;
            }
        }
        return true;
    }
    public static boolean passwordcheck(String input){
        String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passcheck = pattern.matcher(input);
        if (passcheck.matches()){
            return true;
        }
        System.out.println("Enter a password at least 8 characters and max 20 characters");
        System.out.println("Password must contain at least one digit [0-9]");
        System.out.println("Password must contain at least one lowercase character [a-z]");
        System.out.println("Password must contain at least one uppercase character [A-Z]");
        System.out.println("Password must contain at least one special character");
        return false;
    }
    public static boolean emailcheck(String input){
        String EMAIL_REGEX =   "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher checkmail = pattern.matcher(input);
        if (checkmail.matches()){
            return true;
        }
        System.out.println("Invalid Email.");
        return false;
    }
}
