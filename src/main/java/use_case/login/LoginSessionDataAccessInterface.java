package use_case.login;

import entity.User;

public interface LoginSessionDataAccessInterface {
    void setCurrentUser(User user);
}
