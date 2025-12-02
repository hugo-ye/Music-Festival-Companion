package use_case.login;

import entity.User;

/**
 * DAO for loginSession.
 */
public interface LoginSessionDataAccessInterface {
    /**
     * Sets the specified {@link User} as the current logged in user.
     * @param user the user to set as the current user.
     */
    void setCurrentUser(User user);

    /**
     * Retrieves the user that is currently logged in.
     *
     * @return the current {@link User}
     */
    User getCurrentUser();
}
