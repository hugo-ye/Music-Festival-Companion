package data_access;

import entity.User;
import use_case.login.LoginSessionDataAccessInterface;
import use_case.logout.LogoutSessionDataAccessInterface;

public class InMemoryUserDataAccessObject implements LoginSessionDataAccessInterface, LogoutSessionDataAccessInterface {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void clearCurrentUser() {
        this.currentUser = null;
    }

}
