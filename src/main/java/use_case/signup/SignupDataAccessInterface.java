package use_case.signup;

import entity.User;

public interface SignupDataAccessInterface {
    /**
     * Checks whether a user exists with the specified username.
     *
     * @param username the username to check for existence.
     * @return {@code true} if a User exists with a given username. Otherwise, it returns {@code false}
     */
    boolean existsByUsername(String username);

    /**
     * Saves a new user.
     * @param user the {@link User} object to save
     */
    void save(User user);

}
