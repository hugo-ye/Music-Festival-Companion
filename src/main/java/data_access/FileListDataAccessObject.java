package data_access;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data_formatters.LocalDateAdapter;
import entity.EventList;
import entity.User;
import use_case.create_event_list.CreateEventListDataAccessInterface;
import use_case.login.LoginSessionDataAccessInterface;
import use_case.delete_event_list.DeleteEventListDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupDataAccessInterface;

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

/**
 * the json model
 * [
 * {username, lists}
 * ]
 * <p>
 * in lists:
 * {
 * masterList:[EventList]
 * lists: [ [list of EventList] ]
 * }
 */

// hasnt been connected yet. just creating general functions
public class FileListDataAccessObject implements LoginUserDataAccessInterface, SignupDataAccessInterface {

    private final String filePath;
    private final Gson gson;
    private static final Type USER_LIST_TYPE = new TypeToken<List<User>>() {
    }.getType();
    private final LoginSessionDataAccessInterface sessionDataAccess;

    public FileListDataAccessObject(String filePath, LoginSessionDataAccessInterface sessionDataAccess) {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        this.filePath = filePath;
        this.sessionDataAccess = sessionDataAccess;
    }

    public List<User> read() {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(filePath)) {
            List<User> data = gson.fromJson(reader, USER_LIST_TYPE);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error reading file at filePath: " + filePath);
            throw new RuntimeException(e);
        }
    }

    public User getByUsername(String username) {
        List<User> users = read();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    public void save(User user) {
        List<User> users = read();
        User existingUser = existsByUsername(user.getUsername(), users);
        if (existingUser != null) {
            replaceUser(existingUser, user, users);
        } else {
            users.add(user);
        }
        writeAllUsers(users);
    }

    public void writeAllUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User existsByUsername(String username, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean existsByUsername(String username) {
        List<User> users = read();
        return existsByUsername(username, users) != null;
    }

    private void replaceUser(User oldUser, User newUser, List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(oldUser)) {
                users.set(i, newUser);
                break;
            }
        }
    }

}


