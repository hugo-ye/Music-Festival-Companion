package interface_adapter.signup;

public class SignupState {
    private String username = "";
    private String password = "";
    private String repeatPassword = "";
    private String errorMessage = "";

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRepeatPassword() {
        return repeatPassword;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
