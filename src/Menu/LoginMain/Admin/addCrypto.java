package Menu.LoginMain.Admin;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Crypto.CryptoService.addCrypto;
import static Menu.LoginMain.Admin.AdminMain.adminMain;
import static Menu.WelcomePage.*;

public class addCrypto {
    public static void AddCryptoMenu() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter Crypto name you want to get:");
            String input = sc.next();
            while(!checkCryptoinput(input)){
                System.out.println("type" + red + "cancel" + RESET + "to cancel");
                input = sc.next();
                if(input.equals("cancel")){
                    adminMain();
                    break;
                }
            }
            addCrypto(input);
            adminMain();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    static boolean checkCryptoinput(String name){
        if (name.length()>4){
            System.out.println("invalid crypto name");
            return false;
        }
        String regex = "^[a-zA-Z]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()){
            System.out.println("invalid crypto name");
            return false;
        }
        return true;
    }
}
