package Account.AccService;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

import static Account.AccService.UserRequestionService.*;
import static Account.Wallet.Wallet.getWallet;
import static Account.Wallet.WalletService.getWalletData;
import static Account.Wallet.WalletService.walletToFile;
import static Crypto.CryptoService.getCryptofromName;
import static Menu.LoginMain.Admin.AdminMain.adminMain;
import static Menu.WelcomePage.*;


public class AdminService {
    private static final int CONFIRM = 1;
    private static final int CANCEL = 2;
    private static String formatDateTime;
    private static LocalDateTime localDateTime;
    private static int inputlist;

    public static HashMap<Integer, String []> listRequest = new HashMap<>();
    private static final String pathRequest = "Data/request.txt";
    private static String [] arr;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static void getRequestTransactionData(){
        try {
            FileReader fileReader = new FileReader(pathRequest);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int i = 1;
            while((line = bufferedReader.readLine()) != null){
                    arr = line.split(",");
                    listRequest.put(i, arr);
                    i++;
            }
            bufferedReader.close();
            fileReader.close();
            listRequest.forEach((k, v) -> {
                localDateTime = LocalDateTime.parse(v[2]);
                formatDateTime = localDateTime.format(formatter);
                if(v[0].equals("confirm")) {
                    System.out.println(WHITE + k + ". " + formatDateTime + RESET + " " + GREEN + v[0] + RESET
                            + WHITE + " " + v[1] + " from " + v[5] + " " + RESET + YELLOW + v[3] + " " + v[4] + RESET );
                }
                if(v[0].equals("pending")) {
                    System.out.println(WHITE + k + ". " + formatDateTime + RESET + " " + YELLOW + v[0] + RESET
                            + WHITE + " " + v[1] + " from " + v[5] + " " + RESET + YELLOW + v[3] + " " + v[4] + RESET );
                }
                if(v[0].equals("cancel")) {
                    System.out.println(WHITE + k + ". " + formatDateTime + RESET + " " + red + v[0] + RESET
                            + WHITE + " " + v[1] + " from " + v[5] + " " + RESET + YELLOW + v[3] + " " + v[4] + RESET );
                }
            });
            System.out.println("0. BACK");
            if(arr != null) {
                inputlist = inputCheck(0, i);
                if (inputlist == 0) adminMain();
                checkRequest(listRequest.get(inputlist));
            }
            else{
                System.err.println("NO DATA");
                adminMain();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    static void ExtractRequestData(){
        try {
            FileWriter fileWriter = new FileWriter(pathRequest);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            listRequest.forEach((k, v) -> {
                try {
                    bufferedWriter.write(v[0]+","+v[1]+","+v[2]+","+v[3]+","+v[4]+","+v[5]+ "," + v[6] + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    static void checkRequest(String [] arr) throws IOException {
            localDateTime = LocalDateTime.parse(arr[2]);
            formatDateTime = localDateTime.format(formatter);
            System.out.println(WHITE + "DATE : " + formatDateTime + RESET);
            if (arr[0].equals("cancel")) System.out.println(WHITE + "STATUS : " + RESET + red + arr[0] + RESET);
            if (arr[0].equals("pending")) System.out.println(WHITE + "STATUS : " + RESET + YELLOW + arr[0] + RESET);
            if (arr[0].equals("confirm")) System.out.println(WHITE + "STATUS : " + RESET + GREEN + arr[0] + RESET);
            System.out.println(WHITE + "TRANSACTION : " + arr[1] + RESET);
            System.out.println(WHITE + "USERNAME : " + arr[5] + RESET);
            System.out.println(WHITE + "Amount : " + RESET + YELLOW + arr[3] + " " + arr[4] + RESET);
            System.out.println(WHITE + "ID : " + arr[6] + RESET);

        if(arr[0].equals("pending")) {
            System.out.println("1. CONFIRM");
            System.out.println("2. CANCEL");
            switch (inputCheck(1, 2)) {
                case CONFIRM -> {
                    adminConfirmRequest(arr);
                    System.out.println(GREEN + "Success" + RESET);
                    adminMain();
                }
                case CANCEL -> adminMain();
            }
        }
        else if (arr[0].equals("confirm")){
            System.out.println(WHITE + "This transaction have been confirmed!" + RESET);
            System.out.println("press any key to continue");
            Scanner sc = new Scanner(System.in);
            if(sc.hasNext()){
                System.out.println(" ");
                getRequestTransactionData();
            }
        }
    }
    public static void adminConfirmRequest(String [] arr){
            check = true;
            if(arr[1].equals("deposit")) {
                getWalletData(arr[5]);
                getWallet().forEach((k, v) -> {
                    if (k.getCrypto().equals(arr[4])) {
                        getWallet().replace(k, v, v + Double.parseDouble(arr[3]));
                        check = false;
                    }
                });
                if (check) {
                    getWallet().put(getCryptofromName(arr[4]), Double.parseDouble(arr[3]));
                }
                walletToFile(arr[5]);
            }
                String[] arrlist = listRequest.get(inputlist);
                arrlist[0] = "confirm";
                listRequest.put(inputlist, arrlist);
                ExtractRequestData();
                userGetTransactionData(arr[5]);
                for(String [] array : getTransactionUser()){
                    if(array[6].equals(arr[6])){
                        array[0] = "confirm";
                    }
                }
            System.out.println(GREEN + "SUCCESS" + RESET);
            System.out.println("Press any key to continue");
            if (sc.hasNext()) {
                System.out.println(" ");
                ExtractTransactionUserData(arr[5]);
            }
    }
}
