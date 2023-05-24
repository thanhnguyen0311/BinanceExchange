package Account.AccService;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static Account.AccService.AccountService.*;
import static Account.AccService.AdminService.*;
import static Account.Account.getAccountlogged;
import static Account.Wallet.Wallet.getWallet;
import static Account.Wallet.WalletService.walletToFile;
import static Crypto.CryptoService.getCryptofromName;
import static Crypto.CryptoService.getUSDTcrypto;
import static Market.MarketService.getPrice;
import static Menu.LoginMain.User.UserMain.usermain;
import static Menu.WelcomePage.*;
import static Menu.WelcomePage.RESET;

public class UserRequestionService {
    private static final int BACK = 0 ;
    private static final int CANCEL = 1;
    private static LocalDateTime localDateTime;
    private static String formatDateTime;
    private static HashSet<String []> transactionUser = new HashSet<>();

    public static HashSet<String[]> getTransactionUser() {
        return transactionUser;
    }

    public static void setTransactionUser(HashSet<String[]> transactionUser) {
        UserRequestionService.transactionUser = transactionUser;
    }
    static Scanner sc = new Scanner(System.in);

    public static void userNewRequest(double amount, String coin , String transaction){
        try {
            UUID id = UUID.randomUUID();
            now = LocalDateTime.now();
            String [] arr;
            if(coin.equals("USDT"))
                arr  = new String[]{"pending", transaction, String.valueOf(now) , String.valueOf(amount), coin, String.valueOf(amount), String.valueOf(id)};
            else{
                arr  = new String[]{"pending", transaction, String.valueOf(now), String.valueOf(amount), coin,
                        String.valueOf(getPrice(getCryptofromName(coin),getUSDTcrypto())*amount), String.valueOf(id)};
            }
            AddNewRequestfromUser(arr);
            getTransactionUser().add(arr);
            ExtractTransactionUserData(getAccountlogged().getUsername());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    static void AddNewRequestfromUser(String [] arr){
        try{
            arr[5] = getAccountlogged().getUsername();
            String RequestPath = "Data";
            FileWriter fileWriter = new FileWriter(RequestPath + "/request.txt",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + arr[3] + "," + arr[4] + "," + arr[5] + "," + arr[6] + "\n");
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void userGetTransactionData(String username){
        transactionUser.clear();
        String path = "Data/user/" + username;
        try {
            String line;
            String [] arr;
            FileReader fileReader = new FileReader(path + "/transaction.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                arr = line.split(",");
                transactionUser.add(arr);
            }
            bufferedReader.close();
            fileReader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void ExtractTransactionUserData(String username){
        String path = "Data/user/" + username;
        try {
            FileWriter fileWriter = new FileWriter(path + "/transaction.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String[] arr : transactionUser) {
                bufferedWriter.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + arr[3] + "," + arr[4] + "," + arr[5] + "," + arr[6] + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void transactionHistory(){
        String [] request;
        int i = getTransactionUser().size();
        HashMap <Integer,String[]> listPendingRequest = new HashMap<>();
        for (String [] arr : getTransactionUser()){
            listPendingRequest.put(i,arr);
            i--;
        }
        SortedSet<Integer> keys = new TreeSet<> (listPendingRequest.keySet());
        for (Integer key : keys){
            request = listPendingRequest.get(key);
            localDateTime = LocalDateTime.parse(request[2]);
            formatDateTime = localDateTime.format(formatter);
            if(request[0].equals("confirm")) {
                System.out.println(WHITE + key + ". " + formatDateTime + RESET + " " + GREEN + request[0] + RESET
                        + WHITE + " " + request[1] + RESET + " " + YELLOW + request[3] + " " + request[4] + RESET);
            }
            if(request[0].equals("cancel")) {
                System.out.println(WHITE + key + ". " + formatDateTime + RESET + " " + red + request[0] + RESET
                        + WHITE + " " + request[1] + RESET + " " + YELLOW + request[3] + " " + request[4] + RESET);
            }
            if(request[0].equals("pending")) {
                System.out.println(WHITE + key + ". " + formatDateTime + RESET + " " + YELLOW + request[0] + RESET
                        + WHITE + " " + request[1] + RESET + " " + YELLOW + request[3] + " " + request[4] + RESET);
            }
        }

        System.out.println("0. Back");
        int input = inputCheck(0,listPendingRequest.size());
        if (input == BACK) usermain();
        userRequestEdit(listPendingRequest.get(input));
    }

    static void userRequestEdit(String [] arr) {
        localDateTime = LocalDateTime.parse(arr[2]);
        formatDateTime = localDateTime.format(formatter);
        System.out.println(WHITE + "DATE : " + formatDateTime + RESET);
        if (arr[0].equals("cancel")) System.out.println(WHITE + "STATUS : " + RESET + red + arr[0] + RESET);
        if (arr[0].equals("pending")) System.out.println(WHITE + "STATUS : " + RESET + YELLOW + arr[0] + RESET);
        if (arr[0].equals("confirm")) System.out.println(WHITE + "STATUS : " + RESET + GREEN + arr[0] + RESET);
        System.out.println(WHITE + "TRANSACTION : " + arr[1] + RESET);
        System.out.println(WHITE + "Amount : " + RESET + YELLOW + arr[3] + " " + arr[4] + RESET);
        System.out.println(WHITE + "ID : " + arr[6] + RESET);
        if(arr[0].equals("pending")) {
            System.out.println("1. Cancel this transaction.");
            System.out.println("0. Back");
            switch (inputCheck(0, 1)) {
                case CANCEL -> CancelTransaction(arr);
                case BACK -> transactionHistory();
            }
        }
        else{
            System.out.println("Press any key to continue");
            if (sc.hasNext()) {
                System.out.println(" ");
                transactionHistory();
            }
        }
    }
    static void CancelTransaction(String [] arr){
        for(String [] array : transactionUser){
            if(arr[6].equals(array[6])) array[0] = "cancel";
        }
        listRequest.forEach((k,v) -> {
            if(v[6].equals(arr[6])) v[0] = "cancel";
        });
        if (arr[1].equals("withdraw")){
            double oldAmount = getWallet().get(getCryptofromName(arr[4]));
        getWallet().replace(getCryptofromName(arr[4]),oldAmount, oldAmount + Double.parseDouble(arr[3]));
        walletToFile(getAccountlogged().getUsername());
        }
        ExtractTransactionUserData(getAccountlogged().getUsername());
        ExtractRequestData();
        System.out.println(GREEN + "SUCCESS" + RESET);
        System.out.println("Press any key to continue");
        if (sc.hasNext()) {
            System.out.println(" ");
            transactionHistory();
        }
    }
}
