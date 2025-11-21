package use_case.signup;

public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData outputData);

    void prepareFailView(String errorMessage);
}
