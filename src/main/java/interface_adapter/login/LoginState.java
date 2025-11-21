package interface_adapter.login;

public class LoginState {
    private String username;
    private String password;
    private String errorMessage = "";

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
