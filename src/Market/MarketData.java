package Market;

import java.io.*;

import static Crypto.CryptoService.*;
import static Market.Market.getBTCmarket;
import static Market.Market.getUSDTmarket;
import static Market.MarketService.getPrice;

public class   MarketData {
    static String [] arr;
    public static void ExtractUSDTMarketData() {
        String path = "Data/Market/USDT/";
        getUSDTmarket().forEach((k, v) -> {
            try {
                File f = new File(path + k.getCrypto() + "USDT.txt");
                if (!f.exists()) {
                    FileWriter fileWriter = new FileWriter(path+ k.getCrypto() + "USDT.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(v + "," + v);
                    bufferedWriter.close();
                    fileWriter.close();
                } else {
                    arr = new String[2];
                    FileReader fileReader = new FileReader(path+ k.getCrypto() + "USDT.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    arr = bufferedReader.readLine().split(",");
                    bufferedReader.close();
                    fileReader.close();
                    FileWriter fileWriter = new FileWriter(path+ k.getCrypto() + "USDT.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(v + "," + arr[0]);
                    bufferedWriter.close();
                    fileWriter.close();
                }
                } catch(IOException e){
                    e.printStackTrace();
                }
        });
    }
    public static void ExtractBTCMarketData() {
        String path = "Data/Market/BTC/";
        getBTCmarket().forEach((k, v) -> {
            try {
                File f = new File(path + k.getCrypto() + "BTC.txt");
                if (!f.exists()) {
                    FileWriter fileWriter = new FileWriter(path+ k.getCrypto() + "BTC.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(v + "," + v);
                    bufferedWriter.close();
                    fileWriter.close();
                } else {
                    arr = new String[2];
                    FileReader fileReader = new FileReader(path+ k.getCrypto() + "BTC.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    arr = bufferedReader.readLine().split(",");
                    bufferedReader.close();
                    fileReader.close();
                    FileWriter fileWriter = new FileWriter(path+ k.getCrypto() + "BTC.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(v + "," + arr[0]);
                    bufferedWriter.close();
                    fileWriter.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        });
    }
    public static void updateMarketfromData() throws IOException {
        String path = "Data/Market/USDT/";
        File file = new File(path);
        String[] arr = file.list();
        if (file.list() != null) {
            assert arr != null;
            for (String a : arr) {
                getUSDTmarket().put(getCryptofromName(a.substring(0,a.length() - 8)),
                        getPrice(getCryptofromName(a.substring(0,a.length()-8)),getUSDTcrypto()));
            }
        }
            path = "Data/Market/BTC/";
            file = new File(path);
            arr = file.list();
            if (arr != null) {
                for (String a : arr) {
                    getBTCmarket().put(getCryptofromName(a.substring(0,a.length() - 7)),
                            getPrice(getCryptofromName(a.substring(0,a.length() - 7)), getBTCcrypto()));
                }
            }
        }

    }