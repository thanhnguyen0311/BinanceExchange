package Account;
import java.time.LocalDateTime;
import java.util.HashSet;

public abstract class Account {
    private String name;
    private String username;
    private String password;
    private String email;
    private LocalDateTime lastSeen;
    private LocalDateTime JoinDate;

    private static Account logged;
    private static HashSet<Account> accountList = new HashSet<>();

    public Account(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public Account(String username) {
        this.username = username;
    }
    public LocalDateTime getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        JoinDate = joinDate;
    }
    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Account(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static HashSet<Account> getAccountlist() {
        return accountList;
    }

    public void setAccountList(HashSet<Account> accountlist) {
        accountList = accountlist;
    }
    public static Account getAccountlogged(){
        return logged;
    }

    public static Account setAccount(Account account){
        return logged = account;
    }
}

