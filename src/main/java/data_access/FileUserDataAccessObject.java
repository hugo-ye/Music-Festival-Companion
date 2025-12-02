package data_access;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data_formatters.LocalDateAdapter;
import entity.User;
import use_case.login.LoginSessionDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupDataAccessInterface;

/**
 * Persistent Storage Data Access Object for User.
 * This class is responsible for reading and writing user data to a file.
 */
public class FileUserDataAccessObject implements LoginUserDataAccessInterface, SignupDataAccessInterface,
        LogoutUserDataAccessInterface {

    private static final Type USER_LIST_TYPE = new TypeToken<List<User>>() {
    }.getType();
    private final String filePath;
    private final Gson gson;

    private final LoginSessionDataAccessInterface sessionDataAccess;

    public FileUserDataAccessObject(String filePath, LoginSessionDataAccessInterface sessionDataAccess) {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        this.filePath = filePath;
        this.sessionDataAccess = sessionDataAccess;
    }

    /**
     * Method to get a list of users by reading file at filePath.
     *
     * @return List of user read from filePath. Or empty List for no existing filePath or empty file in filePath.
     */
    public List<User> read() {
        final Path path = Paths.get(filePath);
        List<User> result = new ArrayList<>();

        if (Files.exists(path)) {
            result = readHelper();
        }
        return result;
    }

    /**
     * Helper used by read, which will return a list of user once the given filePath is existing.
     *
     * @return List of user read from filePath.
     * @throws RuntimeException if an I/O error occurs when reading the file.
     */
    @NotNull
    private List<User> readHelper() {
        List<User> result = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            final List<User> data = gson.fromJson(reader, USER_LIST_TYPE);
            if (data != null) {
                result = data;
            }
            return result;
        } catch (IOException ioException) {
            System.out.println("Error reading file at filePath: " + filePath);
            throw new RuntimeException(ioException);
        }
    }

    /**
     * A method that get User by username.
     *
     * @param username the username used to retrieve the {@link User}
     * @return The User data that with same username as provided, or null for no such User.
     */
    public User getByUsername(String username) {
        final List<User> users = read();
        User result = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                result = user;
                break;
            }
        }
        return result;
    }

    /**
     * A method to save user to file.
     *
     * @param user the {@link User} object to save
     */
    public void save(User user) {
        final List<User> users = read();
        final User existingUser = existsByUsername(user.getUsername(), users);
        if (existingUser != null) {
            replaceUser(existingUser, user, users);
        } else {
            users.add(user);
        }
        writeAllUsers(users);
    }

    /**
     * A method that write users into a file.
     *
     * @param users A List of users provided.
     * @throws RuntimeException if an I/O error occurs when reading the file.
     */
    public void writeAllUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(users, writer);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * A method to get a user from A List of user by a username.
     *
     * @param username name of user, String.
     * @param users    a List of user.
     * @return the user if a certain user with the username in the users, else null;
     */
    private User existsByUsername(String username, List<User> users) {
        User result = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                result = user;
                break;
            }
        }
        return result;
    }

    /**
     * A boolean method that return whether the user with such username is existing.
     *
     * @param username the username to check for existence.
     * @return whether the such user existing.
     */
    public boolean existsByUsername(String username) {
        final List<User> users = read();
        return existsByUsername(username, users) != null;
    }

    /**
     * A method that replace a user in a list of user.
     * @param oldUser the user to be replaced.
     * @param newUser the new user.
     * @param users the list of users.
     */
    private void replaceUser(User oldUser, User newUser, List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(oldUser)) {
                users.set(i, newUser);
                break;
            }
        }
    }
}
