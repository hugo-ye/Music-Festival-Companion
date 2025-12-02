package use_case.signup;

public interface SignupOutputBoundary {
    /**
     * Prepares the success view after a successful login attempt.
     * @param outputData the output data
     */
    void prepareSuccessView(SignupOutputData outputData);

    /**
     * Prepares the failure view after a successful login attempt.
     * @param errorMessage the output data
     */
    void prepareFailView(String errorMessage);
}
