package tasks.second;

public class User {

    private String username;
    private String displayName;
    private String password;


    public User(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }
}
