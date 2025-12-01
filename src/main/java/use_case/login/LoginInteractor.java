package use_case.login;

import entity.User;

/**
 * Interactor for the Login use case.
 * The interactor validates user credentials, sets the currently
 * logged in user, and calls either the prepareFailView,
 * if the user does not exist or the password is wrong,
 * or prepareSuccessView, if the user exists and the password isn't wrong.
 * 
 */
public class LoginInteractor implements LoginInputBoundary{
    private final LoginUserDataAccessInterface loginDataAccessObject;
    private final LoginOutputBoundary loginPresenter;
    private final LoginSessionDataAccessInterface currentUserDataAccessObject;

    public LoginInteractor(LoginUserDataAccessInterface loginDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary,
                           LoginSessionDataAccessInterface loginSessionDataAccessInterface) {
        this.loginDataAccessObject = loginDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
        this.currentUserDataAccessObject= loginSessionDataAccessInterface;
    }

    /**
     * Executes the login use case using the provided login input data.
     * This method checks whether the username exists, verifies the password,
     * and either triggers a failure view or sets the current user and triggers
     * a success view.
     *
     * @param loginInputData the input data containing the username and password to validate
     */
    @Override
    public void execute(LoginInputData loginInputData){
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (!loginDataAccessObject.existsByUsername(username)){
            loginPresenter.prepareFailView("User does not exist.");
        }
        else {
            final User user = loginDataAccessObject.getByUsername(username);
            final String userPassword = user.getPassword();
            if (!userPassword.equals(password)){
                loginPresenter.prepareFailView("Incorrect password.");
            }
            else {
                currentUserDataAccessObject.setCurrentUser(user);
                LoginOutputData loginOutputData = new LoginOutputData(user.getUsername());
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
