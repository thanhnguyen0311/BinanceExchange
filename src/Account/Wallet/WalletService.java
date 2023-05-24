package Account.Wallet;

import Crypto.Crypto;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import static Account.Wallet.Wallet.getWallet;
import static Crypto.CryptoService.getCryptofromName;
import static Menu.WelcomePage.*;


public class WalletService {

    public static void walletToFile(String username) {
        try {
        String path = "Data/User/" + username;
        File dir = new File(path);
        dir.mkdirs();
        FileWriter fileWriter = new FileWriter(dir.getAbsolutePath() + "/wallet.txt");
            getWallet().forEach((k, v) -> {
            try {
                fileWriter.write(k.getCrypto() + "," + v + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
                fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void getWalletData(String username) {
        try {
            getWallet().clear();
            String path = "Data/User/" + username;
            File dir = new File(path);
            dir.mkdir();
            FileReader fileReader = new FileReader(dir.getAbsolutePath() + "/wallet.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] arr = line.split(",");
                getWallet().put(getCryptofromName(arr[0]), Double.parseDouble(arr[1]));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void showWallet(){
//        getWallet().clear();
//        getWalletData(getAccountlogged().getUsername());
        Locale locale = new Locale("en", "EN");
        String pattern = "###,###.######";
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat
                .getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);
        String leftAlignFormat =BLUE + "%-5s " + RESET + YELLOW + "  %-5s " + RESET + "%n";
            getWallet().forEach((k, v) -> {
                    if(k.getCrypto().equals("USDT")) {
                        System.out.format(leftAlignFormat, k.getCrypto(),decimalFormat.format(v));
                    }
                    else {
                        System.out.format(leftAlignFormat, k.getCrypto(),  decimalFormat.format(v));
                    }
            });
        }
    public static void EditWallet(Crypto crypto, double amount, String username){
        try{
            String path = "Data/User/" + username;
            HashMap<Crypto,Double> listWallet = new HashMap<>();
            String line;
            String [] arr;
            double newAmount;
            FileReader fileReader = new FileReader(path+ "/wallet.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                arr = line.split(",");
                listWallet.put(getCryptofromName(arr[0]),Double.parseDouble(arr[1]));
            }
            bufferedReader.close();
            fileReader.close();
            if(listWallet.containsKey(crypto)) {
                for (Crypto crypto1 : listWallet.keySet()) {
                    if (crypto.getCrypto().equals(crypto1.getCrypto())) {
                        newAmount = listWallet.get(crypto1) + amount;
                        listWallet.replace(crypto1, listWallet.get(crypto1), newAmount);
                    }
                }
            }
            else{
                listWallet.put(crypto,amount);
            }

            FileWriter fileWriter = new FileWriter(path+ "/wallet.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            listWallet.forEach((k,v) ->{
                try {
                    bufferedWriter.write(k.getCrypto() + "," + v + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            bufferedWriter.close();
            fileWriter.close();
//            if(getAccountlogged().getUsername().equals(username)){
//                getWallet().clear();
//                getWalletData(username);
//            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
