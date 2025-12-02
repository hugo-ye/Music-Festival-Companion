package use_case.logout;

public interface LogoutOutputBoundary {
    /**
     * Prepares the success view after a successful login attempt.
     * @param outputData the output data
     */
    void prepareSuccessView(LogoutOutputData outputData);

    /**
     * Prepares the failure view after a successful login attempt.
     * @param errorMessage the output data
     */
    void prepareFailView(String errorMessage);
}
