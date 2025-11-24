package use_case.logout;

import entity.User;

public interface LogoutUserDataAccessInterface {
    public void save(User user);
}
