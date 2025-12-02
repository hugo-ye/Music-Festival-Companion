package use_case.logout;

/**
 * Interactor for the logout use case.
 * This class handles logout and saving to persistent storage.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutSessionDataAccessInterface currentUserDataAccessObject;
    private final LogoutUserDataAccessInterface logoutUserDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutSessionDataAccessInterface logoutSessionDataAccessInterface,
                            LogoutUserDataAccessInterface logoutUserDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.currentUserDataAccessObject = logoutSessionDataAccessInterface;
        this.logoutUserDataAccessObject = logoutUserDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    /**
     * Executes the logout Use Case.
     * Saves the user and their lists to persistent storage
     *
     * @param logoutInputData the input data for logout.
     */
    @Override
    public void execute(LogoutInputData logoutInputData) {
        logoutUserDataAccessObject.save(currentUserDataAccessObject.getCurrentUser());
        currentUserDataAccessObject.clearCurrentUser();

        final LogoutOutputData logoutOutputData = new LogoutOutputData();
        logoutPresenter.prepareSuccessView(logoutOutputData);
    }
}
