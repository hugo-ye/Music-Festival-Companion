package use_case.logout;

public interface LogoutInputBoundary {
    /**
     * Executes the logout Use Case.
     * Saves the user and their lists to persistent storage
     *
     * @param logoutInputData the input data for logout.
     */
    void execute(LogoutInputData logoutInputData);
}
