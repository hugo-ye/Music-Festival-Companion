package use_case.logout;

import entity.User;

public interface LogoutSessionDataAccessInterface {
    void clearCurrentUser();
    User getCurrentUser();
}
