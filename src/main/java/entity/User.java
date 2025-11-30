package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in our program. Used to store user credentials and user lists.
 */
public class User {
    private final String username;
    private String password;
    private List<EventList> lists;
    private final EventList masterList = new EventList("master_list", "Master List");

    /**
     * A constructor for User that initializes a user with the given username and password.
     * @param username is the username of the user.
     * @param password is the password that the user set.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.lists = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public EventList getMasterList() {
        return masterList;
    }

    public String getPassword() {
        return password;
    }

    public List<EventList> getLists() {
        return lists;
    }

    /**
     * Returns a list by given a provided id.
     * @param id is the id of the list.
     * @return the list with the given id.
     */
    public EventList getListById(String id) {
        EventList result = null;
        for (EventList list : lists) {
            if (list.getId().equals(id)) {
                result = list;
                break;
            }
        }
        return result;
    }

    public void setLists(List<EventList> lists) {
        this.lists = lists;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    /**
     * Removes a list from the user's list of lists given a list id.
     * @param id is the id of the list to be removed.
     */
    public void removeListById(String id) {
        EventList toRemove = null;
        for (EventList list : lists) {
            if (list.getId().equals(id)) {
                toRemove = list;
                break;
            }
        }
        if (toRemove != null) {
            lists.remove(toRemove);
        }
    }
}

