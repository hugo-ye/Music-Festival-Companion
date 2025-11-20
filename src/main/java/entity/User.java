package entity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public String toString() {
        String listNames = eventLists.stream()
                .filter(list -> !list.getName().equals("master_list"))
                .map(list -> String.format("%s (%d events)", list.getName(), list.getEvents().size()))
                .collect(Collectors.joining("; "));

        if (listNames.isEmpty()) {
            listNames = "None";
        }

        // Count events in the master list
        int masterListCount = masterList != null ? masterList.getEvents().size() : 0;

        return "--- USER ACCOUNT DETAILS ---\n" +
                "Username:      " + username + "\n" +
                "Master List:   " + masterList.getName() + " (" + masterListCount + " events)\n" +
                "Other Lists:   " + listNames + "\n" +
                "----------------------------";
    }
}
