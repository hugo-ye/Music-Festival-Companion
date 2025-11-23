package entity;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testConstructorWithParameters() {
        final String username = "username";
        final String password = "password";
        final User user = new User(username, password);
        assert user.getUsername().equals(username);
        assert user.getPassword().equals(password);
    }
}
