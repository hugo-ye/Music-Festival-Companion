package entity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private final String username;
    private String password;
    private List<EventList> lists;
    private final EventList masterList = new EventList("master_list", "Master List");

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.lists = new ArrayList<>();
    }

    @Override
    public String toString() {
        String listStr = "";
        for (EventList list : lists) {
            listStr += list.toString() + "\n";
        }
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lists=" + lists +
                '}';
    }

    // Getters

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

    public EventList getListById(String id) {
        for (EventList list : lists) {
            if (list.getId().equals(id)) {
                return list;
            }
        }
        return null;
    }

    public void setLists(List<EventList> lists) {
        this.lists = lists;
    }

    // Mutators
    public void setPassword(String password) {
        this.password = password;

    }

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

