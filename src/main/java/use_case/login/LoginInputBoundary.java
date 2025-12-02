package use_case.login;

public interface LoginInputBoundary {
    /**
     * Executes the login use case using the provided login input data.
     * This method checks whether the username exists, verifies the password,
     * and either triggers a failure view or sets the current user and triggers
     * a success view.
     *
     * @param inputData the input data containing the username and password to validate
     */
    void execute(LoginInputData inputData);
}
