package use_case.logout;

import entity.User;

public class LogoutInteractor implements LogoutInputBoundary{
    private final LogoutSessionDataAccessInterface currentUserDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutSessionDataAccessInterface logoutSessionDataAccessInterface, LogoutOutputBoundary logoutOutputBoundary) {
        this.currentUserDataAccessObject = logoutSessionDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        currentUserDataAccessObject.clearCurrentUser();

        final LogoutOutputData logoutOutputData = new LogoutOutputData();
        logoutPresenter.prepareSuccessView(logoutOutputData);

    }
}
