package liyu.test.springboot.init.entity;

public class User {
    private String username;
    private String password;
    private int enable = 1;

    public User(){}
    public User(String username,String password){
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getEnable() {
        return this.enable;
    }
}
