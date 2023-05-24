package Account.Wallet;

import Crypto.Crypto;

import java.util.HashMap;

public class Wallet {
    private static HashMap< Crypto, Double> wallet = new HashMap<>();

    public Wallet() {
    }

    public static HashMap<Crypto, Double> getWallet() {
        return wallet;
    }

    public static void setWallet(HashMap<Crypto, Double> wallet) {
        Wallet.wallet = wallet;
    }
}
