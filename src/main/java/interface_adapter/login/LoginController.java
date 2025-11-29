package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * Controller for the Login use case.
 * The {@link LoginController} takes in raw input from the UI, username and password, and converts them into
 * {@link LoginInputData} object and forwards it to the interactor for processing.
 */
public class LoginController {
    private final LoginInputBoundary loginInteractor;

    public LoginController(LoginInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    /**
     * Executes the login use case using the provided username and password.
     *
     * @param username the username entered by the user.
     * @param password the password entered by the user.
     */
    public void execute(String username, String password) {
        LoginInputData inputData = new LoginInputData(username, password);
        loginInteractor.execute(inputData);
    }

}
