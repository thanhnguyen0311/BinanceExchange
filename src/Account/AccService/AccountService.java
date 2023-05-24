package Account.AccService;
import Account.*;

import java.io.*;
import java.time.LocalDateTime;

import static Account.Account.*;
import static Factory.AccFactory.*;

public class AccountService {
    public static LocalDateTime now;
    public static boolean loginCheck(String username, String password){
        for (Account account : getAccountlist()){
            if (account.getUsername().equals(username) && account.getPassword().equals(password))
                return true;
        }
        return false;
    }
    public static void addAccount(String role , String name, String username, String password, String email){
        Account account = getAccount(role,name,username,password,email);
        now = LocalDateTime.now();
        account.setJoinDate(now);
        account.setLastSeen(now);
        ExtractToFolder(account);
        getAccountlist().add(account);
        ExtractToList();
    }

    public static void importUserfromData() throws IOException {
        FileReader fileReader = new FileReader("Data/list.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while((line = bufferedReader.readLine()) != null)
        {
            getAccountlist().add(importAccount(line));
        }
        bufferedReader.close();
    }
    public static void ExtractToList(){
        try {
            FileWriter fileWriter = new FileWriter("Data/list.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Account account : getAccountlist()) {
                if (account instanceof UserAccount) {
                    bufferedWriter.write(((UserAccount) account).tofile() + "\n");
                }
                if (account instanceof Admin) {
                    bufferedWriter.write(((Admin) account).tofile() + "\n");
                }
            }
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void ExtractToFolder(Account account){
        try {
            String path;
            FileWriter fileWriter;
            BufferedWriter bufferedWriter;
            if (account instanceof UserAccount) {
                path = "Data/user/" + account.getUsername();
                File dir = new File(path);
                dir.mkdirs();
                fileWriter = new FileWriter(dir.getAbsolutePath() + "/INFO.txt");
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(((UserAccount) account).tofile() + "\n" + account.getLastSeen() + "\n" + account.getJoinDate());
                bufferedWriter.close();
                fileWriter.close();
                fileWriter = new FileWriter(path + "/wallet.txt");
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("USDT," + 0.0 + "\n");
                bufferedWriter.write("BTC," + 0.0);
                bufferedWriter.close();
                fileWriter.close();
            }
            if (account instanceof Admin) {
                path = "Data/admin/INFO.txt";
                fileWriter = new FileWriter(path);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(((Admin) account).tofile() + "\n");
                bufferedWriter.close();
                fileWriter.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Account importAccount(String line){
        String [] arr = line.split(",");
        return getAccount(arr[0], arr[1], arr[2], arr[3], arr[4]);
    }
    public static void passUpdate(Account account , String pass){
        account.setPassword(pass);
        ExtractToList();
        ExtractToFolder(account);
    }
    public static Account LogAcc(String username){
        for (Account account : getAccountlist()){
            if (account.getUsername().equals(username)){
                now = LocalDateTime.now();
                account.setLastSeen(now);
                return setAccount(account);
            }
        }
        return getAccountlogged();
    }
    public static boolean emailFind(String email){
        for (Account account : getAccountlist()){
            if (account.getEmail().equals(email))
                return true;
        }
        return false;
    }
}