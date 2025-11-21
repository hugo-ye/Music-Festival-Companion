package use_case.logout;

public interface LogoutOutputBoundary {
    void prepareSuccessView(LogoutOutputData outputData);

    void prepareFailView(String errorMessage);
}
