package TradeService;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;

import static Account.AccService.AdminService.formatter;
import static Account.Account.getAccountlogged;
import static Account.Wallet.Wallet.getWallet;
import static Account.Wallet.WalletService.*;
import static Crypto.CryptoService.getCryptofromName;
import static Market.MarketService.getPrice;
import static Menu.LoginMain.User.UserMain.usermain;
import static Menu.WelcomePage.*;

public class BuySellHistoryService {
    private static HashSet<String[]> history = new HashSet<>();
    private static String path;
    private static String pathorder;
    private static final int CANCEL = 1;
    private static final int BACK = 0;
    private static LocalDateTime localDateTime;
    private static String formatDateTime;

    public static HashSet<String[]> getHistory() {
        return history;
    }

    public static void setHistory(HashSet<String[]> history) {
        BuySellHistoryService.history = history;
    }

    private static HashSet <String [] > orders = new HashSet<>();

    public static HashSet<String[]> getOrders() {
        return orders;
    }

    public static void setOrders(HashSet<String[]> orders) {
        BuySellHistoryService.orders = orders;
    }

    public static void getBuySellDataUser(String username){
        history.clear();
        path = "Data/user/" + username;
        try {
            String line;
            String [] arr;
            FileReader fileReader = new FileReader(path + "/buysellhistory.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                arr = line.split(",");
                history.add(arr);
            }
            bufferedReader.close();
            fileReader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void ExtractBuySellDataUser(String username){
        path = "Data/user/" + username;
        try {
            FileWriter fileWriter = new FileWriter(path + "/buysellhistory.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(String [] arr : history){
                bufferedWriter.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + arr[3] + "," + arr[4] + "," + arr[5] + "," + arr[6] + "," + arr[7] + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void getBuySellMenu(){
        getBuySellDataUser(getAccountlogged().getUsername());
        int i = 0;
        Locale locale = new Locale("en", "EN");
        String pattern = "###,###.######";
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat
                .getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);
        HashMap <Integer,String[]> listHistory = new HashMap<>();
        for (String [] arr : history){
            localDateTime = LocalDateTime.parse(arr[2]);
            formatDateTime = localDateTime.format(formatter);
            i++;
            if(arr[0].equals("confirm")) {
                System.out.println(WHITE + i + ". " + formatDateTime + RESET + " " + GREEN + arr[0] + RESET
                        + WHITE + " " + arr[1] + " " + arr[3] + RESET + "/" + arr[4] + " Amount: " + YELLOW + decimalFormat.format(Double.parseDouble(arr[6]))  + RESET + " Price : " + BLUE + arr[5] + RESET );
            }
            if(arr[0].equals("pending")) {
                System.out.println(WHITE + i + ". " + formatDateTime + RESET + " " + YELLOW + arr[0] + RESET
                        + WHITE + " " + arr[1] + " " + arr[3] + RESET + "/" + arr[4] + " Amount: " + YELLOW + decimalFormat.format(Double.parseDouble(arr[6]))  + RESET + " Price : " + BLUE + arr[5] + RESET );
            }
            if(arr[0].equals("cancel")) {
                System.out.println(WHITE + i + ". " + formatDateTime + RESET + " " + red + arr[0] + RESET
                        + WHITE + " " + arr[1] + " " + arr[3] + RESET + "/" + arr[4] + " Amount: " + YELLOW + decimalFormat.format(Double.parseDouble(arr[6]))  + RESET + " Price : " + BLUE + arr[5] + RESET);
            }
            listHistory.put(i,arr);
        }
        System.out.println("0. Back");
        int input = inputCheck(0,listHistory.size());
        if (input == 0) usermain();
        OrderEdit(listHistory.get(input));
    }
    static void OrderEdit(String [] arr){
        localDateTime = LocalDateTime.parse(arr[2]);
        formatDateTime = localDateTime.format(formatter);
        System.out.println(WHITE + "DATE : " + formatDateTime + RESET);
        if (arr[0].equals("cancel")) System.out.println(WHITE + "STATUS : " + RESET + red + arr[0] + RESET);
        if (arr[0].equals("pending")) System.out.println(WHITE + "STATUS : " + RESET + YELLOW + arr[0] + RESET);
        if (arr[0].equals("confirm")) System.out.println(WHITE + "STATUS : " + RESET + GREEN + arr[0] + RESET);
        System.out.println(WHITE + "TRANSACTION : " + arr[1] +  " " + arr[3]  + RESET + "/" + arr[4]);
        System.out.println(WHITE + "Amount : " + RESET + YELLOW + arr[6] + " "  + RESET);
        System.out.println(WHITE + "Price : " + BLUE + arr[5] + " "  + RESET);
        System.out.println(WHITE + "ID : " + arr[7] + RESET);
        if(arr[0].equals("pending")) {
            System.out.println("1. Cancel this transaction.");
            System.out.println("0. Back");
            switch (inputCheck(0, 1)) {
                case CANCEL -> cancelOrder(arr);
                case BACK -> getBuySellMenu();
            }
        }
        else{
            System.out.println("Press any key to continue");
            Scanner sc = new Scanner(System.in);
            if (sc.hasNext()) {
                System.out.println(" ");
                getBuySellMenu();
            }
        }
    }
    static void cancelOrder(String [] arr){
        double oldAmount;
        for(String [] array : getOrders()){
            if (array[7].equals(arr[7])){
                getOrders().remove(array);
            }
        }
        ExtractOrderstoFile();
        for(String [] array : history){
            if(array[7].equals(arr[7])){
                array[0] = "cancel";
                if(array[1].equals("buy")){
                    oldAmount = getWallet().get(getCryptofromName(array[4]));
                    getWallet().replace(getCryptofromName(array[4]),oldAmount,oldAmount + (Double.parseDouble(array[5]) * Double.parseDouble(array[6])));
                }
                else{
                    oldAmount = getWallet().get(getCryptofromName(array[3]));
                    getWallet().replace(getCryptofromName(array[3]),oldAmount, oldAmount - Double.parseDouble(arr[6]));
                }
                System.out.println("SUCCESS");
                walletToFile(getAccountlogged().getUsername());
                ExtractBuySellDataUser(getAccountlogged().getUsername());
                getBuySellMenu();
            }
        }
    }
    public static void getOrdersfromData(){
        getOrders().clear();
        try{
            String [] arr;
            String line;
            String path = "Data/";
            FileReader fileReader = new FileReader(path + "buysellorders.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                arr = line.split(",");
                getOrders().add(arr);
            }
            bufferedReader.close();
            fileReader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void ExtractOrderstoFile(){
        try{
            String path = "Data/";
            FileWriter fileWriter = new FileWriter(path + "buysellorders.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String [] arr : getOrders()) {
                bufferedWriter.write(arr[0] + "," + arr[1] + "," + arr[2] + "," + arr[3] + "," + arr[4] + "," + arr[5] + "," + arr[6] + "," + arr[7] + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void CheckBuySellOrder() throws IOException {
        for (String [] arr : getOrders()){
            if(arr[0].equals("buy")){
                if(getPrice(getCryptofromName(arr[2]),getCryptofromName(arr[3])) <= Double.parseDouble(arr[4])){
                    EditWallet(getCryptofromName(arr[2]),Double.parseDouble(arr[5]),arr[6]);
                    confirmOrder(arr[7]);
                    confirmOrderUser(arr[7]);
                }
            }
            else{
                if(getPrice(getCryptofromName(arr[2]),getCryptofromName(arr[3])) >= Double.parseDouble(arr[4])){
                    EditWallet(getCryptofromName(arr[2]),-Double.parseDouble(arr[5]),arr[6]);
                    confirmOrder(arr[7]);
                    confirmOrderUser(arr[7]);
                }
            }
        }
    }
    static void confirmOrder(String id){
        for(String[] arr : orders){
            if(arr[7].equals(id)){
                orders.remove(arr);
            }
        }
        ExtractOrderstoFile();
    }
    static void confirmOrderUser(String id){
        for(String[] arr : orders){
            if(arr[7].equals(id)){
                pathorder = "Data/user/" + arr[6];
            }
        }
        try {
            HashSet <String[]> listOrderUser = new HashSet<>();
            String line;
            String [] arr;
            FileReader fileReader = new FileReader(pathorder + "/buysellhistory.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                arr = line.split(",");
                listOrderUser.add(arr);
            }
            bufferedReader.close();
            fileReader.close();
            FileWriter fileWriter = new FileWriter(pathorder + "/buysellhistory.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(String[] array : listOrderUser){
                if(array[7].equals(id)){
                    array[0] = "confirm";
                }
                bufferedWriter.write(array[0] + "," + array[1] + "," + array[2] + "," + array[3] + "," + array[4] + "," + array[5] + "," + array[6] + "," + array[7] + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
