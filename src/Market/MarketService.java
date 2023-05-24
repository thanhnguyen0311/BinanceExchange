package Market;

import Crypto.Crypto;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import static Crypto.Crypto.getCryptos;
import static Crypto.CryptoService.*;
import static Market.Market.*;
import static Market.MarketData.*;
import static Menu.WelcomePage.*;

public class MarketService {
        public static boolean CheckCryptoPriceAvailable(Crypto crypto, Crypto crypto2){
            for(Crypto c : getUSDTmarket().keySet()){
                if(c.getCrypto().equals(crypto.getCrypto())) {
                    System.out.println(red + crypto.getCrypto() + "already exists in this market!" + RESET);
                    return false;
                }
            }
            try {
                String pricePath = "https://api.binance.com/api/v3/ticker/price?symbol=" + crypto.getCrypto() + crypto2.getCrypto();
                URL url = new URL(pricePath);
                URLConnection yc = url.openConnection();
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                line = bufferedReader.readLine();
                bufferedReader.close();
                String[] arr;
                arr = line.split(":");
                line = arr[arr.length - 1].substring(1, arr[arr.length - 1].length() - 2);
                if (line.equals("Invalid symbol.") || line.contentEquals("Illegal characters")) {
                    System.out.println(red + "CAN'T GET PRICE !!! PLEASE CHOOSE ANOTHER CRYPTO" + RESET);
                    return false;
                }
            } catch (Exception e) {
                System.out.println(red + "CAN'T GET PRICE !!! PLEASE CHOOSE ANOTHER CRYPTO" + RESET);
                return false;
            }
        return true;
    }

    public static void addCryptoToUSDTMarket(Crypto crypto) throws IOException {
        for (Crypto c : getCryptos()){
            if(c.getCrypto().equals(crypto.getCrypto())){
                if(CheckCryptoPriceAvailable(c,getUSDTcrypto())) {
                    getUSDTmarket().put(c, getPrice(crypto, getUSDTcrypto()));
                }
            }
        }
        ExtractUSDTMarketData();
    }

    public static void addCryptoToBTCMarket(Crypto crypto) throws IOException {
        for (Crypto c : getCryptos()){
            if(c.getCrypto().equals(crypto.getCrypto())){
                if(CheckCryptoPriceAvailable(c,getBTCcrypto())) {
                    getBTCmarket().put(c, getPrice(crypto, getBTCcrypto()));
                }
            }
        }
        ExtractBTCMarketData();
    }
    public static double getPrice(Crypto crypto,Crypto crypto2) throws IOException {
        try {
            String pricePath = "https://api.binance.com/api/v3/ticker/price?symbol=" + crypto.getCrypto() + crypto2.getCrypto();
            URL url = new URL(pricePath);
            URLConnection yc = url.openConnection();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            line = bufferedReader.readLine();
            bufferedReader.close();
            String[] arr;
            arr = line.split(":");
            line = arr[arr.length - 1].substring(1, arr[arr.length - 1].length() - 2);
            return Double.parseDouble(line);
        } catch (Exception e) {
            System.err.println("CAN NOT GET PRICE");
        }
        return 0;
    }
    public static void updatePrice5s(){
        getUSDTmarket().forEach((k, v) -> {
            try {
                getUSDTmarket().put(k,getPrice(k,getUSDTcrypto()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ExtractUSDTMarketData();
        getBTCmarket().forEach((k, v) -> {
            try {
                getBTCmarket().put(k,getPrice(k,getBTCcrypto()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ExtractBTCMarketData();
    }
}
