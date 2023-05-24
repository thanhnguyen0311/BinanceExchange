package Crypto;
import java.io.*;
import static Crypto.Crypto.getCryptos;

public class CryptoData {
    public static void getCryptoFromData() throws IOException {
        String line;
        FileReader fileReader = new FileReader("Data/cryptos.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while((line = bufferedReader.readLine()) != null){
            getCryptos().add(new Crypto(line,1));
        }
        bufferedReader.close();
        fileReader.close();
    }
    public static void extractCryptoToData() throws IOException {
        FileWriter fileWriter = new FileWriter("Data/cryptos.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(Crypto crypto : getCryptos()){
         bufferedWriter.write(crypto.getCrypto() + "\n");
        }
        bufferedWriter.close();
        fileWriter.close();
    }
}
