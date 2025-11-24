package data_access;

import entity.Event;
import entity.EventList;
import entity.User;
import use_case.attend_event.AttendEventDataAccessInterface;
import use_case.create_event_list.CreateEventListDataAccessInterface;
import use_case.delete_event_list.DeleteEventListDataAccessInterface;
import use_case.display_event_lists.DisplayEventListsDataAccessInterface;
import use_case.login.LoginSessionDataAccessInterface;
import use_case.logout.LogoutSessionDataAccessInterface;

import java.util.List;

public class InMemoryUserDataAccessObject implements LoginSessionDataAccessInterface, LogoutSessionDataAccessInterface,
        AttendEventDataAccessInterface, CreateEventListDataAccessInterface, DeleteEventListDataAccessInterface,
        DisplayEventListsDataAccessInterface {

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

    public void saveEvent(Event event) {
        currentUser.getMasterList().addEvent(event);
    }

    public boolean alreadyAttends(Event event) {
        return currentUser.getMasterList().getEvents().contains(event);
    }

    @Override
    public boolean existsByName(String listName) {
        User user = getCurrentUser();
        if (user == null) return false;
        for (EventList list : user.getLists()) {
            if (list.getName().equalsIgnoreCase(listName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void createEventList(EventList eventList) {
        User user = getCurrentUser();
        if (user == null) return;
        if (eventList == null) return;
        if (user.getLists().contains(eventList)) {
            return;
        }
        user.getLists().add(eventList);
        System.out.println("after createEventList, user is: \n" + user);
    }

    @Override
    public boolean existsById(String listId) {
        User current = getCurrentUser();
        if (current == null) return false;
        if (current.getMasterList().getId().equals(listId)) return true;
        for (EventList list : current.getLists()) {
            if (list.getId().equals(listId)) return true;
        }
        return false;
    }

    @Override
    public boolean isMasterList(String listId) {
        User user = getCurrentUser();
        if (user == null) return false;
        return user.getMasterList().getId().equals(listId);
    }

    @Override
    public void deleteById(String listId) {
        User user = getCurrentUser();
        if (user == null) return;
        user.removeListById(listId);
        System.out.println("after deleteById, user is: \n" + user);
    }

    @Override
    public List<EventList> getEventLists() {
        User user = getCurrentUser();
        if (user == null) return null;
        return user.getLists();
    }
}
