package use_case.logout;

/**
 * Interactor fot the logout use case.
 * <p>
 *     This class Implements {@link LogoutInputBoundary} and handles the logout process for the user.
 * </p>
 */
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

    /**
     * Executes the logout process.
     * <p>
     *     it perfrome the following steps: it saves the currently loggedin user, Clears the current user,
     *     Creates a {@link LogoutOutputData} object and passes it to the presenter
     * </p>
     * @param logoutInputData the input data for logout, which is empty because you don't need an input to logout.
     *
     */
    @Override
    public void execute(LogoutInputData logoutInputData) {
        logoutUserDataAccessObject.save(currentUserDataAccessObject.getCurrentUser());
        currentUserDataAccessObject.clearCurrentUser();

        final LogoutOutputData logoutOutputData = new LogoutOutputData();
        logoutPresenter.prepareSuccessView(logoutOutputData);

    }
}
