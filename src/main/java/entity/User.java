package entity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private String password;
    private final List<EventList> eventLists = new ArrayList<>();
    private final EventList masterList = new EventList("master_list", "Master List");

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public List<EventList> getEventLists() {
        return eventLists;
    }

    public EventList getMasterList() {
        return masterList;
    }

    public String getPassword() {
        return password;
    }

    // Mutators

    public void setPassword(String password) {
        this.password = password;
    }





}
