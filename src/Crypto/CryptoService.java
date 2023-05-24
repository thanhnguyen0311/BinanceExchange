package Crypto;

import java.io.IOException;

import static Crypto.Crypto.*;
import static Crypto.CryptoData.extractCryptoToData;

public class CryptoService {
    public static void addCrypto(String name) throws IOException {
        if(!checkCryptoExist(name)) {
            System.out.println("SUCCESS! YOU HAVE ADDED A NEW CRYPTO");
            getCryptos().add(new Crypto(name,1));
            extractCryptoToData();
        }
    }
    public static boolean checkCryptoExist(String name){
        for(Crypto crypto : getCryptos()){
            if(crypto.getCrypto().equals(name)){
                return true;
            }
        }
        return false;
    }
    public static Crypto getUSDTcrypto(){
        for (Crypto crypto : getCryptos()){
            if(crypto.getCrypto().equals("USDT")) return crypto;
        }
        return new Crypto("USDT");
    }
    public static Crypto getBTCcrypto(){
        for (Crypto crypto : getCryptos()){
            if(crypto.getCrypto().equals("BTC")) return crypto;
        }
        return new Crypto("BTC");
    }
    public static Crypto getCryptofromName(String name){
        for (Crypto crypto : getCryptos()){
            if (crypto.getCrypto().equals(name))
                return crypto;
        }
        return new Crypto(name);
    }
}
