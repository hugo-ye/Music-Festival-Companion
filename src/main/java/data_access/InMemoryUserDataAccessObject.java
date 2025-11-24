package data_access;

import entity.Event;
import entity.EventList;
import entity.User;
import use_case.attend_event.AttendEventDataAccessInterface;
import use_case.display_notifications.DisplayNotificationsDataAccessInterface;
import use_case.create_event_list.CreateEventListDataAccessInterface;
import use_case.delete_event_list.DeleteEventListDataAccessInterface;
import use_case.display_event_lists.DisplayEventListsDataAccessInterface;
import use_case.login.LoginSessionDataAccessInterface;
import use_case.logout.LogoutSessionDataAccessInterface;
import use_case.remove_event_from_list.RemoveEventFromListDataAccessInterface;
import use_case.save_event_to_list.SaveEventToListDataAccessInterface;

import java.util.List;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDataAccessObject implements
        LoginSessionDataAccessInterface,
        LogoutSessionDataAccessInterface,
        AttendEventDataAccessInterface,
        CreateEventListDataAccessInterface,
        DeleteEventListDataAccessInterface,
        DisplayEventListsDataAccessInterface,
        SaveEventToListDataAccessInterface,
        RemoveEventFromListDataAccessInterface,
        DisplayNotificationsDataAccessInterface {

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

    @Override
    public void saveEventToList(Event event, EventList eventList) {
        currentUser = getCurrentUser();
        int currentEventListIndex = currentUser.getLists().indexOf(eventList);
        // for situation of non-exist: may not happen, since user cannot select eventList that is not created
        if (currentEventListIndex == -1) {
            System.out.println("no eventList currently");
            return;
        }
        EventList currentEventList = currentUser.getLists().get(currentEventListIndex);
        currentEventList.addEvent(event);
        // I omit the situation that eventList that will add the same event

        // for persistent storage, may need discuss later, currently I only deal with inMemory
    }

    public void saveEventToMasterList(Event event) {
        currentUser.getMasterList().addEvent(event);
    }

    public boolean alreadyAttends(Event event) {
        return currentUser.getMasterList().getEvents().contains(event);
    }

    @Override
    public List<Event> getMasterListEvents() {
        return currentUser.getMasterList().getEvents();
    }

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

    @Override
    public void removeEventFromList(Event removedEvent, EventList targetEventList) {
        User user = getCurrentUser();
        int currentEventListIndex = currentUser.getLists().indexOf(targetEventList);
        // for situation of non-exist: may not happen, since user cannot select eventList that is not created
        if (currentEventListIndex == -1) {
            System.out.println("no eventList currently");
            return;
        }
        EventList currentEventList = currentUser.getLists().get(currentEventListIndex);
        currentEventList.removeEvent(removedEvent);
    }
}
