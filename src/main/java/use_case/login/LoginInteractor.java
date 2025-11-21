package use_case.login;

import entity.User;

public class LoginInteractor implements LoginInputBoundary{
    private final LoginUserDataAccessInterface loginDataAccessObject;
    private final LoginOutputBoundary loginPresenter;
    private final LoginSessionDataAccessInterface currentUserDataAccessObject;

    public LoginInteractor(LoginUserDataAccessInterface loginDataAccessInterface, LoginOutputBoundary loginOutputBoundary, LoginSessionDataAccessInterface loginSessionDataAccessInterface) {
        this.loginDataAccessObject = loginDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
        this.currentUserDataAccessObject= loginSessionDataAccessInterface;
    }

    public void execute(LoginInputData loginInputData){
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (!loginDataAccessObject.existsByUsername(username)){
            loginPresenter.prepareFailView("User does not exist.");
        }
        else {
            User user = loginDataAccessObject.getByUsername(username);
            String userPassword = user.getPassword();
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
