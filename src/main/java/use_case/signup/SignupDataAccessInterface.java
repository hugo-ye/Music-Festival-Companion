package use_case.signup;

import entity.User;

public interface SignupDataAccessInterface {

    boolean existsByUsername(String username);

    void save(User user);

}
