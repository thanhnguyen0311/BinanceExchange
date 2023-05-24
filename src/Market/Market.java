package Market;

import Crypto.Crypto;

import java.util.HashMap;

import static Crypto.CryptoService.getBTCcrypto;
import static Crypto.CryptoService.getUSDTcrypto;

public class Market {
    private static HashMap<Crypto, Double> USDTmarket = new HashMap<>();
    private static HashMap<Crypto, Double> BTCmarket = new HashMap<>();

    public static HashMap<Crypto, Double> getBTCmarket() {
        return BTCmarket;
    }
    public static void setBTCmarket(HashMap<Crypto, Double> BTCmarket) {
        Market.BTCmarket = BTCmarket;
    }

    public static HashMap<Crypto, Double> getUSDTmarket() {
        return USDTmarket;
    }

    public static void setUSDTmarket(HashMap<Crypto, Double> USDTmarket) {
        Market.USDTmarket = USDTmarket;
    }
}
