package use_case.logout;

public class LogoutInteractor implements LogoutInputBoundary{
    private final LogoutSessionDataAccessInterface currentUserDataAccessObject;
    private final LogoutUserDataAccessInterface logoutUserDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutSessionDataAccessInterface logoutSessionDataAccessInterface,
                            LogoutUserDataAccessInterface logoutUserDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.currentUserDataAccessObject = logoutSessionDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
        this.logoutUserDataAccessObject = logoutUserDataAccessInterface;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        logoutUserDataAccessObject.save(currentUserDataAccessObject.getCurrentUser());
        currentUserDataAccessObject.clearCurrentUser();

        final LogoutOutputData logoutOutputData = new LogoutOutputData();
        logoutPresenter.prepareSuccessView(logoutOutputData);

    }
}
