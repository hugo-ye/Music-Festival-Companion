package use_case.logout;

public interface LogoutOutputBoundary {
    /**
     * Prepares the success view after a successful login attempt.
     * @param outputData the output data containing the username of the logged in user.
     */
    void prepareSuccessView(LogoutOutputData outputData);

    /**
     * Prepares the failure view after a successful login attempt.
     * @param errorMessage the output data containing the username of the logged in user.
     */
    void prepareFailView(String errorMessage);
}
