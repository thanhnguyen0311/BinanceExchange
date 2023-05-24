package Account;
import java.time.LocalDateTime;

public class UserAccount extends Account{


    public UserAccount(String name, String username, String password, String email) {
        super(name, username, password, email);
    }
    public UserAccount() {
    }
    public UserAccount(UserBuilder userBuilder){
        this.setName(userBuilder.name);
        this.setUsername(userBuilder.username);
        this.setPassword(userBuilder.password);
        this.setEmail(userBuilder.email);
    }
    public UserAccount(String name) {
    }

    public String toString() {
        return "USER : " + this.getName()  + "$" +
                " | Username : " + this.getUsername() + "\n";
    }


    @Override
    public LocalDateTime getJoinDate() {
        return super.getJoinDate();
    }

    @Override
    public void setJoinDate(LocalDateTime joinDate) {
        super.setJoinDate(joinDate);
    }

    @Override
    public LocalDateTime getLastSeen() {
        return super.getLastSeen();
    }

    @Override
    public void setLastSeen(LocalDateTime lastSeen) {
        super.setLastSeen(lastSeen);
    }

    public String tofile() {
        return "user" + "," + this.getName() + "," + this.getUsername() + "," + this.getPassword() + "," + this.getEmail();
    }
    public static class UserBuilder extends Account{
        private String name;
        private String username;
        private String password;
        private String email;

        public UserBuilder(){
        }
        public UserBuilder name(String name){
            this.name = name;
            return this;
        }
        public UserBuilder username(String username){
            this.username = username;
            return this;
        }
        public UserBuilder password(String password){
            this.password = password;
            return this;
        }
        public UserBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserAccount build(){
            return new UserAccount(this);
        }
    }
}
