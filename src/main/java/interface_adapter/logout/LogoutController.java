package interface_adapter.logout;

import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

/**
 * Controller for the Logout use case.
 * <p>
 *     The controller receives logout requests from the UI and calls the Interactor.
 * </p>
 */
public class LogoutController {
    private final LogoutInputBoundary logoutInteractor;

    public LogoutController(LogoutInputBoundary logoutInteractor) {
        this.logoutInteractor = logoutInteractor;
    }

    /**
     * Executes the logout process.
     * <p>
     *     This method creates a {@link LogoutInputData} object (which is empty) and passes it to the interactor for
     *     processing.
     * </p>
     */
    public void execute(){
        logoutInteractor.execute(new LogoutInputData());
    }
}
