package use_case.login;

import entity.User;

/**
 * DAO for LoginUser
 */
public interface LoginUserDataAccessInterface {
    /**
     * Checks whether a user exists with the specified username.
     *
     * @param username the username to check for existence.
     * @return {@code true} if a User exists with a given username. Otherwise, it returns {@code false}
     */
    boolean existsByUsername(String username);

    /**
     * Retrieves a {@link User} given the user's username.
     * @param username the username used to retrieve the {@link User}
     * @return a {@link User} that has the username.
     */
    User getByUsername(String username);
}
