package use_case.logout;

import entity.User;

/**
 * DAO for LogoutUser
 */
public interface LogoutUserDataAccessInterface {
    /**
     * updates any necessary information related to the user who is logging out.
     *
     * @param user the {@link User} who is logging out.
     */
    public void save(User user);
}
