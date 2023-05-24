package Crypto;

import java.util.HashSet;

import static Menu.WelcomePage.*;

public class Crypto {
    private String crypto;
    private double amount;
    private static HashSet<Crypto> cryptos = new HashSet<>();

    public Crypto(String crypto, double amount) {
        this.crypto = crypto;
        this.amount = amount;
    }
    public Crypto(String crypto){
        this.crypto = crypto;
        this.amount = 1;
    }

    public String getCrypto() {
        return crypto;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return crypto + " : " + BLUE + amount + RESET;
    }
    public String toFile() {
        return crypto + ',' + amount;
    }

    public static HashSet<Crypto> getCryptos() {
        return cryptos;
    }

    public static void setCryptos(HashSet<Crypto> cryptos) {
        Crypto.cryptos = cryptos;
    }
}
