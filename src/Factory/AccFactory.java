package Factory;

import Account.*;
public class AccFactory {
    private AccFactory (){};
    public static Account getAccount(String role, String name, String username, String password, String email){
        if (role.equals("admin")){
            return new Admin(name,username,password,email);
        }
        else {
            return new UserAccount.UserBuilder()
                    .name(name)
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }
    }
}
