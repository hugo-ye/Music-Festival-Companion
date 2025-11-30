package use_case.logout;

import entity.User;

/**
 * DAO for the LogoutSession
 */
public interface LogoutSessionDataAccessInterface {
    /**
     * clears the current user session.
     */
    void clearCurrentUser();

    /**
     * Returns the user that is currently logged in.
     * @return The {@link User} object that is the current user.
     */
    User getCurrentUser();
}
