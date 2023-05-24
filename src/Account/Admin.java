package Account;

public class Admin extends Account{
    public Admin(String name, String username, String password, String email) {
        super(name, username, password, email);
    }

    public Admin() {
    }

    public String toString() {
        return "";
    }


    public String tofile() {
        return "admin" + "," + this.getName() + "," + this.getUsername() + "," + this.getPassword() + "," + this.getEmail() +",0" ;
    }
}
