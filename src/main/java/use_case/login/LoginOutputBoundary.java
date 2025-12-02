package use_case.login;

import interface_adapter.login.LoginViewModel;

public interface LoginOutputBoundary {
    /**
     * Prepares the success view after a successful login attempt.
     * This method updates the LoginViewModel and SearchEventViewModel
     * @param outputData the output data containing the username of the logged in user.
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Prepares the fail view after an unsuccessful login attempt.
     * This method sets an error message in the {@link LoginViewModel}.
     *
     * @param errorMessage the error message describing why the login attempt failed.
     */
    void prepareFailView(String errorMessage);

}
