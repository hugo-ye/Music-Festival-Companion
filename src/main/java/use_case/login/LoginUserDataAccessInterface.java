package use_case.login;

import entity.User;

public interface LoginUserDataAccessInterface {

    boolean existsByUsername(String username);

    User getByUsername(String username);
}
