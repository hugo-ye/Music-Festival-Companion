package use_case.logout;

import entity.User;

/**
 * DAO for LogoutUser
 */
public interface LogoutUserDataAccessInterface {
    /**
     * Saves the user entity to persistent storage.
     *
     * @param user the {@link User} who is logging out.
     */
    public void save(User user);
}
